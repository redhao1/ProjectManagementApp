package com.example.KoreraProject.Service;

import com.example.KoreraProject.DatabaseModels.*;
import com.example.KoreraProject.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ResourceDetailServiceImpl implements ResourceDetailService{

    @Autowired
    ResourceRepository resourceRep;
    @Autowired
    ResourceDetailRepository resourceDetailRep;

    private Set<String> extraColumns = new HashSet<>();

    public Set<String> getExtraColumns() {
        return extraColumns;
    }

    public boolean addNewColumn(String columnName) {
        if(extraColumns.contains(columnName)) {
            return false;
        } else {
            List<Resource> allResources = resourceRep.findAll();
            if (!allResources.isEmpty()) {
                for (Resource resource: allResources) {
                    ResourceDetail rd = new ResourceDetail(columnName);
                    resource.addResourceDetail(rd);
                    resourceRep.save(resource);
                }
            }
            extraColumns.add(columnName);
        }
        return true;
    }

}
