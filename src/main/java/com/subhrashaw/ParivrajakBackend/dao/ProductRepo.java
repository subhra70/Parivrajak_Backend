package com.subhrashaw.ParivrajakBackend.dao;

import com.subhrashaw.ParivrajakBackend.model.Product;
import com.subhrashaw.ParivrajakBackend.model.ProductRequest;
import com.subhrashaw.ParivrajakBackend.model.ProductResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepo extends JpaRepository<Product,Integer> {

    List<Product> findAllByOrgId_Id(int id);

    @Query("SELECT p FROM Product p WHERE p.price BETWEEN :startPrice AND :endPrice AND (p.minDays <= :startDay AND p.maxDays>= :startDay) OR (p.minDays <= :endDay AND p.maxDays>= :endDay)")
    List<Product> findByPriceDate(@Param("startPrice") double startPrice,
                                  @Param("endPrice") double endPrice,
                                  @Param("startDay") int startDay,
                                  @Param("endDay") int endDay);
    @Query("SELECT p FROM Product p WHERE p.price BETWEEN :startPrice AND :endPrice")
    List<Product> findByPrice(@Param("startPrice") double startPrice,
                              @Param("endPrice") double endPrice);
    @Query("SELECT p FROM Product p WHERE (p.minDays <= :startDay AND p.maxDays>= :startDay) OR (p.minDays <= :endDay AND p.maxDays>= :endDay)")
    List<Product> findByDate(@Param("startDay") int startDay,
                             @Param("endDay") int endDay);


    @Query("SELECT p.destImg FROM Product p WHERE p.id = :id")
    byte[] getImageById(@Param("id") int id);
}
