package com.car.carservices.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Branch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long branchId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;

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
    private String status = "disapproved";
}