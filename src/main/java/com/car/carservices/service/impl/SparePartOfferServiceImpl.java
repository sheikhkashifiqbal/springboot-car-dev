// src/main/java/com/car/carservices/service/impl/SparePartOfferServiceImpl.java
package com.car.carservices.service.impl;

import com.car.carservices.dto.SparePartOfferResponse;
import com.car.carservices.repository.SparePartOfferRepository;
import com.car.carservices.repository.views.SparePartOfferView;
import com.car.carservices.service.SparePartOfferService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SparePartOfferServiceImpl implements SparePartOfferService {

    private final SparePartOfferRepository repo;

    public SparePartOfferServiceImpl(SparePartOfferRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<SparePartOfferResponse> byUserId(Long userId) {
        if (userId == null) return List.of();
        return map(repo.findOffersByUserId(userId));
    }
    @Override
    public List<SparePartOfferResponse> byUserAndBranch(Long userId, Long branchId) {
        if (userId == null || branchId == null) return List.of();
        return map(repo.findOffersByUserAndBranch(userId, branchId));
    }

    
private static List<SparePartOfferResponse> map(List<SparePartOfferView> rows) {
        return rows.stream().map(r ->
            new SparePartOfferResponse(
                r.getSparePartsRequestId(),
                r.getDate(),
                r.getBranchName(),
                r.getAddress(),
                r.getCity(),
                r.getVin(),
                r.getSparepartsType(),
                r.getState(),
                r.getSparePart(),
                
                r.getClassType(),
                r.getQty(),
                r.getPrice(),

                r.getManagerMobile(),
                r.getId()
            )
        ).toList();
    }
}
