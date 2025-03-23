package com.example.demo.user.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @GetMapping("/me")
    public ResponseEntity<Map<String, String>> user(@AuthenticationPrincipal OAuth2User principal) {
        return ResponseEntity.ok(Map.of(
                "sub", principal.getAttribute("sub"),
                "name", principal.getAttribute("name"),
                "xero_userid", principal.getAttribute("xero_userid"),
                "email", principal.getAttribute("email")
        ));
    }

}
