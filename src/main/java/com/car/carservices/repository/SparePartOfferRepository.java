// src/main/java/com/car/carservices/repository/SparePartOfferRepository.java
package com.car.carservices.repository;

import com.car.carservices.entity.SparePartsRequest;
import com.car.carservices.repository.views.SparePartOfferView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SparePartOfferRepository extends JpaRepository<SparePartsRequest, Long> {

    @Query(value = """
        SELECT
            spr.date                  AS date,
            b.branch_name             AS branchName,
            b.address                 AS address,
            b.city                    AS city,
            spr.vin_number            AS vin,
            sp.spareparts_type        AS sparepartsType,
            bbsp.state                AS state,
            sp.condition              AS sparePart,
            c.manager_mobile          AS managerMobile,
            bbsp.id                   AS id
        FROM spare_parts_request spr
        JOIN spare_parts             sp   ON sp.spareparts_id = spr.spareparts_id
        JOIN branch_brand_spare_part bbsp ON bbsp.spareparts_id = spr.spareparts_id
        JOIN branch                  b    ON b.branch_id       = bbsp.branch_id
        JOIN company                 c    ON c.company_id      = b.company_id
        WHERE spr.user_id = :userId
        ORDER BY spr.date DESC, b.branch_name ASC
        """, nativeQuery = true)
    List<SparePartOfferView> findOffersByUserId(@Param("userId") Long userId);
}
