package com.example.agency.service.repository;

import com.example.agency.service.entity.Agence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface AgenceRepository extends JpaRepository<Agence, Long> {
    // Custom query methods can be defined here if needed
    // Example: Optional<Agence> findByEmail(String email);
    Optional<Agence> findByEmail(String email);

    default List<Agence> findByNameContainingOrLocationContaining(String keyword, String keyword1) {
        return null;
    }
}
