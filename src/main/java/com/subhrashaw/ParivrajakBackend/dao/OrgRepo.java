package com.subhrashaw.ParivrajakBackend.dao;

import com.subhrashaw.ParivrajakBackend.model.Organizer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrgRepo extends JpaRepository<Organizer,Integer> {
    Organizer findByEmail(String email);

    @Query("SELECT o.id FROM Organizer o WHERE o.email = :email")
    int findIdByEmail(@Param("email") String email);

    void deleteByEmail(String email);
}
