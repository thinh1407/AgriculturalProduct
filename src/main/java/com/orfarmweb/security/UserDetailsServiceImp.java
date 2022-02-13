package com.orfarmweb.security;


import com.orfarmweb.entity.User;
import com.orfarmweb.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailsServiceImp implements UserDetailsService {
    @Autowired
    private UserRepo repo;

    @Override
    public UserDetails loadUserByUsername(String email) {
        User user = repo.findUserByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("NOT FOUND");
        }
        return new CustomUserDetails(user);
    }
}