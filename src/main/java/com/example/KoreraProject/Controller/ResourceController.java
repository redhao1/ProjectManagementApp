package com.example.KoreraProject.Controller;

import com.example.KoreraProject.DatabaseModels.Resource;
import com.example.KoreraProject.DatabaseModels.User;
import com.example.KoreraProject.Repository.ResourceDetailRepository;
import com.example.KoreraProject.Repository.ResourceRepository;
import com.example.KoreraProject.Service.ResourceDetailService;
import com.example.KoreraProject.Service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/resource")
//@PreAuthorize("hasAuthority('MANAGEMENT')")

public class ResourceController {

    @Autowired
    ResourceRepository resourceRep;
    @Autowired
    ResourceDetailRepository resourceDetailRep;
    @Autowired
    ResourceService resourceService;
    @Autowired
    ResourceDetailService resourceDetailService;

    Map<String, String> response = new HashMap<>();

    @GetMapping
    public ResponseEntity<?> getAllResources() {
        List<Resource> resources = resourceRep.findAll();
        response.put("message", "get all resources successfully");
        return new ResponseEntity<>(resources, HttpStatus.OK);

    }


    @PostMapping("/add")
    public ResponseEntity<?> addResource(@RequestBody Map<String, String> requestBody) {
        String rName = requestBody.get("resourceName");
        String rCode = requestBody.get("resourceCode");
        boolean isSuccess = resourceService.addNewResource(rName, rCode);
        if (isSuccess) {
            response.put("message", "new resource has been created");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }
        response.put("message", "add resource failed");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteResource(@RequestBody Map<String, String> requestBody) {
        String rName = requestBody.get("resourceName");
        boolean isSuccess = resourceService.removeResource(rName);
        if (isSuccess) {
            response.put("message", "resource has been removed");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        response.put("message", "remove resource failed");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/addColumn")
    public ResponseEntity<?> addColumn(@RequestBody Map<String, String> requestBody) {
        String cName = requestBody.get("columnName");
        boolean isSuccess = resourceDetailService.addNewColumn(cName);
        if (isSuccess) {
            response.put("message", "new column has been added");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }
        response.put("message", "add column failed");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
