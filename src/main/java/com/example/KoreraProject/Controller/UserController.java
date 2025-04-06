package com.example.KoreraProject.Controller;

import com.example.KoreraProject.DatabaseModels.User;
import com.example.KoreraProject.Repository.ProjectRepository;
import com.example.KoreraProject.Repository.UserRepository;
import com.example.KoreraProject.Service.MyUserDetailsService;
import com.example.KoreraProject.Service.ProjectService;
import com.example.KoreraProject.Service.UserService;
import com.example.KoreraProject.util.JwtUtil;
import com.example.KoreraProject.util.Role;
import com.fasterxml.jackson.annotation.JsonFormat;
import jdk.jfr.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final ProjectService projectService;
    private final AuthenticationManager authenticate;
    private final MyUserDetailsService myUserDetailsService;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public UserController(UserService userService, AuthenticationManager authenticate, MyUserDetailsService myUserDetailsService, ProjectService projectService, JwtUtil jwtUtil, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.authenticate = authenticate;
        this.myUserDetailsService = myUserDetailsService;
        this.projectService = projectService;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        boolean isSuccess = userService.addNewUser(user);
        Map<String, String> response = new HashMap<>();
        if (isSuccess) {
            response.put("message", "new user created");
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        response.put("message", "user create failed");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


//    public ResponseEntity<?> login(@RequestBody User user) {
//        boolean isSuccess = userService.authenticate(user);
//        if (isSuccess) {
//            return new ResponseEntity<>("login successfully", HttpStatus.OK );
//        }
//        return new ResponseEntity<>("login failed", HttpStatus.UNAUTHORIZED);
//    }

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody User user) {

        logger.info("Login endpoint hit with user: {}", user.toString());
        try {
            authenticate.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getName(), user.getPassword())
            );

        } catch (AuthenticationException e) {
            return new ResponseEntity<>(e,HttpStatus.UNAUTHORIZED);
        }
        Role role = userService.getUser(user).getRole();
        String name = userService.getUser(user).getName();
        HttpHeaders headers = new HttpHeaders();
        String jwt;
        if(user.isRememberMe()){
            jwt  = jwtUtil.generateToken(user.getName(),role,604800000);
        }else{
            jwt = jwtUtil.generateToken(user.getName(),role,1800000);
        }
        headers.add("Authorization", "Bearer " + jwt);
        Map<String, String> response = new HashMap<>();
        response.put("message", "user login successfully");
        response.put("name", name);
        response.put("role", role.name());
        return  new ResponseEntity<>(response, headers, HttpStatus.OK);
    }

    @PostMapping("/updatePassword")
    public ResponseEntity<?> updatePwd(@RequestBody Map<String, String> requestBody) {
        String userName = requestBody.get("name");
        String newPwd = requestBody.get("password");
        String oldPwd = requestBody.get("OldPassword");
        boolean isSuccess = userService.resetPassword(userName, oldPwd, newPwd);
        Map<String, String> response = new HashMap<>();
        if (isSuccess) {
            response.put("message", "password updated successfully");
            return new ResponseEntity<>(response, HttpStatus.OK );
        }
        response.put("message", "update password failed");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @PostMapping("/updateRole")
    public ResponseEntity<?> updateRole(@RequestBody User user) {
        Role role = user.getRole();
        boolean isSuccess = userService.updateRole(user, role);
        Map<String, String> response = new HashMap<>();
        if (isSuccess) {
            response.put("message", "role updated successfully");
            return new ResponseEntity<>(response, HttpStatus.OK );
        }
        response.put("message", "update role failed");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUser(@RequestBody User user) {
        boolean isSuccess = userService.deleteUser(user);
        Map<String, String> response = new HashMap<>();
        if (isSuccess) {
            response.put("message", "user deleted successfully");
            return new ResponseEntity<>(response, HttpStatus.OK );
        }
        response.put("message", "delete user failed");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
