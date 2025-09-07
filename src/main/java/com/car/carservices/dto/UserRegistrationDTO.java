package com.car.carservices.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserRegistrationDTO {
    private Long id;
    private String fullName;
    private LocalDate birthday;
    private String gender;
    private String email;
    private String password;
}
