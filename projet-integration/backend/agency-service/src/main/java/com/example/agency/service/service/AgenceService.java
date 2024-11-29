package com.example.agency.service.service;

import com.example.agency.service.entity.Agence;
import com.example.agency.service.exception.ResourceNotFoundException;
import com.example.agency.service.repository.AgenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
            Path documentPath = Paths.get("backend/agency-service/src/main/uploads", uniqueFileName); // Adjust the path as needed
    
            try {
                // Ensure the upload directory exists
                Files.createDirectories(documentPath.getParent());
    
                // Save the file to the specified path
                Files.copy(file.getInputStream(), documentPath, StandardCopyOption.REPLACE_EXISTING);
    
                // Set the document path in the Agence object
                agence.setDocumentPath("http://localhost:8081/uploads/"+uniqueFileName.toString()); // Only store the document path
    
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

    public Optional<Agence> getAgenceDetailsByEmail(String email) {
        // Assuming you have a repository or a service to fetch the agence
        return agenceRepository.findByEmail(email);
    }

    // Update an existing Agence
    public Agence updateAgence(Long id, Agence agenceDetails, MultipartFile file) {
        // Retrieve the existing Agence from the database
        Agence agence = getAgenceById(id);
    
        // Update basic details
        agence.setName(agenceDetails.getName());
        // Check if the new email is different and already exists in the database
        if (!agence.getEmail().equals(agenceDetails.getEmail())) {
            Optional<Agence> existingAgence = agenceRepository.findByEmail(agenceDetails.getEmail());
            if (existingAgence.isPresent()) {
                throw new IllegalArgumentException("The email address is already in use.");
            }
            agence.setEmail(agenceDetails.getEmail());
        }
        if (agenceDetails.getPassword() != null && !agenceDetails.getPassword().isEmpty()) {
            String hashedPassword = passwordEncoder.encode(agenceDetails.getPassword());
            agence.setPassword(hashedPassword);
        }
        agence.setVerificationStatus(agenceDetails.getVerificationStatus());
    
        // Check if a new file is provided for upload
        if (file != null && !file.isEmpty()) {
            // Generate a unique filename for the new file
            String fileName = file.getOriginalFilename();
            String uniqueFileName = System.currentTimeMillis() + "_" + fileName;
            Path documentPath = Paths.get("backend/agency-service/src/main/uploads", uniqueFileName);
    
            try {
                // Ensure the upload directory exists
                Files.createDirectories(documentPath.getParent());
    
                // Save the new file to the specified path
                Files.copy(file.getInputStream(), documentPath, StandardCopyOption.REPLACE_EXISTING);
    
                // Update the documentPath in the Agence object
                agence.setDocumentPath("http://localhost:8081/uploads/" + uniqueFileName);
    
                // Log for debugging (consider using a logger instead)
                System.out.println("Updated Document Path: " + documentPath.toString());
            } catch (IOException e) {
                // Handle file upload exception
                throw new RuntimeException("Failed to store updated file: " + e.getMessage());
            }
        } else {
            System.out.println("No new file provided or the file is empty. Keeping the existing document path.");
        }
    
        // Save the updated Agence details to the database
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

}
