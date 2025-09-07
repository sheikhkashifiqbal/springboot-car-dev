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

        return branches.stream().map(branch -> new BranchAvailableSlotDTO(
            branch.getBranchId(),
            branch.getBranchName(),
            branch.getLocation(),
            TimeSlotUtil.generateTimeSlots(branch.getWorkingHours())
        )).toList();
    }
}
