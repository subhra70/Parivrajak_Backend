package com.subhrashaw.ParivrajakBackend.controller;

import com.subhrashaw.ParivrajakBackend.DTO.PurchaseDTO;
import com.subhrashaw.ParivrajakBackend.model.*;
import com.subhrashaw.ParivrajakBackend.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@RestController
public class PurchaseController {
    @Autowired
    private JwtService jwtService;
    @Autowired
    private OrgService orgService;
    @Autowired
    private UserService userService;
    @Autowired
    private PurchaseService purchaseService;
    @Autowired
    private ProductService productService;
    @PostMapping("purchase")
    public ResponseEntity<?> purchaseProduct(@RequestBody PurchaseDTO purchaseDTO, @RequestHeader("Authorization") String authHeader)
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
        Organizer organizer= orgService.getOrganizer(purchaseDTO.getOrgId());
        User user=userService.getUser(email);
        Product product=productService.getProductItem(purchaseDTO.getDestId());
        if(organizer.getId()==-1)
        {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if(user==null)
        {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        String dateStr= purchaseDTO.getDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate localDate=LocalDate.parse(dateStr,formatter);
        int status=purchaseService.saveProduct(organizer,localDate,purchaseDTO,user,product);
        if(status==0)
        {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("historyProduct/{orgId}")
    public ResponseEntity<?> listHistory(@PathVariable int orgId,@RequestHeader("Authorization") String authHeader)
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
        Organizer organizer=orgService.getOrganizer(orgId);
        System.out.println("Method called");
        List<Purchase> purchaseList=purchaseService.getPurchaseList(organizer);
        if (purchaseList.size()==0)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        System.out.println("Method executed");
        return new ResponseEntity(purchaseList,HttpStatus.OK);
    }
}
