package com.bidsphere.controller;

import com.bidsphere.dto.LoginRequest;
import com.bidsphere.dto.RegisterRequest;
import com.bidsphere.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService){
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegisterRequest request){
        String result = authService.registerUser(request);
        if(result.startsWith("Error")){
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request){
        try{
            String username = request.getUsername();
            String password = request.getPassword();

            String jwtToken = authService.login(username, password);

            return ResponseEntity.ok(Map.of("token", jwtToken));
        }catch (Exception e){
            return ResponseEntity.status(401).body(Map.of("error", e.getMessage()));
        }
    }
}
