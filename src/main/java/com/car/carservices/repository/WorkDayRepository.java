// com/car/carservices/repository/WorkDayRepository.java
package com.car.carservices.repository;

import com.car.carservices.entity.WorkDay;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface WorkDayRepository extends JpaRepository<WorkDay, Long> {

    List<WorkDay> findByBranch_BranchId(Long branchId);

    Optional<WorkDay> findByBranch_BranchIdAndWorkingDay(Long branchId, String workingDay);

    // Keep this for cleaning up non-canonical rows after upsert
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("DELETE FROM WorkDay w WHERE w.branch.branchId = :branchId AND w.workingDay NOT IN :days")
    void deleteByBranchAndWorkingDayNotIn(@Param("branchId") Long branchId,
                                          @Param("days") Collection<String> days);

    // 🔧 NEW: used by BranchService.delete(Long id)
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("DELETE FROM WorkDay w WHERE w.branch.branchId = :branchId")
    void deleteByBranch_BranchId(@Param("branchId") Long branchId);
}
