package com.example.KoreraProject.Service;

import com.example.KoreraProject.DatabaseModels.*;

import java.util.List;

public interface ProjectService {
    public boolean addNewProject(String projectName, User user);
    public boolean removeProject(String projectName, User user);
    public boolean addResources(String projectName, User user, List<String> resourcesNameList);
    public boolean removeResources(String projectName, User user, List<String> resourcesNameList);

}
