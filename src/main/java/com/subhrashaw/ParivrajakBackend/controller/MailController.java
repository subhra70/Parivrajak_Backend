package com.subhrashaw.ParivrajakBackend.controller;

import com.subhrashaw.ParivrajakBackend.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MailController {

    @Autowired
    private EmailService emailService;

    @GetMapping("/sendMail")
    public String sendMail(@RequestParam String to) {
        emailService.sendSimpleMail(to, "Test Mail", "This is a test mail from Spring Boot.");
        return "Mail sent successfully!";
    }
}

