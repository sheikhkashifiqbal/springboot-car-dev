package com.car.carservices.dto;

import lombok.Data;

@Data
public class CompanyDTO {
    private Long companyId;
    private String companyName;
    private String brandName;
    private String taxId;
    private String managerName;
    private String managerSurname;
    private String managerPhone;
    private String managerMobile;
    private String managerEmail;
    private String website;
    private String password;
    private String tinPhoto;
}