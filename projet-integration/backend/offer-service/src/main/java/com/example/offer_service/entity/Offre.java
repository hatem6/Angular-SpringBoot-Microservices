package com.example.offer_service.entity;
import jakarta.persistence.*;
import lombok.*;
@Entity
@Table(name = "offres")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Offre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long agencyId;
    private String title;
    private String description;
    private Double price;
    private String type;
    private String theme;
    private String level;
    private String date;
    private Boolean approvalStatus;
    private String etat;
    private String image;

   
}
