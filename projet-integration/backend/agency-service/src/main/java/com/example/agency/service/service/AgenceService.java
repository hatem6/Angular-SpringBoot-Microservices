package com.example.agency.service.service;

import com.example.agency.service.entity.Agence;
import com.example.agency.service.exception.ResourceNotFoundException;
import com.example.agency.service.repository.AgenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

@Service
public class AgenceService {

    private final AgenceRepository agenceRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    @Autowired
    public AgenceService(AgenceRepository agenceRepository, BCryptPasswordEncoder passwordEncoder) {
        this.agenceRepository = agenceRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Agence createAgence(Agence agence, MultipartFile file) {
        // Check if the email already exists in the database
        Optional<Agence> existingAgence = agenceRepository.findByEmail(agence.getEmail());
        if (existingAgence.isPresent()) {
            throw new IllegalArgumentException("Email already exists.");
        }
        String hashedPassword = passwordEncoder.encode(agence.getPassword());
        agence.setPassword(hashedPassword);
        // Proceed with file upload if email is unique
        if (file != null && !file.isEmpty()) {
            // Generate a unique filename for the file
            String fileName = file.getOriginalFilename();
            String uniqueFileName = System.currentTimeMillis() + "_" + fileName; // Avoid filename duplication
            Path documentPath = Paths.get("agency-service/src/main/uploads", uniqueFileName); // Adjust the path as needed
    
            try {
                // Ensure the upload directory exists
                Files.createDirectories(documentPath.getParent());
    
                // Save the file to the specified path
                Files.copy(file.getInputStream(), documentPath, StandardCopyOption.REPLACE_EXISTING);
    
                // Set the document path in the Agence object
                agence.setDocumentPath(uniqueFileName.toString()); // Only store the document path
    
                // Log for debugging (consider using a logger instead)
                System.out.println("Document Path: " + documentPath.toString());
            } catch (IOException e) {
                // Handle file upload exception
                throw new RuntimeException("Failed to store file: " + e.getMessage());
            }
        } else {
            System.out.println("No file provided or the file is empty.");
        }
    
        // Save the agence to the database or perform other business logic
        return agenceRepository.save(agence);
    }
    


    // Retrieve Agence by ID
    public Agence getAgenceById(Long id) {
        return agenceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Agence not found with id: " + id));
    }

    // Retrieve all Agences
    public List<Agence> getAllAgences() {
        return agenceRepository.findAll();
    }

    // Update an existing Agence
    public Agence updateAgence(Long id, Agence agenceDetails) {
        Agence agence = getAgenceById(id);
        agence.setName(agenceDetails.getName());
        agence.setEmail(agenceDetails.getEmail());
        agence.setPassword(agenceDetails.getPassword());
        agence.setVerificationStatus(agenceDetails.getVerificationStatus());

        return agenceRepository.save(agence);
    }

    // Delete an Agence
    public void deleteAgence(Long id) {
        Agence agence = getAgenceById(id);
        agenceRepository.delete(agence);
    }

    public boolean login(String email, String password) {
        Optional<Agence> agence = agenceRepository.findByEmail(email);
        if (agence.isPresent()) {
            // Use BCryptPasswordEncoder's matches method to check password
            return passwordEncoder.matches(password, agence.get().getPassword());
        } else {
            return false;
        }
    }
    @GetMapping("/unverified")
    public List<Agence> getUnverifiedAgencies() {
        return agenceRepository.findByVerificationStatusFalse();
// Appel sur l'instance injectée
    }
}


