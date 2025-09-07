package com.car.carservices.dto;

import lombok.Data;
import java.util.List;

@Data
public class BranchSearchResultDTO {
    private Long brandId;
    private String brandName;
    private List<BranchWithServicesDTO> branches;
}
