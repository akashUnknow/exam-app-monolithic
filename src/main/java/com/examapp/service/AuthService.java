package com.examapp.service;

import com.examapp.model.User;
import com.examapp.repository.UserRepository;
import com.examapp.dto.*;
import com.examapp.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    @Autowired
    private OTPService otpService;
    
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setIsVerified(false);
        user = userRepository.save(user);
        
        String token = jwtUtil.generateToken(user.getId(), user.getEmail(), user.getRole());
        return new AuthResponse(token, user.getId().toString(), user.getName(), 
                              user.getEmail(), user.getRole());
    }
    
    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> new RuntimeException("Invalid credentials"));
        
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }
        
        String token = jwtUtil.generateToken(user.getId(), user.getEmail(), user.getRole());
        return new AuthResponse(token, user.getId().toString(), user.getName(), 
                              user.getEmail(), user.getRole());
    }
    
    public void sendOTP(String email) {
        otpService.sendOTP(email);
    }

    public AuthResponse verifyOTP(String email, String otp) {
        if (!otpService.verifyOTP(email, otp)) {
            throw new RuntimeException("Invalid or expired OTP");
        }

        User user = userRepository.findByEmail(email)
            .orElseGet(() -> {
                User newUser = new User();
                newUser.setEmail(email);
                newUser.setIsVerified(true);
                newUser.setName("User");
                return userRepository.save(newUser);
            });

        user.setIsVerified(true);
        userRepository.save(user);

        String token = jwtUtil.generateToken(user.getId(), user.getEmail(), user.getRole());
        return new AuthResponse(token, user.getId().toString(), user.getName(),
                              user.getEmail() != null ? user.getEmail() : "", user.getRole());
    }

    public void registerByOAuth2(OAuth2User principal) {
        String email = principal.getAttribute("email");
        if (email == null) {
            throw new RuntimeException("Email not found in OAuth2 user attributes");
        }

        userRepository.findByEmail(email).orElseGet(() -> {
            User newUser = new User();
            newUser.setEmail(email);
            newUser.setName(principal.getAttribute("name"));
            newUser.setIsVerified(true);
            return userRepository.save(newUser);
        });
    }


    public void updateUser(UpdateUser updateUser) {
        User user = userRepository.findByEmail(updateUser.getEmail())
            .orElseThrow(() -> new RuntimeException("User not found"));

        if (updateUser.getName() != null) {
            user.setName(updateUser.getName());
        }
        if (updateUser.getPhoneNumber() != null) {
            user.setPhoneNumber(updateUser.getPhoneNumber());
        }
        if (updateUser.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(updateUser.getPassword()));
        }
        if (updateUser.getIsVerified() != null) {
            user.setIsVerified(updateUser.getIsVerified());
        }

        userRepository.save(user);
    }
}