package com.bidsphere.controller;


import com.bidsphere.dto.ChangePasswordRequest;
import com.bidsphere.dto.UserDto;
import com.bidsphere.model.User;
import com.bidsphere.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/me")
    public ResponseEntity<?> getUserDetails(Authentication authentication){
        String username = authentication.getName();
        Optional<User> userOptional = userRepository.findByUsername(username);

        if(userOptional.isPresent()){
            User user = userOptional.get();
            UserDto userDto = new UserDto(user.getFirstname(), user.getLastname(), user.getUsername(), user.getEmail(), user.getAddress());
            return ResponseEntity.ok(userDto);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateUser(Authentication authentication,@Valid @RequestBody UserDto updatedUserDto) {
        String username = authentication.getName();
        Optional<User> userOptional = userRepository.findByUsername(username);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if(updatedUserDto.getFirstname() != null && !updatedUserDto.getFirstname().isBlank()){
                user.setFirstname(updatedUserDto.getFirstname());
            }
            if(updatedUserDto.getLastname() != null && !updatedUserDto.getLastname().isBlank()){
                user.setLastname(updatedUserDto.getLastname());
            }
            if(updatedUserDto.getEmail() != null && !updatedUserDto.getEmail().isBlank()) {
                user.setEmail(updatedUserDto.getEmail());
            }
            if(updatedUserDto.getAddress() != null && !updatedUserDto.getAddress().isBlank()){
                user.setAddress(updatedUserDto.getAddress());
            }
            userRepository.save(user);
            return ResponseEntity.ok("User updated successfully.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUser(Authentication authentication) {
        String username = authentication.getName();
        Optional<User> userOptional = userRepository.findByUsername(username);

        if (userOptional.isPresent()) {
            userRepository.delete(userOptional.get());
            return ResponseEntity.ok("User deleted successfully.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/username")
    public ResponseEntity<?> getUsernameById(@PathVariable Long id) {
        return userRepository.findById(id)
                .map(user -> ResponseEntity.ok(user.getUsername()))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/change-password")
    public ResponseEntity<?> changePassword(Authentication authentication, @RequestBody ChangePasswordRequest request){
        String username = authentication.getName();
        Optional<User> userOptional = userRepository.findByUsername(username);

        if(userOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }

        User user = userOptional.get();

        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Old password is incorrect.");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);

        return ResponseEntity.ok("Password changed successfully");
    }
}
