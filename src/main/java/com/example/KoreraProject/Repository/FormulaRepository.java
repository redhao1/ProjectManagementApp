package com.example.KoreraProject.Repository;

import com.example.KoreraProject.DatabaseModels.Formula;
import com.example.KoreraProject.DatabaseModels.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FormulaRepository extends JpaRepository<Formula, Integer>{
    List<Formula> findByProject(Project project);

}
