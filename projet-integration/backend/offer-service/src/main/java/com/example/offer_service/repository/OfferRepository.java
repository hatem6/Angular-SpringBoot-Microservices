package com.example.offer_service.repository;

import com.example.offer_service.entity.Offre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import org.springframework.data.repository.query.Param;

@Repository
public interface OfferRepository extends JpaRepository<Offre, Long> {
    // Additional query methods can be added here if needed
    @Query("SELECT o FROM Offre o " +
           "WHERE (:title IS NULL OR o.title LIKE %:title%) " +
           "AND (:theme IS NULL OR o.theme LIKE %:theme%) " +
           "AND (:type IS NULL OR o.type LIKE %:type%) " +
           "AND (:level IS NULL OR o.level LIKE %:level%) " +
           "AND (:price IS NULL OR o.price = :price)")
    List<Offre> searchOffers(
            @Param("title") String title,
            @Param("theme") String theme,
            @Param("type") String type,
            @Param("level") String level,
            @Param("price") Double price
    );
    List<Offre> findByAgencyId(Long agencyId);
}