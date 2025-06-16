package com.subhrashaw.ParivrajakBackend.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class History {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User userId;

    @ElementCollection
    @CollectionTable(name = "history_product_status", joinColumns = @JoinColumn(name = "history_id"))
    private List<ProductStatus> productStatuses;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public List<ProductStatus> getProductStatuses() {
        return productStatuses;
    }

    public void setProductStatuses(List<ProductStatus> productStatuses) {
        this.productStatuses = productStatuses;
    }
}
