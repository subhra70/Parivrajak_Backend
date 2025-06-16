package com.subhrashaw.ParivrajakBackend.controller;

import com.subhrashaw.ParivrajakBackend.DTO.LoginResponse;
import com.subhrashaw.ParivrajakBackend.model.User;
import com.subhrashaw.ParivrajakBackend.model.UserLoginRequest;
import com.subhrashaw.ParivrajakBackend.service.JwtService;
import com.subhrashaw.ParivrajakBackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private AuthenticationManager manager;
    @Autowired
    private JwtService jwtService;

    @PostMapping("/registerUser")
    public ResponseEntity<?> register(@RequestBody User user)
    {
        User temp=null;
        if(userService.getUser(user.getEmail())!=null)
        {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        user.setPassword(encoder.encode(user.getPassword()));
        user.setStatus(true);
        temp=userService.saveUser(user);
        if(temp==null)
        {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        else{
            return new ResponseEntity<>(temp,HttpStatus.CREATED);
        }
    }

    @PostMapping("/loginUser")
    public ResponseEntity<?> login(@RequestBody UserLoginRequest loginRequest) {
        try {
            Authentication authentication = manager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),loginRequest.getPassword())
            );

            if (authentication.isAuthenticated()) {
                String token = jwtService.generateToken(loginRequest.getEmail());
                User user=userService.getUser(loginRequest.getEmail());
                return new ResponseEntity<>(new LoginResponse(token,user.getUsername()),HttpStatus.OK);
            } else {
                System.out.println("Unsuccessfull1");
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
        } catch (AuthenticationException e) {
            System.out.println("Unsuccessfull2");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

}
