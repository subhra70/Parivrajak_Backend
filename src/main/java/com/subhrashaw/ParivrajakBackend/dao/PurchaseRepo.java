package com.subhrashaw.ParivrajakBackend.dao;

import com.subhrashaw.ParivrajakBackend.model.Organizer;
import com.subhrashaw.ParivrajakBackend.model.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface PurchaseRepo extends JpaRepository<Purchase,Integer> {
    @Query("SELECT p FROM Purchase p WHERE p.orgId= :orgId")
    List<Purchase> findAllByOrgId(@Param("orgId") Organizer orgId);
}
