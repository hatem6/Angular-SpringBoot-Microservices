package org.example.userservice.controller;

import com.example.agency.service.entity.Agence;
import org.example.userservice.dto.Login;
import org.example.userservice.entity.User;
import org.example.userservice.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Create a new User
    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> createUser(
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("password") String password) {

        // Create a User object with basic details
        User user = User.builder()
                .name(name)
                .email(email)
                .password(password)
                .build();

        // Create the user
        try {
            userService.createUser(user);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "User created successfully");
            response.put("created", true);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (IllegalArgumentException e) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", e.getMessage());
            response.put("status", 400);
            response.put("created", false);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    // Retrieve User by ID
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    // Retrieve all Users
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    // Update an existing User
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        User updatedUser = userService.updateUser(id, userDetails);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "User updated successfully");
        response.put("updated", true);
        response.put("user", updatedUser);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // User login
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Login loginRequest) {
        boolean isAuthenticated = userService.login(loginRequest.getEmail(), loginRequest.getPassword());

        Map<String, Object> response = new HashMap<>();
        if (isAuthenticated) {
            response.put("message", "Login successful");
            response.put("authenticated", true);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            response.put("message", "Invalid email or password");
            response.put("authenticated", false);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }

}
