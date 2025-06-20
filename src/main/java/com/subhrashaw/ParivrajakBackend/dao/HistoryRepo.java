package com.subhrashaw.ParivrajakBackend.dao;

import com.subhrashaw.ParivrajakBackend.model.History;
import com.subhrashaw.ParivrajakBackend.model.Product;
import com.subhrashaw.ParivrajakBackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface HistoryRepo extends JpaRepository<History, Integer> {
    Optional<History> findByUserId(User user);

    @Query("SELECT ps.product FROM History h JOIN h.productStatuses ps WHERE ps.saved = true AND h.userId = :userId")
    List<Product> getAllSavedProduct(@Param("userId") User userId);

    @Query("SELECT ps.product FROM History h JOIN h.productStatuses ps WHERE ps.purchased = true AND h.userId = :userId")
    List<Product> getAllPurchasedProduct(@Param("userId") User userId);

}
