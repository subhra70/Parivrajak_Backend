package com.subhrashaw.ParivrajakBackend.dao;

import com.subhrashaw.ParivrajakBackend.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRepo extends JpaRepository<Hotel,Integer> {
}
