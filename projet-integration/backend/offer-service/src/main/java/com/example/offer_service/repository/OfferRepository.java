package com.example.offer_service.repository;

import com.example.offer_service.entity.Offre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OfferRepository extends JpaRepository<Offre, Long> {
    // Additional query methods can be added here if needed
}