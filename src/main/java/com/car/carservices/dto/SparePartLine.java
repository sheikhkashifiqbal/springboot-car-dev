// src/main/java/com/car/carservices/dto/SparePartLine.java
package com.car.carservices.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record SparePartLine(
    @JsonProperty("spare_part") String sparePart,
    @JsonProperty("class_type") String classType,
    @JsonProperty("qty") int qty,
    @JsonProperty("price") double price
) {}
