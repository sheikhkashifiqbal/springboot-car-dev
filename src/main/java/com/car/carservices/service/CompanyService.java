package com.car.carservices.service;

import com.car.carservices.dto.CompanyDTO;
import com.car.carservices.entity.Company;
import com.car.carservices.mapper.CompanyMapper;
import com.car.carservices.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository repo;

    @Autowired
    private CompanyMapper mapper;

    public List<CompanyDTO> getAll() {
        return repo.findAll().stream().map(mapper::toDTO).collect(Collectors.toList());
    }

    public CompanyDTO get(Long id) {
        return repo.findById(id).map(mapper::toDTO).orElse(null);
    }

    public CompanyDTO save(CompanyDTO dto) {
        Company saved = repo.save(mapper.toEntity(dto));
        return mapper.toDTO(saved);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}