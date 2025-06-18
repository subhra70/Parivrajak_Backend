package com.subhrashaw.ParivrajakBackend.controller;

import com.subhrashaw.ParivrajakBackend.DTO.ExploreDTO;
import com.subhrashaw.ParivrajakBackend.model.*;
import com.subhrashaw.ParivrajakBackend.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
public class ProductController {
    @Autowired
    private JwtService jwtService;
    @Autowired
    private HotelService hotelService;
    @Autowired
    private OrgService orgService;
    @Autowired
    private ProductService productService;
    @Autowired
    private UserService userService;
    @PostMapping("product")
    public ResponseEntity<?> addProduct(@RequestPart("product") ProductRequest product,@RequestPart("destImg") MultipartFile banner, @RequestPart("imageFile1") MultipartFile image1,@RequestPart("imageFile2") MultipartFile image2,@RequestPart("imageFile3") MultipartFile image3,@RequestPart("imageFile4") MultipartFile image4,@RequestHeader("Authorization") String authHeader) throws IOException {
        if(authHeader==null || !authHeader.startsWith("Bearer "))
        {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        String token=authHeader.substring(7);
        String email=jwtService.extractUserName(token);
        if(!jwtService.validateToken(token, email))
        {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        Hotel hotelObj=hotelService.addHotel(image1,image2,image3,image4);
        if(hotelObj==null)
        {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        Organizer organizer =orgService.getOrganizer(email);
        if(organizer==null || !organizer.isStatus())
        {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        Product tempProduct=productService.addProduct(product,banner, organizer,hotelObj);
        if (tempProduct==null)
        {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @GetMapping("orgproducts")
    public ResponseEntity<?> postHistory(@RequestHeader("Authorization") String authHeader)
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
        int id=orgService.getId(email);
        if(id<1)
        {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        List<ProductResponse> productList=productService.getProducts(id);
        if (productList==null || productList.isEmpty())
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(productList,HttpStatus.OK);
    }

    @GetMapping("/productsFilter") //
    public ResponseEntity<?> getAllProducts(
            @RequestParam double startPrice,
            @RequestParam double endPrice,
            @RequestParam int startDay,
            @RequestParam int endDay
    ) {
        List<Product> productList = productService.getAllProducts(startPrice, endPrice, startDay, endDay);
        if (productList == null || productList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }
    @GetMapping("bannerImage/{id}")
    public ResponseEntity<?> fetchBanner(@PathVariable int id)
    {
        System.out.println("Image banner methode called");
        byte[] image=productService.getImageById(id);
        if(image==null)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else
        {
            return new ResponseEntity<>(image,HttpStatus.OK);
        }
    }
//    @GetMapping("prodImage/{id}")
//    public ResponseEntity<?> fetchProdBanner(@PathVariable int id)
//    {
//        byte[] image=productService.getImageById(id);
//        if(image==null)
//        {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//        else
//        {
//            return new ResponseEntity<>(image,HttpStatus.OK);
//        }
//    }

    @GetMapping("product/{id}")
    public ResponseEntity<?> getProduct(@PathVariable int id, @RequestHeader("Authorization") String authHeader)
    {
        if(authHeader==null || !authHeader.startsWith("Bearer "))
        {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        String token=authHeader.substring(7);
        String email= jwtService.extractUserName(token);
        if(!jwtService.validateToken(token, email))
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        ProductDetailsResponse product=productService.getProductById(id);
        if(product==null)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(product,HttpStatus.OK);
    }
    @PutMapping("product/{id}")
    public ResponseEntity<?> update(@PathVariable int id, @RequestHeader("Authorization") String authHeader,@RequestBody ProductRequest productRequest)
    {
        if(authHeader==null || !authHeader.startsWith("Bearer "))
        {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        String token=authHeader.substring(7);
        String email= jwtService.extractUserName(token);
        if(!jwtService.validateToken(token, email))
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Product product=productService.updateProduct(productRequest, id);
        if(product==null)
        {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(product,HttpStatus.OK);
    }
    @PutMapping("/productImages/{hotelId}/{id}")
    public ResponseEntity<?> updateImages(@PathVariable int id,@PathVariable int hotelId,@RequestHeader("Authorization") String authHeader,@RequestPart(value = "destImage",required = false) MultipartFile banner, @RequestPart(value = "image1",required = false) MultipartFile image1,@RequestPart(value = "image2",required = false) MultipartFile image2,@RequestPart(value = "image3",required = false) MultipartFile image3,@RequestPart(value = "image4",required = false) MultipartFile image4) throws IOException {
        if(authHeader==null || !authHeader.startsWith("Bearer "))
        {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        String token=authHeader.substring(7);
        String email= jwtService.extractUserName(token);
        if(!jwtService.validateToken(token, email)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Hotel obj=hotelService.update(image1,image2,image3,image4,hotelId);
        Product product=productService.updateBanner(id,banner);
        if(obj==null)
        {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if(product==null)
        {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @DeleteMapping("product/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable int id,@RequestHeader("Authorization") String authHeader)
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
        int status=productService.deleteProduct(id);
        if(status!=0)
        {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
