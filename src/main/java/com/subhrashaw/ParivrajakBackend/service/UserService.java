package com.subhrashaw.ParivrajakBackend.service;

import com.subhrashaw.ParivrajakBackend.dao.UserRepo;
import com.subhrashaw.ParivrajakBackend.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;

    public User saveUser(User user) {
        return userRepo.save(user);
    }

    public User getUser(String email) {
        return userRepo.findByEmail(email);
    }
}
