package com.example.notes.notes.controller;

import com.example.notes.notes.entity.AuthRequest;
import com.example.notes.notes.entity.UserInfo;
import com.example.notes.notes.service.JwtService;
import com.example.notes.notes.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class UserController {
    @Autowired
    private UserInfoService service;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome this endpoint is not secure";
    }

    @PostMapping("/addNewUser")
    public ResponseEntity<String> addNewUser(@RequestBody UserInfo userInfo) {
        return ResponseEntity.ok().body(service.addUser(userInfo));
    }

    @GetMapping("/user/userProfile")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<String> userProfile() {
        System.out.println("Welcome to User Profile");
        return ResponseEntity.ok().body("Welcome to User Profile");
    }

    @GetMapping("/admin/adminProfile")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<String> adminProfile() {
        return ResponseEntity.ok("Welcome to Admin Profile");
    }

    @PostMapping("/generateToken")
    public ResponseEntity<String> authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            return ResponseEntity.ok().body(jwtService.generateToken(authRequest.getUsername()));
        } else {
            throw new UsernameNotFoundException("invalid user request !");
        }
    }
}
