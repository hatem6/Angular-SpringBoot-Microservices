package com.example.serviceadmin.service;

import org.springframework.stereotype.Service;

@Service
public class adminservice {
    public adminrepository adminrepository;

    public boolean login(String email, String password) {
        Optional<admin> admin = adminrepository.findByEmail(email);
        return admin.isPresent() && passwordEncoder.matches(password, admin.get().getPassword());
    }
}
