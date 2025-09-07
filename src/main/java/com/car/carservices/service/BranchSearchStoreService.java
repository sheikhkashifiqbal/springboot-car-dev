package com.car.carservices.service;

import com.car.carservices.dto.BranchAvailableSlotDTO;
import com.car.carservices.dto.BranchSearchServiceRequestDTO;

import java.util.List;

public interface BranchSearchStoreService {
    List<BranchAvailableSlotDTO> searchBranches(BranchSearchServiceRequestDTO request);
}
