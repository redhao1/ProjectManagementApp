package com.example.KoreraProject.Repository;

import com.example.KoreraProject.DatabaseModels.Project;
import com.example.KoreraProject.DatabaseModels.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer>{
    Optional<Project> findByName(String name);
    List<Project> findByUser(User user);
}

