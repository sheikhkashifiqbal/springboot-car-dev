package com.car.carservices.mapper;

import com.car.carservices.dto.BranchDTO;
import com.car.carservices.entity.Branch;
import com.car.carservices.repository.CompanyRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BranchMapper {

    @Autowired
    private CompanyRepository companyRepo;

    public BranchDTO toDTO(Branch entity) {
        BranchDTO dto = new BranchDTO();
        BeanUtils.copyProperties(entity, dto);
        if (entity.getCompany() != null) {
            dto.setCompanyId(entity.getCompany().getCompanyId());
        }
       // dto.setStatus(dto.getStatus() == null ? "disapproved" : dto.getStatus());
        return dto;
    }

    public Branch toEntity(BranchDTO dto) {
        Branch entity = new Branch();
        BeanUtils.copyProperties(dto, entity);
        if (dto.getCompanyId() != null) {
            entity.setCompany(companyRepo.findById(dto.getCompanyId()).orElse(null));
        }
        return entity;
    }
}