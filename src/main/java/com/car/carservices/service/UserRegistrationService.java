package com.car.carservices.service;

import com.car.carservices.dto.UserRegistrationDTO;
import com.car.carservices.entity.UserRegistration;
import com.car.carservices.mapper.UserMapper;
import com.car.carservices.repository.UserRegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserRegistrationService {
    @Autowired
    private UserRegistrationRepository repository;

    @Autowired
    private UserMapper userMapper;

    public UserRegistrationDTO save(UserRegistrationDTO dto) {
        UserRegistration saved = repository.save(userMapper.toEntity(dto));
        return userMapper.toDTO(saved);
    }

    public List<UserRegistrationDTO> getAll() {
        return repository.findAll().stream().map(userMapper::toDTO).collect(Collectors.toList());
    }

    public Optional<UserRegistrationDTO> getById(Long id) {
        return repository.findById(id).map(userMapper::toDTO);
    }

    public UserRegistrationDTO update(Long id, UserRegistrationDTO dto) {
        UserRegistration existing = repository.findById(id).orElseThrow();
        dto.setId(existing.getId());
        return userMapper.toDTO(repository.save(userMapper.toEntity(dto)));
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
