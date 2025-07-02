package com.subhrashaw.ParivrajakBackend.config;

import com.subhrashaw.ParivrajakBackend.model.User;
import com.subhrashaw.ParivrajakBackend.service.JwtService;
import com.subhrashaw.ParivrajakBackend.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomOAuth2SuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserService userService;
    @Value("${frontend.url}")
    private String frontendURL;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, IOException {
        OAuth2User user = (OAuth2User) authentication.getPrincipal();
        String email = user.getAttribute("email");
        System.out.println("Invoked handler");

        String jwt = jwtService.generateToken(email);
        User user1=userService.getUser(email);
        String redirectUrl = frontendURL+"/login?token=" + jwt+"&username="+user1.getUsername();

        response.sendRedirect(redirectUrl);
    }
}

