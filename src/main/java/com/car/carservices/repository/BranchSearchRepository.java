// com/car/carservices/repository/BranchSearchRepository.java
package com.car.carservices.repository;

import com.car.carservices.entity.Branch;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BranchSearchRepository extends JpaRepository<Branch, Long> {

    @Query("""
        SELECT DISTINCT b FROM Branch b
        JOIN BranchBrandService bbs ON b.branchId = bbs.branch.branchId
        JOIN ServiceEntity se ON se.serviceId = bbs.service.serviceId
        JOIN Brand br ON br.brandId = bbs.brand.brandId
        LEFT JOIN CarBrandModel cbm ON cbm.brand.brandId = br.brandId
        WHERE (:brand   IS NULL OR LOWER(br.brandName)   = LOWER(:brand))
          AND (:model   IS NULL OR LOWER(cbm.modelName)  = LOWER(:model))
          AND (:service IS NULL OR LOWER(se.serviceName) = LOWER(:service))
          AND (:city    IS NULL OR LOWER(b.location)     = LOWER(:city))
          AND b.status = 'approved'
    """)
    List<Branch> searchBranchesFlexible(
        @Param("brand") String brand,
        @Param("model") String model,
        @Param("service") String service,
        @Param("city") String city
    );
}
