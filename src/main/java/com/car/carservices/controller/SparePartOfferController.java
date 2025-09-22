// src/main/java/com/car/carservices/controller/SparePartOfferController.java
package com.car.carservices.controller;

import com.car.carservices.dto.SparePartOfferResponse;
import com.car.carservices.dto.UserIdRequest;
import com.car.carservices.service.SparePartOfferService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/spare-parts/offers")
public class SparePartOfferController {

    private final SparePartOfferService service;

    public SparePartOfferController(SparePartOfferService service) {
        this.service = service;
    }
        @PostMapping("/by-user-branch")
    public ResponseEntity<List<SparePartOfferResponse>> byUserAndBranch(@RequestBody UserIdRequest req) {
        if (req == null || req.getUserId() == null || req.getBranchId() == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(service.byUserAndBranch(req.getUserId(), req.getBranchId()));
    }
}

