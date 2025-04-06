package com.example.KoreraProject.Repository;


import com.example.KoreraProject.DatabaseModels.Project;
import com.example.KoreraProject.DatabaseModels.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResourceRepository extends JpaRepository<Resource, Integer>{
    Optional<Resource> findByName(String name);
    List<Resource> findByCode(String code);
}
