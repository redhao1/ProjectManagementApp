package com.example.KoreraProject.Service;

import com.example.KoreraProject.DatabaseModels.*;

import java.util.List;

public interface ResourceToProjectService {
    public List<Resource> getAllResoruces(Project project);
    public void removeAllResources(Project project);
}
