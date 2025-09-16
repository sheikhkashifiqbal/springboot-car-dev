package com.car.carservices.service.impl;

import com.car.carservices.dto.BranchAvailableSlotDTO;
import com.car.carservices.dto.BranchSearchServiceRequestDTO;
import com.car.carservices.entity.Branch;
import com.car.carservices.repository.BranchServiceRepository;
import com.car.carservices.service.BranchSearchStoreService;
import com.car.carservices.util.TimeSlotUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BranchSearchStoreServiceImpl implements BranchSearchStoreService {

    private final BranchServiceRepository branchRepository;

    public BranchSearchStoreServiceImpl(BranchServiceRepository branchRepository) {
        this.branchRepository = branchRepository;
    }

    @Override
    public List<BranchAvailableSlotDTO> searchBranches(BranchSearchServiceRequestDTO request) {
        List<Branch> branches = branchRepository.searchBranches(
            request.carBrand(),
            request.carModel(),
            request.serviceEntity(),
            request.location()
        );

java.util.List<String> slots = java.util.List.of(
    "09:00","09:30","10:00","10:30","11:00","11:30","12:00","12:30",
    "13:00","13:30","14:00","14:30","15:00","15:30","16:00","16:30",
    "17:00","17:30","18:00"
);

        return branches.stream().map(branch -> new BranchAvailableSlotDTO(
            branch.getBranchId(),
            branch.getBranchName(),
            branch.getLocation(),
            slots
        )).toList();
    }
}
