package com.example.KoreraProject.Service;

import com.example.KoreraProject.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {
    private UserRepository userRep;
    @Autowired
    public MyUserDetailsService(UserRepository userRep){
        this.userRep = userRep;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<com.example.KoreraProject.DatabaseModels.User> optional = userRep.findByName(username);
        if(optional.isEmpty()){
            throw new NoSuchElementException("User is not exist");
        }else{
            com.example.KoreraProject.DatabaseModels.User realUser= optional.get();
            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(realUser.getRole().toString()));
            return new User(realUser.getName(),realUser.getPassword(),authorities);
        }
    }
}
