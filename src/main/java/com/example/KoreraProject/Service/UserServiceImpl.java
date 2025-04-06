package com.example.KoreraProject.Service;

import com.example.KoreraProject.DatabaseModels.*;
import com.example.KoreraProject.Repository.*;
import com.example.KoreraProject.util.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRep;
    private final ProjectRepository projectRep;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRep, ProjectRepository projectRep, PasswordEncoder passwordEncoder) {
        this.userRep = userRep;
        this.projectRep = projectRep;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public boolean addNewUser(User user) {
        String userName = user.getName();
        if (userName == null) {
            return false;
        } else {
            Optional<User> targetUser = userRep.findByName(userName);
            if (targetUser.isEmpty()) {
                try {
                    user.setPassword(passwordEncoder.encode(user.getPassword()));
                    userRep.save(user);
                } catch (Exception e) {
                    return false;
                }
            } else {
                return false;
            }
        }
        return true;
    }

    @Override
    public User getUser(User user) {
        String userName = user.getName();
        if (userName == null) {
            return null;
        } else {
            Optional<User> targetUser = userRep.findByName(userName);
            if (targetUser.isPresent()) {
                return targetUser.get();
            }
        }
        return null;
    }

    @Override
    public boolean deleteUser(User user) {
        String userName = user.getName();
        if (userName == null) {
            return false;
        } else {
            Optional<User> targetUser = userRep.findByName(userName);
            if (targetUser.isPresent()) {
                try {
                    // delete user
                    userRep.delete(targetUser.get());
                } catch (Exception e) {
                    return false;
                }
            } else {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean resetPassword(String userName, String oldPwd, String password) {
        Optional<User> targetUser = userRep.findByName(userName);
        if (targetUser.isPresent()) {
            try {
                User u = targetUser.get();
                if (passwordEncoder.matches(oldPwd, u.getPassword())) {
                    if (passwordEncoder.matches(password, u.getPassword())) {
                        return false;
                    } else {
                        u.setPassword(passwordEncoder.encode(password));
                        userRep.save(u);
                        return true;
                    }
                }
            } catch (Exception e) {
                return false;
            }
        }
        return false;
    }

    public boolean updateRole(User user, Role role) {
        String userName = user.getName();
        Optional<User> targetUser = userRep.findByName(userName);
        if (targetUser.isPresent()) {
            try {
                User u = targetUser.get();
                u.setRole(role);
                userRep.save(u);
                return true;
            } catch (Exception e) {
                return false;
            }
        }
        return false;
    }
}
