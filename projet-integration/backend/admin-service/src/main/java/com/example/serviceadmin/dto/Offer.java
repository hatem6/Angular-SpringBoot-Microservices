package com.example.serviceadmin.dto;

import lombok.Data;

@Data
public class Offer {
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