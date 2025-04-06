package com.example.KoreraProject.Service;

import com.example.KoreraProject.DatabaseModels.*;
import com.example.KoreraProject.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectServiceImpl implements ProjectService{
    private UserRepository userRep;
    private ProjectRepository projectRep;
    private ResourceRepository resourceRep;
    private ResourceToProjectRepository resourceToProjectRep;

    @Autowired
    ResourceToProjectService resourceToProjectService;

    @Autowired
    public ProjectServiceImpl(UserRepository userRep, ProjectRepository projectRep,
                              ResourceRepository resourceRep, ResourceToProjectRepository resourceToProjectRep) {
        this.userRep = userRep;
        this.projectRep = projectRep;
        this.resourceRep = resourceRep;
        this.resourceToProjectRep = resourceToProjectRep;
    }

    @Override
    public boolean addNewProject(String projectName, User user) {
        Optional<User> u = userRep.findByName(user.getName());
        if (u.isPresent()) {
            List<Project> projects = projectRep.findByUser(u.get());
            for (Project p: projects) {
                if (p.getName().equals(projectName)) return false;
            }
            try {
                Project newProject = new Project(projectName);
                user.addProject(newProject);
                projectRep.save(newProject);
                userRep.save(u.get());
            } catch (Exception e) {
                return false;
            }
        } else return false;
        return true;
    }

    @Override
    public boolean removeProject(String projectName, User user) {
        Optional<User> u = userRep.findByName(user.getName());
        if (u.isPresent()) {
            User targetUser = u.get();
            List<Project> projects = projectRep.findByUser(targetUser);
            for (Project p: projects) {
                if (p.getName().equals(projectName)) {
                    try {
                        targetUser.removeProject(p);
                        projectRep.delete(p);
                        userRep.save(targetUser);
                        return true;
                    } catch (Exception e) {
                        return false;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public boolean addResources(String projectName, User user, List<String> resourcesNameList) {
        resourceToProjectService = new ResourceToProjectServiceImpl(projectRep, resourceRep, resourceToProjectRep);
        List<Project> projects = projectRep.findByUser(user);
        if (projects.isEmpty()) {
            return false;
        } else {
            for (Project p : projects) {
                if (p.getName().equals(projectName)) {
                    List<Resource> resourceList = resourceToProjectService.getAllResoruces(p);
                    if (!resourceList.isEmpty()) {
                        for (String rName : resourcesNameList) {
                            for (Resource r : resourceList) {
                                if (r.getName().equals(rName)) return false;
                            }
                        }
                    }
                    for (String rName: resourcesNameList) {
                        Optional<Resource> hasResource = resourceRep.findByName(rName);
                        if (hasResource.isEmpty()) {
                            return false;
                        } else {
                            ResourceToProject newRp = new ResourceToProject(p, hasResource.get());
                            try {
                                resourceToProjectRep.save(newRp);
                            } catch (Exception e) {
                                System.out.println("Can't add the new resourceToProject object");
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    @Override
    public boolean removeResources(String projectName, User user, List<String> resourcesNameList) {
        resourceToProjectService = new ResourceToProjectServiceImpl(projectRep, resourceRep, resourceToProjectRep);
        List<Project> projects = projectRep.findByUser(user);
        if (projects.isEmpty()) {
            return false;
        } else {
            for (Project p : projects) {
                if (p.getName().equals(projectName)) {
                    List<Resource> resourceList = resourceToProjectService.getAllResoruces(p);
                    if (resourceList.isEmpty()) {
                        return false;
                    } else {
                        List<String> existNames = new ArrayList<>();
                        for (Resource r : resourceList) {
                            existNames.add(r.getName());
                        }
                        if (!existNames.containsAll(resourcesNameList)) {
                            return false;
                        } else {
                            for (String rName : resourcesNameList) {
                                Optional<Resource> hasResource = resourceRep.findByName(rName);
                                if (hasResource.isEmpty()) return false;
                                else {
                                    Optional<ResourceToProject> rp = resourceToProjectRep.findByResourceAndProject(hasResource.get(), p);
                                    if (rp.isEmpty()) {
                                        return false;
                                    } else {
                                        try {
                                            resourceToProjectRep.delete(rp.get());
                                        } catch (Exception e) {
                                            System.out.println("unable to delete resourceToProject object");
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return true;
    }
}
