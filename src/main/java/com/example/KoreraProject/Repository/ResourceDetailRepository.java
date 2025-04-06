package com.example.KoreraProject.Repository;

import com.example.KoreraProject.DatabaseModels.Resource;
import com.example.KoreraProject.DatabaseModels.ResourceDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResourceDetailRepository extends JpaRepository<ResourceDetail, Integer>{
    List<ResourceDetail> findByResource(Resource resource);
}

