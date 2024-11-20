package com.example.serviceadmin.dto;

import lombok.Data;

@Data
public class Agence {
    private Long id;
    private String name;
    private String email;
    private String password;
    private Boolean verificationStatus;
    private String documentPath;
}