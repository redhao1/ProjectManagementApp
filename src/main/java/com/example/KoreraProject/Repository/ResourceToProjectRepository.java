package com.example.KoreraProject.Repository;

import com.example.KoreraProject.DatabaseModels.Formula;
import com.example.KoreraProject.DatabaseModels.Project;
import com.example.KoreraProject.DatabaseModels.Resource;
import com.example.KoreraProject.DatabaseModels.ResourceToProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResourceToProjectRepository extends JpaRepository<ResourceToProject, Integer>{
    List<ResourceToProject> findByProject(Project project);
    List<ResourceToProject> findByResource(Resource resource);
    Optional<ResourceToProject> findByResourceAndProject(Resource resource, Project project);
}
