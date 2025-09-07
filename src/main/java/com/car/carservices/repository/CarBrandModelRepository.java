package com.car.carservices.repository;

import com.car.carservices.entity.CarBrandModel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface CarBrandModelRepository extends JpaRepository<CarBrandModel, Long> {
 List<CarBrandModel> findByBrandBrandId(Long brandId);
}
