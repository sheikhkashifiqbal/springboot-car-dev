package com.car.carservices.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class SparePartsRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sparepartsrequestId;

    private Long userId;
    private String date;
    private String vinNumber;
    private Long categoryId;
    private String state;
    private String requestStatus;
    private String city;
}
