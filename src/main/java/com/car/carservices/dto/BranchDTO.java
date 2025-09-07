package com.car.carservices.dto;

import lombok.Data;

@Data
public class BranchDTO {
    private Long branchId;
    private Long companyId;
    private String branchName;
    private String branchCode;
    private String branchManagerName;
    private String branchManagerSurname;
    private String branchAddress;
    private String location;
    private String workdays;
    private String workingHours;
    private String loginEmail;
    private String password;
    private String logoImg;
    private String branchCoverImg;
    private String status;
}