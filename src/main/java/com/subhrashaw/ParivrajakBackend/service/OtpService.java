package com.subhrashaw.ParivrajakBackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Random;

@Service
public class OtpService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private JavaMailSender mailSender;

    // Set expiry time here
    private final Duration OTP_TTL = Duration.ofMinutes(5); // 5 minutes

    public void sendOtp(String email) {
        String otp = String.valueOf(new Random().nextInt(900000) + 100000);

        // Store OTP with expiry
        redisTemplate.opsForValue().set("OTP:" + email, otp, OTP_TTL);

        // Send email
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Your OTP for Email Verification");
        message.setText("Your OTP is: " + otp + ". It will expire in 5 minutes.");
        mailSender.send(message);
    }

    public boolean verifyOtp(String email, String inputOtp) {
        String storedOtp = redisTemplate.opsForValue().get("OTP:" + email);
        return storedOtp != null && storedOtp.equals(inputOtp);
    }

    public void clearOtp(String email) {
        redisTemplate.delete("OTP:" + email);
    }
}


