package com.car.carservices.dto;
import java.time.LocalDate;

public record BranchSearchServiceRequestDTO(
    String carBrand,
    String carModel,
    String serviceEntity,
    LocalDate date,
    String location
) {}
