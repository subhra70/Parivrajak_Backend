package com.subhrashaw.ParivrajakBackend.service;

import com.subhrashaw.ParivrajakBackend.DTO.PurchaseDTO;
import com.subhrashaw.ParivrajakBackend.dao.PurchaseRepo;
import com.subhrashaw.ParivrajakBackend.model.Organizer;
import com.subhrashaw.ParivrajakBackend.model.Product;
import com.subhrashaw.ParivrajakBackend.model.Purchase;
import com.subhrashaw.ParivrajakBackend.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PurchaseService {
    @Autowired
    private PurchaseRepo purchaseRepo;
    @Autowired
    private HistoryService historyService;

    public int saveProduct(Organizer organizer, LocalDate localDate, PurchaseDTO purchaseDTO, User user, Product product) {
        Purchase purchase=new Purchase();
        purchase.setOrgId(organizer);
        purchase.setDate(localDate);
        purchase.setAmount(purchaseDTO.getAmount());
        purchase.setPlace(purchaseDTO.getPlace());
        purchase.setUser(user);
        purchaseRepo.save(purchase);
        historyService.purchaseProduct(user,product);
        return 0;
    }

    public List<Purchase> getPurchaseList(Organizer orgId) {
        return purchaseRepo.findAllByOrgId(orgId);
    }
}
