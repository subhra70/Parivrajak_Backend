package com.subhrashaw.ParivrajakBackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class OtpService {


    private Map<String, String> map=new HashMap<>();

    @Autowired
    private JavaMailSender mailSender;


    public void sendOtp(String email) {
        String otp = String.valueOf(new Random().nextInt(900000) + 100000);

        map.put("OTP:"+email,otp);


        // Send email
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Verification Mail");
        message.setText("Here is your OTP: " + otp + ". Use it to complete your verification.");
        mailSender.send(message);
    }

    public boolean verifyOtp(String email, String inputOtp) {
        String storedOtp = map.get("OTP:" + email);
        return storedOtp != null && storedOtp.equals(inputOtp);
    }

    public void clearOtp(String email) {
        map.remove("OTP:" + email);
    }
}


