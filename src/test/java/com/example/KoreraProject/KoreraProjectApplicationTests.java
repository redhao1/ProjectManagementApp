package com.example.KoreraProject;

import com.example.KoreraProject.DatabaseModels.*;
import com.example.KoreraProject.Repository.*;
import com.example.KoreraProject.Service.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class KoreraApplicationTests {

	@Autowired
	UserRepository userRepository;


	@Autowired
	ProjectService projectService;
	@Autowired
	ResourceToProjectService resourceToProjectService;

	@Test
	void contextLoads() {
//		Candidate candidate1 = new Candidate("Mike", 4, "Dean", "Rutgers",
//				"1234567890", "mike@gmail.com", "NY", "12/09/2024");
//		Candidate candidate2 = new Candidate("Tom", 6, "Teacher", "Rutgers",
//				"1234567891", "tom@gmail.com", "NJ", "01/28/2025");
//		Candidate candidate3 = new Candidate("Allen", 7, "Developer", "Google",
//				"1234567892", "allen@gamil.com", "CA", "01/22/2025");
//		Candidate candidate4 = new Candidate("Bob", 3, "Provost", "Rutgers",
//				"1234567893", "bob@gmail.com", "NJ", "01/20/2025");
//
//		candidateService.addNewCandidate(candidate1);
//		candidateService.addNewCandidate(candidate2);
//		candidateService.addNewCandidate(candidate3);
//		candidateService.addNewCandidate(candidate4);
    }
}

