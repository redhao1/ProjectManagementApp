package com.example.KoreraProject.Service;

import com.example.KoreraProject.DatabaseModels.*;
import com.example.KoreraProject.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ResourceServiceImpl implements ResourceService{

    @Autowired
    ResourceRepository resourceRep;
    @Autowired
    ResourceDetailRepository resourceDetailRep;

    @Autowired
    ResourceDetailService resourceDetailService;


    public boolean addNewResource(String resourceName, String resourceCode) {
        Optional<Resource> hasResource = resourceRep.findByName(resourceName);
        if (hasResource.isPresent()) {
            return false;
        } else {
            try {
                Resource resource = new Resource(resourceName, resourceCode);
                Set<String> cols = resourceDetailService.getExtraColumns();
                if (!cols.isEmpty()) {
                    for (String col: cols) {
                        ResourceDetail rd = new ResourceDetail(col);
                        resource.addResourceDetail(rd);
                        resourceDetailRep.save(rd);
                    }
                }
                resourceRep.save(resource);
            } catch (Exception e) {
                return false;
            }
        }
        return true;
    }

    public boolean removeResource(String resourceName) {
        Optional<Resource> hasResource = resourceRep.findByName(resourceName);
        if (hasResource.isEmpty()) {
            return false;
        } else {
            Resource resource = hasResource.get();
            try {
                resourceRep.delete(resource);
                List<ResourceDetail> rds = resourceDetailRep.findByResource(resource);
                if (!rds.isEmpty()) {
                    for (ResourceDetail rd: rds) {
                        resourceDetailRep.delete(rd);
                    }
                }
            } catch (Exception e) {
                return false;
            }
        }
        return true;
    }
}
