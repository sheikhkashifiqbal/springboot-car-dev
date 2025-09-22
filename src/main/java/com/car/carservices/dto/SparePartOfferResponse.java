// src/main/java/com/car/carservices/dto/SparePartOfferResponse.java
package com.car.carservices.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record SparePartOfferResponse(
    
        @JsonProperty("sparepartsrequest_id") Long sparePartsRequestId,
        @JsonProperty("date")           String date,
        @JsonProperty("branch_name")    String branchName,
        @JsonProperty("address")        String address,
        @JsonProperty("city")           String city,
        @JsonProperty("viN")            String vin,            // exact casing as requested
        @JsonProperty("spareparts_type")String sparepartsType,
        @JsonProperty("state")          String state,
        @JsonProperty("spare_part")     String sparePart,      // mapped from spare_parts.condition
        @JsonProperty("class_Type")     String classType, 
        @JsonProperty("qty")            int qty, 
        @JsonProperty("price")          double price, 

        @JsonProperty("manager_mobile") String managerMobile,
        @JsonProperty("id")             Long id                // from branch_brand_spare_part.id
) {}
