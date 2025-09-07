package com.car.carservices.service.impl;

import com.car.carservices.dto.*;
import com.car.carservices.entity.*;
import com.car.carservices.repository.*;
import com.car.carservices.service.BranchBrandSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class BranchBrandSearchServiceImpl implements BranchBrandSearchService {

    @Autowired
    private BranchBrandRepository branchRepository;

    @Autowired
    private BranchBrandServiceRepository bbsRepository;

    @Autowired
    private BrandRepository brandRepository;

    @Override
    public List<BranchSearchResultDTO> searchBranches(BranchSearchDTO searchDTO) {
        List<Branch> branches = branchRepository.findBranchesByBrandAndServices(searchDTO.getBrandId(), searchDTO.getServiceIds());

        Map<Long, List<Branch>> groupedByBrand = branches.stream()
                .collect(Collectors.groupingBy(branch -> searchDTO.getBrandId()));

        List<BranchSearchResultDTO> resultList = new ArrayList<>();

        for (Map.Entry<Long, List<Branch>> entry : groupedByBrand.entrySet()) {
            Long brandId = entry.getKey();
            String brandName = brandRepository.findById(brandId).map(Brand::getBrandName).orElse("Unknown Brand");

            List<BranchWithServicesDTO> branchDTOs = entry.getValue().stream().map(branch -> {
                BranchWithServicesDTO dto = new BranchWithServicesDTO();
                dto.setBranchId(branch.getBranchId());
                dto.setBranchName(branch.getBranchName());
                dto.setLocation(branch.getLocation());
                dto.setWorkdays(branch.getWorkdays());
                dto.setWorkingHours(branch.getWorkingHours());

                List<String> services = bbsRepository.findByBranch_BranchId(branch.getBranchId()).stream()
                        .filter(bbs -> searchDTO.getServiceIds().contains(bbs.getService().getServiceId()))
                        .map(bbs -> bbs.getService().getServiceName())
                        .distinct()
                        .collect(Collectors.toList());

                dto.setServices(services);
                return dto;
            }).collect(Collectors.toList());

            BranchSearchResultDTO brandResult = new BranchSearchResultDTO();
            brandResult.setBrandId(brandId);
            brandResult.setBrandName(brandName);
            brandResult.setBranches(branchDTOs);

            resultList.add(brandResult);
        }

        return resultList;
    }
}
