package com.subhrashaw.ParivrajakBackend.service;

import com.subhrashaw.ParivrajakBackend.dao.HistoryRepo;
import com.subhrashaw.ParivrajakBackend.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class HistoryService {

    @Autowired
    private HistoryRepo repo;

    public History saveProduct(User user, Product product) {
        History history = repo.findByUserId(user).orElse(null);

        if (history == null) {
            history = new History();
            history.setUserId(user);
            history.setProductStatuses(new ArrayList<>());

            ProductStatus status = new ProductStatus();
            status.setProduct(product);
//            status.setPurchased(false);
            status.setSaved(true);

            history.getProductStatuses().add(status);
        } else {
            boolean found = false;

            for (ProductStatus status : history.getProductStatuses()) {
                if (status.getProduct().getId() == product.getId()) {
                    status.setSaved(true);
                    found = true;
                    break;
                }
            }

            if (!found) {
                ProductStatus status = new ProductStatus();
                status.setProduct(product);
//                status.setPurchased(false);
                status.setSaved(true);

                history.getProductStatuses().add(status);
            }
        }

        return repo.save(history);
    }

    public History purchaseProduct(User user,Product product) {
        History history=repo.findByUserId(user).orElse(null);
        int flag=0;
        if(history==null)
        {
            history=new History();
            history.setUserId(user);
            history.setProductStatuses(new ArrayList<>());
            ProductStatus productStatus=new ProductStatus();
            productStatus.setProduct(product);
            productStatus.setSaved(false);
            productStatus.setPurchased(true);
            history.getProductStatuses().add(productStatus);
        }
        else{
            boolean found=false;
            for(ProductStatus status: history.getProductStatuses())
            {
                if(status.getProduct()==product)
                {
                    found=true;
                    status.setPurchased(true);
                    break;
                }
            }
            if(!found)
            {
                ProductStatus productStatus=new ProductStatus();
                productStatus.setProduct(product);
                productStatus.setSaved(false);
                productStatus.setPurchased(true);
                history.getProductStatuses().add(productStatus);
            }
        }
        return repo.save(history);
    }

    @Transactional
    public List<Product> getAllSavedProduct(User user) {
        return repo.getAllSavedProduct(user);
    }

    @Transactional
    public List<Product> getAllPurchasedProduct(User user) {
        return repo.getAllPurchasedProduct(user);
    }

    public int deleteSavedProduct(User user, Product product) {
        History history = repo.findByUserId(user).orElse(null);
        if (history != null && product != null) {
            List<ProductStatus> statuses = history.getProductStatuses();
            for (int i = 0; i < statuses.size(); i++) {
                ProductStatus ps = statuses.get(i);
                if (ps.getProduct() != null && ps.getProduct().getId() == product.getId()) {
                    ps.setSaved(false);
                    break;
                }
            }
            repo.save(history);
        }
        return 0;
    }



    public int deletePurchasedProduct(User user,Product product)
    {
        History history = repo.findByUserId(user).orElse(null);
        if (history != null && product != null) {
            List<ProductStatus> statuses = history.getProductStatuses();
            for (int i = 0; i < statuses.size(); i++) {
                ProductStatus ps = statuses.get(i);
                if (ps.getProduct() != null && ps.getProduct().getId() == product.getId()) {
                    ps.setPurchased(false);
                    break;
                }
            }
            repo.save(history);
        }
        return 0;
    }
}
