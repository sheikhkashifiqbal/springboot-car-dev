package com.car.carservices.controller;

import com.car.carservices.dto.BranchDTO;
import com.car.carservices.service.BranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
@RequestMapping("/api/branches")
public class BranchController {

    @Autowired
    private BranchService service;

    @GetMapping
    public List<BranchDTO> getAll() { return service.getAll(); }

    @GetMapping("/company/{companyId}")
    public List<BranchDTO> getByCompany(@PathVariable Long companyId) {
        return service.getByCompany(companyId);
    }

    @GetMapping("/{id}")
    public BranchDTO get(@PathVariable Long id) { return service.get(id); }

    @PostMapping
    public BranchDTO create(@RequestBody BranchDTO dto) { return service.save(dto); }

    @PutMapping("/{id}")
    public BranchDTO update(@PathVariable Long id, @RequestBody BranchDTO dto) {
        dto.setBranchId(id);
        return service.save(dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) { service.delete(id); }
}