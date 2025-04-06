package com.example.KoreraProject.Service;

import com.example.KoreraProject.DatabaseModels.*;
import com.example.KoreraProject.util.Role;

public interface UserService {
    public boolean addNewUser(User user);
    public User getUser(User user);
    public boolean deleteUser(User user);
    public boolean resetPassword(String userName, String oldPwd, String password);
    public boolean updateRole(User user, Role role);
}
