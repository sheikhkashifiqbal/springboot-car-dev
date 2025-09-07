package com.car.carservices.dto;

import lombok.Data;

@Data
public class SparePartsRequestDTO {
    private Long sparepartsrequestId;
    private Long userId;
    private String date;
    private String vinNumber;
    private Long categoryId;
    private String state;
    private String requestStatus;
    private String city;
}
