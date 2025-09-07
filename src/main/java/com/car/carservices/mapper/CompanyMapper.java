package com.car.carservices.mapper;

import com.car.carservices.dto.CompanyDTO;
import com.car.carservices.entity.Company;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class CompanyMapper {
    public CompanyDTO toDTO(Company entity) {
        CompanyDTO dto = new CompanyDTO();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }

    public Company toEntity(CompanyDTO dto) {
        Company entity = new Company();
        BeanUtils.copyProperties(dto, entity);
        return entity;
    }
}