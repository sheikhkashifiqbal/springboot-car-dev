package com.car.carservices.dto;

import lombok.Data;

@Data
public class RateExperienceDTO {
    private Long rateExperienceID;
    private Long brachBrandServiceID;
    private int stars;
    private String description;
}