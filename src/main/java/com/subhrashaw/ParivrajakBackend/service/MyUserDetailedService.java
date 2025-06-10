package com.subhrashaw.ParivrajakBackend.service;

import com.subhrashaw.ParivrajakBackend.dao.UserRepo;
import com.subhrashaw.ParivrajakBackend.model.User;
import com.subhrashaw.ParivrajakBackend.model.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Primary
public class MyUserDetailedService implements UserDetailsService {

    @Autowired
    private UserRepo repo;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = null;
        try {
            user = repo.findByEmail(email);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (user == null) {
            System.out.println("User not found");
            throw new UsernameNotFoundException("404 not found");
        }
        System.out.println("Returning UserPrincipal");
        return new UserPrincipal(user);
    }


}
