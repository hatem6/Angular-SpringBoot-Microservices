package com.example.offer_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.HttpStatus;
import com.example.offer_service.entity.Offre;
import com.example.offer_service.service.OfferService;

import java.sql.Date;
import java.util.Map;
import java.util.HashMap;
import java.util.List;

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
        @RequestParam("localisation") String localisation,
        @RequestParam("type") String type,
        @RequestParam("theme") String theme,
        @RequestParam("level") String level,
        @RequestParam("date") String date,
        @RequestParam("approvalStatus") Boolean approvalStatus,
        @RequestParam(value = "etat", defaultValue = "visible") String etat, 
        @RequestParam(value = "image", required = false) MultipartFile image) {

        System.out.println("Received image: " + (image != null ? "not null" : "null"));
        System.out.println("Is image empty: " + (image != null && image.isEmpty()));

        String parsedDate;
        try {
                parsedDate = Date.valueOf(date).toString();
            } catch (IllegalArgumentException e) {
                // Handle invalid date format
                Map<String, Object> response = new HashMap<>();
                response.put("message", "Invalid date format. Expected format: yyyy-MM-dd");
                response.put("created", false);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        if (!etat.equalsIgnoreCase("archive") && !etat.equalsIgnoreCase("visible")) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Invalid etat value. Allowed values are 'archive' or 'visible'.");
            response.put("created", false);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        
        // Create an Agence object with basic details
        Offre offer = Offre.builder()
                .agencyId(agencyId)
                .title(title)
                .description(description)
                .price(price)
                .localisation(localisation)
                .type(type)
                .theme(theme)
                .level(level)
                .date(parsedDate)
                .approvalStatus(approvalStatus)
                .etat(etat.toLowerCase())
                .build();
        // Create the agence
        try {
            offerService.createOfferIfAgencyExists(offer,image);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Offer created successfully");
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

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getOfferById(@PathVariable Long id) {
        try {
            Offre offer = offerService.getOfferById(id);
            Map<String, Object> response = new HashMap<>();
            response.put("offer", offer);
            response.put("found", true);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", e.getMessage());
            response.put("found", false);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping
    public ResponseEntity<List<Offre>> getAllOffers() {
        List<Offre> offers = offerService.getAllOffers();
        return ResponseEntity.ok(offers);   
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteOffer(@PathVariable Long id) {
    boolean isDeleted = offerService.deleteOfferById(id);
    Map<String, Object> response = new HashMap<>();
    if (isDeleted) {
        response.put("message", "Offer deleted successfully");
        response.put("deleted", true);
        return ResponseEntity.ok(response);
    } else {
        response.put("message", "Offer not found");
        response.put("deleted", false);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
    }


    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateOffer(
            @PathVariable Long id,
            @RequestParam("agencyId") Long agencyId,
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("price") Double price,
            @RequestParam("localisation") String localisation,
            @RequestParam("type") String type,
            @RequestParam("theme") String theme,
            @RequestParam("level") String level,
            @RequestParam("date") String date,
            @RequestParam("approvalStatus") Boolean approvalStatus,
            @RequestParam(value = "etat", defaultValue = "visible") String etat,
            @RequestParam(value = "image", required = false) MultipartFile image) {

        // Create updated offer
        Offre updatedOffer = Offre.builder()
                .agencyId(agencyId)
                .title(title)
                .description(description)
                .price(price)
                .localisation(localisation)
                .type(type)
                .theme(theme)
                .level(level)
                .date(date)
                .approvalStatus(approvalStatus)
                .etat(etat.toLowerCase())
                .build();

        try {
            Offre savedOffer = offerService.updateOffer(id, updatedOffer, image);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Offer updated successfully");
            response.put("offer", savedOffer);
            response.put("updated", true);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", e.getMessage());
            response.put("updated", false);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }


    @GetMapping("/search")
    public ResponseEntity<List<Offre>> searchOffers(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String theme,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String level,
            @RequestParam(required = false) Double price) {

        List<Offre> offers = offerService.searchOffers(title, theme, type, level, price);
        return ResponseEntity.ok(offers);
    }
    @GetMapping("/agency/{agencyId}")
    public ResponseEntity<List<Offre>> getOffersByAgencyId(@PathVariable Long agencyId) {
        // Récupérer les offres liées à une agence spécifique
        List<Offre> offers = offerService.getOffersByAgencyId(agencyId);

        if (offers.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(offers); // Retourne une liste vide si aucune offre n'est trouvée
        }

        return ResponseEntity.ok(offers);  // Retourne la liste des offres pour l'agence donnée
    }


}
