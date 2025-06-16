package com.subhrashaw.ParivrajakBackend.controller;

import com.subhrashaw.ParivrajakBackend.DTO.LoginResponse;
import com.subhrashaw.ParivrajakBackend.model.*;
import com.subhrashaw.ParivrajakBackend.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class OrgController {
    @Autowired
    private OrgService service;
    @Autowired
    private HotelService hotelService;
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private AuthenticationManager manager;
    @Autowired
    private JwtService jwtService;
    @Autowired
    @Qualifier("MyOrganizationDetailedService")
    private UserDetailsService userDetailsService;

    @PostMapping("registerOrganizer")
    public ResponseEntity<?> register(@RequestBody Organizer org)
    {
        try {
            org.setStatus(true);
            if(service.getOrganizer(org.getEmail())!=null)
            {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
            org.setPassword(encoder.encode(org.getPassword()));
            User user=null;
            if(userService.getUser(org.getEmail())==null)
            {
                user=userService.saveUser(new User(org.getUsername(),org.getEmail(),org.getPassword()));
            }
            else{
                user=userService.getUser(org.getEmail());
                user.setPassword(org.getPassword());
                userService.saveUser(user);
            }
            if(service.saveOrganizer(org)!=null || user!=null)
            {
                return new ResponseEntity<>(HttpStatus.CREATED);
            }
            else
            {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (AuthenticationException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @DeleteMapping("organizer")
    public ResponseEntity<?> delete(@RequestBody String email)
    {
        if(email==null)
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        service.deleteAccount(email);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("orgLogin")
    public ResponseEntity<?> login(@RequestBody OrgLoginRequest loginRequest) {
        try {
            Authentication authentication = manager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
            );
            if (authentication.isAuthenticated()) {
                Organizer organizer = service.getOrganizer(loginRequest.getEmail());
                if (organizer != null && organizer.isStatus()) {
                    String token = jwtService.generateToken(loginRequest.getEmail());
                    return new ResponseEntity<>(new LoginResponse(token, organizer.getUsername()),HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
                }
            } else {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
        } catch (AuthenticationException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("organizer")
    public ResponseEntity<?> findOrganizer(@RequestHeader("Authorization") String authHeader)
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
        Organizer organizer= service.getOrganizer(email);
        if(organizer==null || !organizer.isStatus())
        {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(organizer,HttpStatus.OK);
    }

    @GetMapping("organizer/{orgId}")
    public ResponseEntity<?> getOrganizer(@PathVariable int orgId,@RequestHeader("Authorization") String authHeader)
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
        Optional<Organizer> organizer= service.getOrganizer(orgId);
        if(organizer.isEmpty())
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(organizer,HttpStatus.OK);
    }

    @GetMapping("hotelImages/{hotelId}")
    public ResponseEntity<?> getHotelImages(@PathVariable int hotelId,@RequestHeader("Authorization") String authHeader)
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
        Hotel hotel = hotelService.findById(hotelId).orElse(null);
        if (hotel == null) return ResponseEntity.notFound().build();

        List<Map<String, String>> images = new ArrayList<>();

        if (hotel.getImgData1() != null) {
            images.add(Map.of(
                    "data", Base64.getEncoder().encodeToString(hotel.getImgData1()),
                    "type", hotel.getImgType1()
            ));
        }

        if (hotel.getImgData2() != null) {
            images.add(Map.of(
                    "data", Base64.getEncoder().encodeToString(hotel.getImgData2()),
                    "type", hotel.getImgType2()
            ));
        }

        if (hotel.getImgData3() != null) {
            images.add(Map.of(
                    "data", Base64.getEncoder().encodeToString(hotel.getImgData3()),
                    "type", hotel.getImgType3()
            ));
        }

        if (hotel.getImgData4() != null) {
            images.add(Map.of(
                    "data", Base64.getEncoder().encodeToString(hotel.getImgData4()),
                    "type", hotel.getImgType4()
            ));
        }

        return new ResponseEntity<>(images,HttpStatus.OK);
    }

    @PutMapping("organizer")
    public ResponseEntity<?> updateOrganizer(@RequestHeader("Authorization") String authHeader, @RequestBody UpdateOrganizerRequest request)
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
        Organizer organizer= service.getOrganizer(email);
        if(organizer==null || !organizer.isStatus())
        {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        organizer.setUsername(request.getUsername());
        organizer.setPhone(request.getPhone());
        organizer.setLocation(request.getLocation());
        organizer.setOrganization(request.getOrganization());
        organizer.setEmail(email);
        Organizer org=service.saveOrganizer(organizer);
        if(org==null || !org.isStatus())
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
