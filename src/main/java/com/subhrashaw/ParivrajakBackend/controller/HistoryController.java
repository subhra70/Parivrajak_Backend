package com.subhrashaw.ParivrajakBackend.controller;

import com.subhrashaw.ParivrajakBackend.DTO.HistoryDTO;
import com.subhrashaw.ParivrajakBackend.model.History;
import com.subhrashaw.ParivrajakBackend.model.Hotel;
import com.subhrashaw.ParivrajakBackend.model.Product;
import com.subhrashaw.ParivrajakBackend.model.User;
import com.subhrashaw.ParivrajakBackend.service.JwtService;
import com.subhrashaw.ParivrajakBackend.service.ProductService;
import com.subhrashaw.ParivrajakBackend.service.HistoryService;
import com.subhrashaw.ParivrajakBackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@CrossOrigin("https://parivrajak.vercel.app")
public class HistoryController {
    @Autowired
    private HistoryService service;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserService userService;
    @Autowired
    private HistoryService historyService;
    @Autowired
    private ProductService productService;

    @PostMapping("saveProduct")
    public ResponseEntity<?> addProduct(@RequestBody HistoryDTO history, @RequestHeader("Authorization") String authHeader)
    {
        System.out.println("Method called");
        if (authHeader==null || !authHeader.startsWith("Bearer "))
        {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        String token=authHeader.substring(7);
        String email=jwtService.extractUserName(token);
        if(!jwtService.validateToken(token, email))
        {
            System.out.println("Method failed1");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        User user=userService.getUser(email);
        Product product=productService.getProduct(history.getDestId());
        History history1=historyService.saveProduct(user,product);
        if(history1==null)
        {
            System.out.println("Method failed2");
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        System.out.println("Method successfull");
        return new ResponseEntity<>(history1,HttpStatus.OK);
    }
//    @PostMapping("purchase")
//    public ResponseEntity<?> purchaseProduct(@RequestBody HistoryDTO historyDTO,@RequestHeader("Authorization") String authHeader)
//    {
//        if (authHeader==null || !authHeader.startsWith("Bearer "))
//        {
//            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//        }
//        String token=authHeader.substring(7);
//        String email=jwtService.extractUserName(token);
//        if(!jwtService.validateToken(token, email))
//        {
//            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//        }
//        User user=userService.getUser(email);
//        Product product=productService.getProduct(historyDTO.getDestId()).orElse(null);
//        History history=purchaseService.purchaseProduct(user,product);
//        if(history==null)
//        {
//            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
//        }
//        return new ResponseEntity<>(history,HttpStatus.OK);
//    }

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
        List<Product> product=historyService.getAllSavedProduct(user);
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
        List<Product> product=historyService.getAllPurchasedProduct(user);
        if(product.size()==0)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        System.out.println("Done");
        return new ResponseEntity<>(product,HttpStatus.OK);
    }
    @DeleteMapping("deleteSavedProduct/{id}")
    public ResponseEntity<HttpStatus> deleteSavedProduct(@PathVariable int id,@RequestHeader("Authorization") String authHeader)
    {
        if(authHeader==null || !authHeader.startsWith("Bearer "))
        {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        String token=authHeader.substring(7);
        String email= jwtService.extractUserName(token);
        if(!jwtService.validateToken(token, email)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        System.out.println("Method called");
        User user=userService.getUser(email);
        Product product=productService.getProductItem(id);
        Hotel hotel= product.getHotelId();
        int status=historyService.deleteSavedProduct(user,product);
        historyService.deleteHotel(hotel);
        if(status!=0)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        System.out.println("Completed");
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @DeleteMapping("deletePurchasedProduct/{id}")
    public ResponseEntity<HttpStatus> deletePurchasedProduct(@PathVariable int id,@RequestHeader("Authorization") String authHeader)
    {
        if(authHeader==null || !authHeader.startsWith("Bearer "))
        {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        String token=authHeader.substring(7);
        String email= jwtService.extractUserName(token);
        if(!jwtService.validateToken(token, email)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        System.out.println("Method called");
        User user=userService.getUser(email);
        Product product=productService.getProductItem(id);
        int status=historyService.deletePurchasedProduct(user,product);
        if(status!=0)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        System.out.println("Completed");
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
