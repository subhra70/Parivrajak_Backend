package com.subhrashaw.ParivrajakBackend.controller;

import com.subhrashaw.ParivrajakBackend.DTO.HistoryDTO;
import com.subhrashaw.ParivrajakBackend.model.History;
import com.subhrashaw.ParivrajakBackend.model.Product;
import com.subhrashaw.ParivrajakBackend.model.ProductDetailsResponse;
import com.subhrashaw.ParivrajakBackend.model.User;
import com.subhrashaw.ParivrajakBackend.service.JwtService;
import com.subhrashaw.ParivrajakBackend.service.ProductService;
import com.subhrashaw.ParivrajakBackend.service.PurchaseService;
import com.subhrashaw.ParivrajakBackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
public class PurchaseController {
    @Autowired
    private PurchaseService service;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserService userService;
    @Autowired
    private PurchaseService purchaseService;
    @Autowired
    private ProductService productService;

    @PostMapping("saveProduct")
    public ResponseEntity<?> addProduct(@RequestBody HistoryDTO history, @RequestHeader("Authorization") String authHeader)
    {
        if (authHeader==null || !authHeader.startsWith("Bearer "))
        {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        String token=authHeader.substring(7);
        String email=jwtService.extractUserName(token);
        if(!jwtService.validateToken(token, email))
        {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        User user=userService.getUser(email);
        Product product=productService.getProduct(history.getDestId()).orElse(null);
        History history1=purchaseService.saveProduct(user,product);
        if(history1==null)
        {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(history1,HttpStatus.OK);
    }
    @PostMapping("purchase")
    public ResponseEntity<?> purchaseProduct(@RequestBody HistoryDTO historyDTO,@RequestHeader("Authorization") String authHeader)
    {
        if (authHeader==null || !authHeader.startsWith("Bearer "))
        {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        String token=authHeader.substring(7);
        String email=jwtService.extractUserName(token);
        if(!jwtService.validateToken(token, email))
        {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        User user=userService.getUser(email);
        Product product=productService.getProduct(historyDTO.getDestId()).orElse(null);
        History history=purchaseService.purchaseProduct(user,product);
        if(history==null)
        {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(history,HttpStatus.OK);
    }

    @GetMapping("savedProduct")
    public ResponseEntity<?> getSavedProducts(@RequestHeader("Authorization") String authHeader)
    {
        if (authHeader==null || !authHeader.startsWith("Bearer "))
        {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        String token=authHeader.substring(7);
        String email=jwtService.extractUserName(token);
        if(!jwtService.validateToken(token, email))
        {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        User user=userService.getUser(email);
        List<Product> product=purchaseService.getAllSavedProduct(user);
        if(product.size()==0)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(product,HttpStatus.OK);
    }
    @GetMapping("purchasedProduct")
    public ResponseEntity<?> getPurchasedProducts(@RequestHeader("Authorization") String authHeader)
    {
        if (authHeader==null || !authHeader.startsWith("Bearer "))
        {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        String token=authHeader.substring(7);
        String email=jwtService.extractUserName(token);
        if(!jwtService.validateToken(token, email))
        {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        User user=userService.getUser(email);
        List<Product> product=purchaseService.getAllPurchasedProduct(user);
        if(product.size()==0)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        System.out.println("Done");
        return new ResponseEntity<>(product,HttpStatus.OK);
    }
}
