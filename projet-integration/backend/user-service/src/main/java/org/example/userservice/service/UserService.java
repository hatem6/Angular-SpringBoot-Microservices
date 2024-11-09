package org.example.userservice.service;

import com.example.agency.service.entity.Agence;
import com.example.agency.service.repository.AgenceRepository;
import org.example.userservice.entity.User;
import org.example.userservice.exception.ResourceNotFoundException;
import org.example.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final AgenceRepository agenceRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, AgenceRepository agenceRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.agenceRepository = agenceRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Create a new user
    public User createUser(User user) {
        // Check if the email already exists in the database
        Optional<User> existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser.isPresent()) {
            throw new IllegalArgumentException("Email already exists.");
        }

        // Hash the password before saving
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);

        // Save the user to the database
        return userRepository.save(user);
    }

    // Retrieve User by ID
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }

    // Retrieve all Users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Update an existing User
    public User updateUser(Long id, User userDetails) {
        User user = getUserById(id);
        user.setName(userDetails.getName());
        user.setEmail(userDetails.getEmail());

        // Update password only if provided
        if (userDetails.getPassword() != null && !userDetails.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userDetails.getPassword()));
        }

        // Save the updated user to the database
        return userRepository.save(user);
    }

    // Login method
    public boolean login(String email, String password) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.isPresent() && passwordEncoder.matches(password, user.get().getPassword());
    }



    // Method to search agencies by name or location
    public List<Agence> searchAgencies(String keyword) {
        return agenceRepository.findByNameContainingOrLocationContaining(keyword, keyword);
    }
}

