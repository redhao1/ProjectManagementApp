package com.example.KoreraProject.Repository;

import com.example.KoreraProject.DatabaseModels.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
    Optional<User> findByName(String name);
    List<User> findByRole(String role);

}
