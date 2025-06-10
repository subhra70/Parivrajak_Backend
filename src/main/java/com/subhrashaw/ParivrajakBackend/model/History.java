package com.subhrashaw.ParivrajakBackend.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class History {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToMany
    @JoinTable(
            name = "history_products",
            joinColumns = @JoinColumn(name = "history_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> destId;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User userId;

    @ElementCollection
    private List<Boolean> purchaseStatus;

    @ElementCollection
    private List<Boolean> saveStatus;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Product> getDestId() {
        return destId;
    }

    public void setDestId(List<Product> destId) {
        this.destId = destId;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public List<Boolean> getPurchaseStatus() {
        return purchaseStatus;
    }

    public void setPurchaseStatus(List<Boolean> purchaseStatus) {
        this.purchaseStatus = purchaseStatus;
    }

    public List<Boolean> getSaveStatus() {
        return saveStatus;
    }

    public void setSaveStatus(List<Boolean> saveStatus) {
        this.saveStatus = saveStatus;
    }
}
