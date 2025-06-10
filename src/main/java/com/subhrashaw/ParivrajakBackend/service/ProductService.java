package com.subhrashaw.ParivrajakBackend.service;

import com.subhrashaw.ParivrajakBackend.dao.ProductRepo;
import com.subhrashaw.ParivrajakBackend.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepo productRepo;

    public Product addProduct(ProductRequest product, MultipartFile banner, Organizer organizer, Hotel hotel) throws IOException {
        List<String> types=product.getType();
        Product prod=new Product();
        prod.setMaxDays(product.getMaxDays());
        prod.setMinDays(product.getMinDays());
        prod.setTitle(product.getPtitle());
        prod.setPlace(product.getDestination());
        prod.setDestImgName(banner.getOriginalFilename());
        prod.setDestImgType(banner.getContentType());
        prod.setDestImg(banner.getBytes());
        prod.setPrice(product.getPrice());
        prod.setDestType(types);
        prod.setDiscount(product.getDiscount());
        prod.setRatings(4);
        prod.setHotelId(hotel);
        prod.setOrgId(organizer);
        return productRepo.save(prod);
    }

    @Transactional
    public List<ProductResponse> getProducts(int orgId) {
        List<Product> product= productRepo.findAllByOrgId_Id(orgId);
        List<ProductResponse> response=new ArrayList<>();
        for(Product prod:product)
        {
            response.add(new ProductResponse(prod.getId(), prod.getTitle(), prod.getPlace(), prod.getPrice()));
        }
        return response;
    }

    @Transactional
    public List<Product> getAllProducts(double startPrice, double endPrice, int startDay, int endDay) {
        if(startPrice==0 && endPrice==0 && startDay==0 && endDay==0)
        {
            return productRepo.findAll();
        }
        else if(startPrice==0 && endPrice==0 && startDay!=0 && endDay!=0)
        {
            return productRepo.findByDate(startDay, endDay);
        }
        else if(startPrice!=0 && endPrice!=0 && startDay==0 && endDay==0)
        {
            return productRepo.findByPrice(startPrice, endPrice);
        }
        return productRepo.findByPriceDate(startPrice,endPrice,startDay,endDay);
    }

    @Transactional
    public byte[] getImageById(int id) {
        return productRepo.getImageById(id);
    }

    public Optional<Product> getProduct(int id)
    {
        return productRepo.findById(id);
    }

    public ProductDetailsResponse getProductById(int id) {
        ProductDetailsResponse response= new ProductDetailsResponse();
        Product product=productRepo.findById(id).orElse(new Product(-1));
        if(product.getId()!=-1)
        {
            response.setId(product.getId());
            response.setTitle(product.getTitle());
            response.setPlace(product.getPlace());
            response.setPrice(product.getPrice());
            response.setDestType(product.getDestType());
            response.setDiscount(product.getDiscount());
            response.setMaxDays(product.getMaxDays());
            response.setMinDays(product.getMinDays());
            response.setRatings(product.getRatings());
            response.setHotelId(product.getHotelId().getHotelId());
            response.setOrgId(product.getOrgId().getId());
        }
        return response;
    }

}
