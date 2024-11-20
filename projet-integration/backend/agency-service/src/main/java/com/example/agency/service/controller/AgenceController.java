package com.example.agency.service.controller;

import com.example.agency.service.entity.Agence;
import com.example.agency.service.service.AgenceService;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.agency.service.dto.Login;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/agences")
public class AgenceController {

    private final AgenceService agenceService;

    @Autowired
    public AgenceController(AgenceService agenceService) {
        this.agenceService = agenceService;
    }

    // Create a new Agence
    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> createAgence(
        @RequestParam("name") String name,
        @RequestParam("email") String email,
        @RequestParam("password") String password,
        @RequestParam(value = "file", required = false) MultipartFile file) {
    
        System.out.println("Received file: " + (file != null ? "not null" : "null"));
        System.out.println("Is file empty: " + (file != null && file.isEmpty()));
    
        // Create an Agence object with basic details
        Agence agence = Agence.builder()
                .name(name)
                .email(email)
                .password(password)
                .verificationStatus(false) // Default to false for a new Agence
                .build();
        // Create the agence
        try {
            agenceService.createAgence(agence,file);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Agence created successfully");
            response.put("created", true);
            // Return a 200 OK response with the confirmation message
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (IllegalArgumentException e) {
            // Return custom JSON response with error details
            Map<String, Object> response = new HashMap<>();
            response.put("message", e.getMessage());
            response.put("status", 400);
            response.put("created", false);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
    

    // Retrieve Agence by ID
    @GetMapping("/{id}")
    public ResponseEntity<Agence> getAgenceById(@PathVariable Long id) {
        Agence agence = agenceService.getAgenceById(id);
        return ResponseEntity.ok(agence);
    }

    // Retrieve all Agences
    @GetMapping("/all")
    public ResponseEntity<List<Agence>> getAllAgences() {
        List<Agence> agences = agenceService.getAllAgences();
        return ResponseEntity.ok(agences);
    }

    // Update an existing Agence
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateAgence(@PathVariable Long id, @RequestBody Agence agenceDetails) {
        Agence updatedAgence = agenceService.updateAgence(id, agenceDetails);
        // Prepare the response map
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Agence updated successfully");
        response.put("updated", true);
        response.put("agence", updatedAgence); // Optionally include the updated Agence in the response
    
        // Return a 200 OK response with the custom message and updated Agence
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    

    // Delete an Agence
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteAgence(@PathVariable Long id) {
        agenceService.deleteAgence(id); // Delete the agency using the service
    
        // Prepare a success response
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Agence deleted successfully");
        response.put("deleted", true);
    
        // Return a 200 OK response with the confirmation message
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Login loginRequest) {
        boolean isAuthenticated = agenceService.login(loginRequest.getEmail(), loginRequest.getPassword());

        Map<String, Object> response = new HashMap<>();
        if (isAuthenticated) {
            Optional<Agence> agence = agenceService.getAgenceDetailsByEmail(loginRequest.getEmail());
            response.put("message", "Login successful");
            response.put("authenticated", true);
            response.put("agence", agence);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            response.put("message", "Invalid email or password");
            response.put("authenticated", false);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }

    
}
