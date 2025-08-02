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
    List<Product> allProduct=new ArrayList<>();

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
        Product product1= productRepo.save(prod);
        refreshAllProducts();
        return product1;
    }

    private void refreshAllProducts()
    {
        allProduct=productRepo.findAll();
    }

    @Transactional
    public List<ProductResponse> getProducts(int orgId) {
        List<Product> product= new ArrayList<>();
        refreshAllProducts();
        for(Product prod:allProduct)
        {
            if(prod.getOrgId().getId()==orgId)
            {
                product.add(prod);
            }
        }
        List<ProductResponse> response=new ArrayList<>();
        for(Product prod:product)
        {
            response.add(new ProductResponse(prod.getId(), prod.getTitle(), prod.getPlace(), prod.getPrice()));
        }
        return response;
    }
    @Transactional
    public Product getProductItem(int id)
    {
        return productRepo.findById(id).orElse(new Product(-1));
    }

    @Transactional
    public List<Product> getAllProducts() {
//        if(startPrice==0 && endPrice==0 && startDay==0 && endDay==0)
//        {
//            if(allProduct.isEmpty())
//            {
//                allProduct=productRepo.findAll();
//            }
//            return allProduct;
//        }
//        else if(startPrice==0 && endPrice==0 && startDay!=0 && endDay!=0)
//        {
//            List<Product> filteredProduct=new ArrayList<>();
//            for(Product p:allProduct)
//            {
//                if((p.getMinDays()<=startDay && p.getMaxDays()>=startDay)||(p.getMinDays()<=endDay && p.getMaxDays()>=endDay))
//                {
//                    filteredProduct.add(p);
//                }
//            }
//            return filteredProduct;
//        }
//        else if(startPrice!=0 && endPrice!=0 && startDay==0 && endDay==0)
//        {
//            List<Product> filteredProduct=new ArrayList<>();
//            for(Product p:allProduct)
//            {
//                if(p.getPrice()>=startPrice && p.getPrice()<=endPrice)
//                {
//                    filteredProduct.add(p);
//                }
//            }
//            return filteredProduct;
//        }
//        List<Product> filteredProduct=new ArrayList<>();
//        for(Product p:allProduct)
//        {
//            if((p.getPrice()>=startPrice && p.getPrice()<=endPrice)&&(p.getMinDays()<=startDay && p.getMaxDays()>=startDay)||(p.getMinDays()<=endDay && p.getMaxDays()>=endDay))
//            {
//                filteredProduct.add(p);
//            }
//        }
//        return filteredProduct;
        return productRepo.findAll();
    }

    @Transactional
    public byte[] getImageById(int id) {
        return productRepo.getImageById(id);
    }

    public Product getProduct(int id)
    {
        return productRepo.findById(id).orElse(new Product(-1));
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

    public Product updateProduct(ProductRequest product, int id) {
        Product prod=productRepo.findById(id).orElse(new Product(-1));
        if(prod.getId()!=-1)
        {
            prod.setTitle(product.getPtitle());
            prod.setDiscount(product.getDiscount());
            prod.setPrice(product.getPrice());
            prod.setPlace(product.getDestination());
            prod.setDestType(product.getType());
            prod.setMinDays(product.getMinDays());
            prod.setMaxDays(product.getMaxDays());
            Product product1= productRepo.save(prod);
            refreshAllProducts();
            return product1;
        }
        return prod;
    }

    public Product updateBanner(int id,MultipartFile banner) throws IOException {
        Product product=productRepo.findById(id).orElse(null);
        if(product!=null)
        {
            if(banner!=null) {
                product.setDestImgName(banner.getOriginalFilename());
                product.setDestImg(banner.getBytes());
                product.setDestImgType(banner.getContentType());
            }
            Product product1= productRepo.save(product);
            refreshAllProducts();
            return product1;
        }
        return product;
    }

    public int deleteProduct(int id) {
        productRepo.deleteById(id);
        refreshAllProducts();
        return 0;
    }

}
