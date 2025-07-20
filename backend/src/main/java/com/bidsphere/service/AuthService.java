package com.bidsphere.service;

import com.bidsphere.dto.RegisterRequest;
import com.bidsphere.model.User;
import com.bidsphere.repository.UserRepository;
import com.bidsphere.security.CustomUserDetails;
import com.bidsphere.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.AuthenticationException;


@Service
public class AuthService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public String registerUser(RegisterRequest request){
        if(userRepository.existsByUsername(request.getUsername())){
            return "Error: Username is already taken";
        }
        if(userRepository.existsByEmail(request.getEmail())){
            return "Error: Email is already in use!";
        }

        User user = new User();
        user.setFirstname(request.getFirstname());
        user.setLastname(request.getLastname());
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setAddress(request.getAddress());
        userRepository.save(user);
        return "User registered successfully!";
    }

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    JwtUtil jwtUtil;

    public String login(String username, String password) throws Exception {
        try{
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );

            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            return jwtUtil.generateToken(userDetails);
        }catch (AuthenticationException e){
            throw new Exception("Invalid Username or password");
        }
    }
}
