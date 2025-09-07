package com.car.carservices.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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