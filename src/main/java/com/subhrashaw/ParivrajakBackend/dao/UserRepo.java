package com.subhrashaw.ParivrajakBackend.dao;

import com.subhrashaw.ParivrajakBackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User,Integer> {
    User findByUsername(String username);
    User findByEmail(String email);
}
