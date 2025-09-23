// src/main/java/com/car/carservices/service/impl/SparePartOfferServiceImpl.java
package com.car.carservices.service.impl;

import com.car.carservices.dto.SparePartLine;
import com.car.carservices.dto.SparePartOfferResponse;
import com.car.carservices.repository.SparePartOfferRepository;
import com.car.carservices.repository.views.SparePartOfferView;
import com.car.carservices.service.SparePartOfferService;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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

    // ⬇️ REPLACED: group rows (1 per detail) into 1 response per (request, branch)
    private static List<SparePartOfferResponse> map(List<SparePartOfferView> rows) {
        if (rows == null || rows.isEmpty()) return List.of();

        // Key by sparePartsRequestId + branch_brand_spare_part.id
        Map<String, List<SparePartOfferView>> grouped = rows.stream()
            .collect(Collectors.groupingBy(
                r -> r.getSparePartsRequestId() + "|" + r.getId(),
                LinkedHashMap::new,
                Collectors.toList()
            ));

        List<SparePartOfferResponse> out = new ArrayList<>(grouped.size());

        for (List<SparePartOfferView> group : grouped.values()) {
            SparePartOfferView first = group.get(0);

            List<SparePartLine> details = group.stream()
                .map(r -> new SparePartLine(r.getSparePart(), r.getClassType(), r.getQty(), r.getPrice()))
                .collect(Collectors.toList());

            // Preserve legacy top-level fields from the first detail.
            String classType = first.getClassType();
            int qty = first.getQty();
            double price = first.getPrice();

            out.add(new SparePartOfferResponse(
                first.getSparePartsRequestId(),
                first.getDate(),
                first.getBranchName(),
                first.getAddress(),
                first.getCity(),
                first.getVin(),
                first.getSparepartsType(),
                first.getState(),
                details,
                classType,
                qty,
                price,
                first.getManagerMobile(),
                first.getId(),
                first.getRequestStatus()
            ));
        }
        return out;
    }
}
