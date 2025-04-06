package com.example.KoreraProject.Service;

import com.example.KoreraProject.DatabaseModels.*;
import com.example.KoreraProject.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ResourceToProjectServiceImpl implements ResourceToProjectService{
    ResourceRepository resourceRep;
    ProjectRepository projectRep;
    ResourceToProjectRepository resourceToProjectRep;

    @Autowired
    public ResourceToProjectServiceImpl(ProjectRepository projectRep, ResourceRepository resourceRep,
                                        ResourceToProjectRepository resourceToProjectRep) {
        this.projectRep = projectRep;
        this.resourceRep = resourceRep;
        this.resourceToProjectRep = resourceToProjectRep;
    }

    @Override
    public List<Resource> getAllResoruces(Project project) {
        List<Resource> resources = new ArrayList<>();
        List<ResourceToProject> rps = resourceToProjectRep.findByProject(project);
        for (ResourceToProject rp : rps) {
            resources.add(rp.getResource());
        }
        return resources;
    }

    @Override
    public void removeAllResources(Project project) {
        List<ResourceToProject> rps = resourceToProjectRep.findByProject(project);
        for (ResourceToProject rp : rps) {
            try {
                resourceToProjectRep.delete(rp);
            } catch (Exception e) {
                System.out.println("unable to remove all the resources");
            }
        }
    }

}
