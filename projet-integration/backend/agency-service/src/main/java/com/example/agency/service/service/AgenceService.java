package com.example.agency.service.service;

import com.example.agency.service.entity.Agence;
import com.example.agency.service.exception.ResourceNotFoundException;
import com.example.agency.service.repository.AgenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AgenceService {

    private final AgenceRepository agenceRepository;

    @Autowired
    public AgenceService(AgenceRepository agenceRepository) {
        this.agenceRepository = agenceRepository;
    }

    // Create a new Agence
    public Agence createAgence(Agence agence) {
        // Check if the email already exists
        Optional<Agence> existingAgence = agenceRepository.findByEmail(agence.getEmail());
        if (existingAgence.isPresent()) {
            throw new IllegalArgumentException("Email already exists.");
        }
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
        if (agence.isPresent() && agence.get().getPassword().equals(password)) {
            return true;
        } else {
            return false;
        }
    }

}
