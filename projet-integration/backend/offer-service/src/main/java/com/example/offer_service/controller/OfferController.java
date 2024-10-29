package com.example.offer_service.controller;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.HttpStatus;
import com.example.offer_service.dto.Agence;
import com.example.offer_service.entity.Offre;
import com.example.offer_service.service.OfferService;


@RestController
@RequestMapping("/api/offers")
public class OfferController {

    private final OfferService offerService;

    @Autowired
    public OfferController(OfferService offerService) {
        this.offerService = offerService;
    }
    
    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> createOffer(
        @RequestParam("agencyId") Long agencyId,
        @RequestParam("title") String title,
        @RequestParam("description") String description,
        @RequestParam("price") Double price,
        @RequestParam("approvalStatus") String approvalStatus,
        
        @RequestParam(value = "image", required = false) MultipartFile image) {
    
        System.out.println("Received image: " + (image != null ? "not null" : "null"));
        System.out.println("Is image empty: " + (image != null && image.isEmpty()));
    
        // Create an Agence object with basic details
        Offre offer = Offre.builder()
                .agencyId(agencyId)
                .title(title)
                .description(description)
                .price(price)
                .approvalStatus(approvalStatus)
                .build();
        // Create the agence
        try {
            offerService.createOfferIfAgencyExists(offer,image);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Offer created successfully");
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

   
}
