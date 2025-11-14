package com.examapp.controller;
import com.examapp.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class GoogleOAuthController {

    @Autowired
    private AuthService authService;

    @GetMapping("/user-info")
    public Map<String, Object> userInfo(@AuthenticationPrincipal OAuth2User principal) {
        if (principal==null) {
            throw new RuntimeException("User is not authenticated");
        }
        authService.registerByOAuth2(principal);

//        return principal.getAttributes();
        return Map.of(
                "name", principal.getAttribute("name"),
                "email", principal.getAttribute("email"),
                "email_verified", principal.getAttribute("email_verified"),
                "userId", authService.getUserIdByEmail(principal.getAttribute("email"))
        );

    }

}