package com.car.carservices.service;

import com.car.carservices.dto.BranchSearchDTO;
import com.car.carservices.dto.BranchSearchResultDTO;

import java.util.List;

public interface BranchBrandSearchService {
    List<BranchSearchResultDTO> searchBranches(BranchSearchDTO searchDTO);
}
