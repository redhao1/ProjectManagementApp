package com.example.KoreraProject.Repository;

import com.example.KoreraProject.DatabaseModels.Template;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TemplateRepository extends JpaRepository<Template, Integer>{
}
