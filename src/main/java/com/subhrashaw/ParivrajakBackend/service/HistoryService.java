package com.subhrashaw.ParivrajakBackend.service;

import com.subhrashaw.ParivrajakBackend.dao.HistoryRepo;
import com.subhrashaw.ParivrajakBackend.dao.HotelRepo;
import com.subhrashaw.ParivrajakBackend.dao.ProductRepo;
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
    @Autowired
    private HotelRepo hotelRepo;
    @Autowired
    private ProductRepo productRepo;

    public History saveProduct(User user, Product product) {
        History history = repo.findByUserId(user).orElse(null);

        if (history == null) {
            history = new History();
            history.setUserId(user);
            history.setProductStatuses(new ArrayList<>());

            ProductStatus status = new ProductStatus();
            status.setProduct(product.getId());
//            status.setPurchased(false);
            status.setSaved(true);

            history.getProductStatuses().add(status);
        } else {
            boolean found = false;

            for (ProductStatus status : history.getProductStatuses()) {
                if (status.getProduct() == product.getId()) {
                    status.setSaved(true);
                    found = true;
                    break;
                }
            }

            if (!found) {
                ProductStatus status = new ProductStatus();
                status.setProduct(product.getId());
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
            productStatus.setProduct(product.getId());
            productStatus.setSaved(false);
            productStatus.setPurchased(true);
            history.getProductStatuses().add(productStatus);
        }
        else{
            boolean found=false;
            for(ProductStatus status: history.getProductStatuses())
            {
                if(status.getProduct()==product.getId())
                {
                    found=true;
                    status.setPurchased(true);
                    break;
                }
            }
            if(!found)
            {
                ProductStatus productStatus=new ProductStatus();
                productStatus.setProduct(product.getId());
                productStatus.setSaved(false);
                productStatus.setPurchased(true);
                history.getProductStatuses().add(productStatus);
            }
        }
        return repo.save(history);
    }

    @Transactional
    public List<Product> getAllSavedProduct(User user) {
        List<Integer> pids=repo.getAllSavedProduct(user);
        List<Product> productList=new ArrayList<>();
        for(Integer i:pids)
        {
            Product product=productRepo.findById(i).orElse(new Product(-1));
            if(product.getId()!=-1)
            {
                productList.add(product);
            }
        }
        return productList;
    }

    @Transactional
    public List<Product> getAllPurchasedProduct(User user) {
        List<Integer> pids=repo.getAllPurchasedProduct(user);
        List<Product> productList=new ArrayList<>();
        for(Integer i:pids)
        {
            Product product=productRepo.findById(i).orElse(new Product(-1));
            if(product.getId()!=-1)
            {
                productList.add(product);
            }
        }
        return productList;
    }

    public int deleteSavedProduct(User user, Product product) {
        History history = repo.findByUserId(user).orElse(null);
        if (history != null && product != null) {
            List<ProductStatus> statuses = history.getProductStatuses();
            for (int i = 0; i < statuses.size(); i++) {
                ProductStatus ps = statuses.get(i);
                if (ps.getProduct() == product.getId()) {
                    ps.setSaved(false);
                    break;
                }
            }
            repo.save(history);
        }
        return 0;
    }


    @Transactional
    public int deletePurchasedProduct(User user,Product product)
    {
        History history = repo.findByUserId(user).orElse(null);
        if (history != null && product != null) {
            List<ProductStatus> statuses = history.getProductStatuses();
            for (int i = 0; i < statuses.size(); i++) {
                ProductStatus ps = statuses.get(i);
                if (ps.getProduct() == product.getId()) {
                    ps.setPurchased(false);
                    break;
                }
            }
            repo.save(history);
        }
        return 0;
    }

    public void deleteHotel(Hotel hotel) {
        if(hotel==null)
        {
            return;
        }
        hotelRepo.delete(hotel);
    }
}
