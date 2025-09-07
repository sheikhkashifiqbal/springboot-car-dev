package com.car.carservices.service;

import com.car.carservices.dto.BranchDTO;
import com.car.carservices.entity.Branch;
import com.car.carservices.mapper.BranchMapper;
import com.car.carservices.repository.BranchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BranchService {

    @Autowired
    private BranchRepository repo;

    @Autowired
    private BranchMapper mapper;

    public List<BranchDTO> getAll() {
        return repo.findAll().stream().map(mapper::toDTO).collect(Collectors.toList());
    }

    public List<BranchDTO> getByCompany(Long companyId) {
        return repo.findByCompanyCompanyId(companyId).stream().map(mapper::toDTO).collect(Collectors.toList());
    }

    public BranchDTO get(Long id) {
        return repo.findById(id).map(mapper::toDTO).orElse(null);
    }

    public BranchDTO save(BranchDTO dto) {
        Branch saved = repo.save(mapper.toEntity(dto));
        return mapper.toDTO(saved);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}