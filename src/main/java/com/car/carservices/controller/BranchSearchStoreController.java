package com.car.carservices.controller;

import com.car.carservices.dto.BranchAvailableSlotDTO;
import com.car.carservices.dto.BranchSearchServiceRequestDTO;
import com.car.carservices.service.BranchSearchStoreService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/branches/services")
public class BranchSearchStoreController {

    private final BranchSearchStoreService branchSearchService;

    public BranchSearchStoreController(BranchSearchStoreService branchSearchService) {
        this.branchSearchService = branchSearchService;
    }

    @PostMapping("/search")
    public ResponseEntity<List<BranchAvailableSlotDTO>> searchBranches(@RequestBody BranchSearchServiceRequestDTO request) {
        List<BranchAvailableSlotDTO> results = branchSearchService.searchBranches(request);
        return ResponseEntity.ok(results);
    }
}
