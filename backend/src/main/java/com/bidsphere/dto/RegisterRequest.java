package com.bidsphere.dto;


import lombok.Data;

@Data
public class RegisterRequest {
    private String firstname;
    private String lastname;
    private String username;
    private String email;
    private String password;
    private String address;
}
