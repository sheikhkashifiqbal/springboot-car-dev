package com.car.carservices.repository;

import com.car.carservices.entity.Branch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BranchRepository extends JpaRepository<Branch, Long> {
    List<Branch> findByCompanyCompanyId(Long companyId);
}