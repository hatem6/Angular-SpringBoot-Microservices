package com.example.agency.service.entity;
import jakarta.persistence.*;
import lombok.*;
@Entity
@Table(name = "agences")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Agence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String password;
    private Boolean verificationStatus;
    private String documentPath;
}
