package com.example.KoreraProject.Service;

import com.example.KoreraProject.DatabaseModels.*;

public interface ResourceService {
    public boolean addNewResource(String resourceName, String resourceCode);
    public boolean removeResource(String resourceName);

}
