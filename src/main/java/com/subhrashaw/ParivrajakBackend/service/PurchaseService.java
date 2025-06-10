package com.subhrashaw.ParivrajakBackend.service;

import com.subhrashaw.ParivrajakBackend.dao.PurchaseRepo;
import com.subhrashaw.ParivrajakBackend.model.History;
import com.subhrashaw.ParivrajakBackend.model.Product;
import com.subhrashaw.ParivrajakBackend.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PurchaseService {

    @Autowired
    private PurchaseRepo repo;

    public History saveProduct(User user, Product product) {
        History history = repo.findByUserId(user).orElse(null);

        if (history == null) {
            // Create new History if not found
            history = new History();
            history.setUserId(user);
            history.setDestId(new ArrayList<>());
            history.setPurchaseStatus(new ArrayList<>());
            history.setSaveStatus(new ArrayList<>());
        }

        history.getDestId().add(product);
        history.getPurchaseStatus().add(false);  // or whatever logic you want
        history.getSaveStatus().add(true);       // or whatever logic you want

        // Save and return updated history
        return repo.save(history);
    }

    public History purchaseProduct(User user,Product product) {
        History history=repo.findByUserId(user).orElse(null);
        if(history!=null)
        {
            List<Product> products=history.getDestId();
            List<Boolean> purchaseStatus=history.getPurchaseStatus();
            int idx=-1;
            for(int i=0;i< products.size();i++)
            {
                if(products.get(i).getId()== product.getId())
                {
                    idx=i;
                    break;
                }
            }
            purchaseStatus.set(idx,true);
        }
        return history;
    }

//    public List<Product> getAllProductId(User user) {
//        return repo.
//    }
}
