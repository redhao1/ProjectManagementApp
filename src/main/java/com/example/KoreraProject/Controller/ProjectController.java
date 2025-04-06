package com.example.KoreraProject.Controller;

import com.example.KoreraProject.DatabaseModels.*;
import com.example.KoreraProject.Repository.*;
import com.example.KoreraProject.Service.ProjectService;
import com.example.KoreraProject.Service.ResourceToProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/project")

public class ProjectController {

    @Autowired
    ProjectService projectService;
    @Autowired
    UserRepository userRep;
    @Autowired
    ProjectRepository projectRep;
    @Autowired
    ResourceToProjectService resourceToProjectService;

    Map<String, String> response = new HashMap<>();

    @PostMapping("/get")
    public ResponseEntity<?> getAllProjects(@RequestBody Map<String, String> requestBody) {
        String userName = requestBody.get("userName");
        Optional<User> hasUser = userRep.findByName(userName);
        if (hasUser.isPresent()) {
            List<Project> projects = projectRep.findByUser(hasUser.get());
            response.put("message", "get all projects successfully");
            return new ResponseEntity<>(projects, HttpStatus.OK);
        }
        response.put("message", "user not found");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @PostMapping("/getResources")
    public ResponseEntity<?> getResourcesInProject(@RequestBody Map<String, String> requestBody) {
        String pName = requestBody.get("projectName");
        String uName = requestBody.get("userName");
        Optional<User> hasUser = userRep.findByName(uName);
        if (hasUser.isPresent()) {
            User user = hasUser.get();
            Set<Project> projects = user.getProjects();
            for (Project p: projects) {
                if (p.getName().equals(pName)) {
                    List<Resource> resources = resourceToProjectService.getAllResoruces(p);
                    response.put("message", "get all resources in project successfully");
                    return new ResponseEntity<>(resources, HttpStatus.OK);
                }
            }
            response.put("message", "project not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        response.put("message", "user not found");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addProject(@RequestBody Map<String, String> requestBody) {
        String pName = requestBody.get("projectName");
        String uName = requestBody.get("userName");
        Optional<User> user = userRep.findByName(uName);
        if (user.isPresent()) {
            boolean isSuccess = projectService.addNewProject(pName, user.get());
            if (isSuccess) {
                response.put("message", "new project has been created");
                return new ResponseEntity<>(response, HttpStatus.CREATED);
            }
            response.put("message", "create project failed");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        response.put("message", "user not found");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteProject(@RequestBody Map<String, String> requestBody) {
        String pName = requestBody.get("projectName");
        String uName = requestBody.get("userName");
        Optional<User> user = userRep.findByName(uName);
        if (user.isPresent()) {
            boolean isSuccess = projectService.removeProject(pName, user.get());
            if (isSuccess) {
                response.put("message", "project has been removed");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            response.put("message", "remove project failed");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        response.put("message", "user not found");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @PostMapping("/addResources")
    public ResponseEntity<?> addResources(@RequestBody Map<String, Object> requestBody) {
        String pName = (String) requestBody.get("projectName");
        String uName = (String) requestBody.get("userName");
        List<String> resourceNameList = (List<String>) requestBody.get("resources");
        Optional<User> hasUser = userRep.findByName(uName);
        if (hasUser.isPresent()) {
            User user = hasUser.get();
            boolean isSuccess = projectService.addResources(pName, user, resourceNameList);
            if (isSuccess) {
                response.put("message", "resources have been added");
                return new ResponseEntity<>(response, HttpStatus.CREATED);
            } else {
                response.put("message", "add resources failed");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
        }
        response.put("message", "user not found");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @PostMapping("/removeResources")
    public ResponseEntity<?> removeResources(@RequestBody Map<String, Object> requestBody) {
        String pName = (String) requestBody.get("projectName");
        String uName = (String) requestBody.get("userName");
        List<String> resourceNameList = (List<String>) requestBody.get("resources");
        Optional<User> hasUser = userRep.findByName(uName);
        if (hasUser.isPresent()) {
            User user = hasUser.get();
            boolean isSuccess = projectService.removeResources(pName, user, resourceNameList);
            if (isSuccess) {
                response.put("message", "resources have been removed");
                return new ResponseEntity<>(response, HttpStatus.CREATED);
            } else {
                response.put("message", "remove resources failed");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
        }
        response.put("message", "user not found");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
