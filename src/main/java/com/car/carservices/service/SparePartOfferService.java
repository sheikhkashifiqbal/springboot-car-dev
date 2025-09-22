// src/main/java/com/car/carservices/service/SparePartOfferService.java
package com.car.carservices.service;

import com.car.carservices.dto.SparePartOfferResponse;

import java.util.List;

public interface SparePartOfferService {
    List<SparePartOfferResponse> byUserId(Long userId);
}
