package com.example.serviceadmin.controller;


import com.example.serviceadmin.dto.Agence;
import com.example.serviceadmin.dto.Offer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class admincontroller {
    @Autowired
    private RestTemplate resttemplate;
    @Autowired
    private adminservice adminservice;



    @GetMapping("/agencies")
    public ResponseEntity<Map<Integer, String>> getAllAgencies() {
        ResponseEntity<Map> response = resttemplate.getForEntity("http://localhost:8081/api/agences/all", Map.class);
        return ResponseEntity.ok(response.getBody());
    }

    @PatchMapping("/agencies/{id}/verification")
    public ResponseEntity<String> updateVerificationStatus(@PathVariable Long id, @RequestParam Boolean status) {
        // Step 1: Fetch the agency
        String fetchUrl = "http://localhost:8081/api/agences/" + id;
        ResponseEntity<Agence> response = resttemplate.getForEntity(fetchUrl, Agence.class);

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            Agence agence = response.getBody();

            // Step 2: Update the verification status
            agence.setVerificationStatus(status);

            // Step 3: Send the updated agency back to ServiceAgency
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Agence> requestEntity = new HttpEntity<>(agence, headers);
            resttemplate.exchange(fetchUrl, HttpMethod.PUT, requestEntity, String.class);

            return ResponseEntity.ok("Verification status updated successfully!");
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Agency not found!");
    }


    private final String OFFER_SERVICE_URL = "http://localhost:8082/api/offers";
    @GetMapping("/offers")
    public ResponseEntity<List> getAllOffers() {
        ResponseEntity<List> response = resttemplate.getForEntity(OFFER_SERVICE_URL, List.class);
        return ResponseEntity.ok(response.getBody());
    }

    @PatchMapping("/offers/{id}/approval")
    public ResponseEntity<String> updateApprovalStatus(@PathVariable Long id, @RequestParam Boolean status) {
        // Step 1: Fetch the offer
        String fetchUrl = OFFER_SERVICE_URL + "/" + id;
        ResponseEntity<Offer> response = resttemplate.getForEntity(fetchUrl, Offer.class);

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            Offer offer = response.getBody();

            // Step 2: Update the approval status
            offer.setApprovalStatus(status);

            // Step 3: Send the updated offer back to Offer Service
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Offer> requestEntity = new HttpEntity<>(offer, headers);
            resttemplate.exchange(fetchUrl, HttpMethod.PUT, requestEntity, String.class);

            return ResponseEntity.ok("Approval status updated successfully!");
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Offer not found!");
    }
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Login loginRequest) {
        boolean isAuthenticated = adminservice.login(loginRequest.getEmail(), loginRequest.getPassword());

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
