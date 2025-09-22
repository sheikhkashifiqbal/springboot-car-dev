// src/main/java/com/car/carservices/dto/UserIdRequest.java
package com.car.carservices.dto;

import com.fasterxml.jackson.annotation.JsonAlias;

public class UserIdRequest {
    @JsonAlias({"userId", "user_id"})
    private Long userId;

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
}
