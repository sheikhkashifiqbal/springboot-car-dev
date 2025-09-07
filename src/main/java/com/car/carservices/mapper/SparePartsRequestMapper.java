package com.car.carservices.mapper;

import com.car.carservices.dto.SparePartsRequestDTO;
import com.car.carservices.entity.SparePartsRequest;
import org.springframework.stereotype.Component;

@Component
public class SparePartsRequestMapper {
    public SparePartsRequestDTO toDTO(SparePartsRequest entity) {
        SparePartsRequestDTO dto = new SparePartsRequestDTO();
        dto.setSparepartsrequestId(entity.getSparepartsrequestId());
        dto.setUserId(entity.getUserId());
        dto.setDate(entity.getDate());
        dto.setVinNumber(entity.getVinNumber());
        dto.setCategoryId(entity.getCategoryId());
        dto.setState(entity.getState());
        dto.setRequestStatus(entity.getRequestStatus());
        dto.setCity(entity.getCity());
        return dto;
    }

    public SparePartsRequest toEntity(SparePartsRequestDTO dto) {
        SparePartsRequest entity = new SparePartsRequest();
        entity.setSparepartsrequestId(dto.getSparepartsrequestId());
        entity.setUserId(dto.getUserId());
        entity.setDate(dto.getDate());
        entity.setVinNumber(dto.getVinNumber());
        entity.setCategoryId(dto.getCategoryId());
        entity.setState(dto.getState());
        entity.setRequestStatus(dto.getRequestStatus());
        entity.setCity(dto.getCity());
        return entity;
    }
}
