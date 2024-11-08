package com.example.offer_service.service;

import com.example.offer_service.client.AgencyClient;
import com.example.offer_service.dto.Agence;
import com.example.offer_service.entity.Offre;
import com.example.offer_service.repository.OfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ssl.SslProperties.Bundles.Watch.File;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
public class OfferService {

    private final AgencyClient agencyClient;
    private final OfferRepository offerRepository;

    @Autowired
    public OfferService(AgencyClient agencyClient, OfferRepository offerRepository) {
        this.agencyClient = agencyClient;
        this.offerRepository = offerRepository;
    }

    public Offre createOfferIfAgencyExists(Offre offer,MultipartFile file) {
        Agence agency = agencyClient.getAgenceById(offer.getAgencyId());
        
        if (agency != null) {
            if (file != null && !file.isEmpty()) {
                // Generate a unique filename for the file
                String fileName = file.getOriginalFilename();
                String uniqueFileName = System.currentTimeMillis() + "_" + fileName; // Avoid filename duplication
                Path imagePath = Paths.get("offer-service/src/main/uploads", uniqueFileName); // Adjust the path as needed
        
                try {
                    // Ensure the upload directory exists
                    Files.createDirectories(imagePath.getParent());
        
                    // Save the file to the specified path
                    Files.copy(file.getInputStream(),imagePath, StandardCopyOption.REPLACE_EXISTING);
        
                    // Set the document path in the Agence object
                    offer.setImage(uniqueFileName.toString()); // Only store the document path
        
                    // Log for debugging (consider using a logger instead)
                    System.out.println("Image Path: " + imagePath.toString());
                } catch (IOException e) {
                    // Handle file upload exception
                    throw new RuntimeException("Failed to store file: " + e.getMessage());
                }
            } else {
                System.out.println("No file provided or the file is empty.");
            }
            return offerRepository.save(offer);
        }
        // Return null if the agency does not exist
        return null;
    }


    public Offre getOfferById(Long id) {
        return offerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Offer not found with id: " + id));
    }

    public List<Offre> getAllOffers() {
        return offerRepository.findAll();
    }

    public List<Offre> searchOffers(String title, String theme, String type, String level, Double price) {
        return offerRepository.searchOffers(title, theme, type, level, price);
    }

    

}
