package com.car.carservices.service.impl;

import com.car.carservices.dto.SparePartsRequestDTO;
import com.car.carservices.entity.SparePartsRequest;
import com.car.carservices.mapper.SparePartsRequestMapper;
import com.car.carservices.repository.SparePartsRequestRepository;
import com.car.carservices.service.SparePartsRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SparePartsRequestServiceImpl implements SparePartsRequestService {

    @Autowired
    private SparePartsRequestRepository repository;

    @Autowired
    private SparePartsRequestMapper mapper;

    @Override
    public SparePartsRequestDTO create(SparePartsRequestDTO dto) {
        return mapper.toDTO(repository.save(mapper.toEntity(dto)));
    }

    @Override
    public SparePartsRequestDTO get(Long id) {
        return mapper.toDTO(repository.findById(id).orElseThrow());
    }

    @Override
    public List<SparePartsRequestDTO> getAll() {
        return repository.findAll().stream().map(mapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public SparePartsRequestDTO update(Long id, SparePartsRequestDTO dto) {
        SparePartsRequest entity = repository.findById(id).orElseThrow();
        entity.setUserId(dto.getUserId());
        entity.setDate(dto.getDate());
        entity.setVinNumber(dto.getVinNumber());
        entity.setCategoryId(dto.getCategoryId());
        entity.setState(dto.getState());
        entity.setRequestStatus(dto.getRequestStatus());
        entity.setCity(dto.getCity());
        return mapper.toDTO(repository.save(entity));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
