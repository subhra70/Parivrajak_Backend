package com.subhrashaw.ParivrajakBackend.dao;

import com.subhrashaw.ParivrajakBackend.model.History;
import com.subhrashaw.ParivrajakBackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PurchaseRepo extends JpaRepository<History, Integer> {
    Optional<History> findByUserId(User user);

}
