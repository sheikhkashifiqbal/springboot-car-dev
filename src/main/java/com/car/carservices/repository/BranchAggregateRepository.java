package com.car.carservices.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/** Class-based repository using EntityManager; no @Entity for 'branch' required. */
@Repository
public class BranchAggregateRepository {

    @PersistenceContext
    private EntityManager em;

    public List<BranchServiceRow> fetchBranchServiceRows() {
        String sql = """
            SELECT 
                b.branch_id        AS branchId,
                b.branch_name      AS branchName,
                b.logo_img         AS logoImg,
                b.branch_cover_img AS branchCoverImg,
                s.service_name     AS serviceName
            FROM branch b
            LEFT JOIN branch_brand_service bbs ON bbs.branch_id = b.branch_id
            LEFT JOIN service_entity s ON s.service_id = bbs.service_id
            WHERE b.status = 'approved' OR b.status = 'APPROVED'
            ORDER BY b.branch_id
        """;

        @SuppressWarnings("unchecked")
        List<Object[]> rows = em.createNativeQuery(sql).getResultList();

        List<BranchServiceRow> result = new ArrayList<>(rows.size());
        for (Object[] r : rows) {
            // order matches the SELECT list
            Long branchId        = r[0] == null ? null : ((Number) r[0]).longValue();
            String branchName    = (String) r[1];
            String logoImg       = (String) r[2];
            String branchCoverImg= (String) r[3];
            String serviceName   = (String) r[4];

            result.add(new BranchServiceRow(branchId, branchName, logoImg, branchCoverImg, serviceName));
        }
        return result;
    }

    /** Simple row holder mirroring the SELECT. */
    public static class BranchServiceRow {
        public final Long branchId;
        public final String branchName;
        public final String logoImg;
        public final String branchCoverImg;
        public final String serviceName; // may be null

        public BranchServiceRow(Long branchId, String branchName, String logoImg, String branchCoverImg, String serviceName) {
            this.branchId = branchId;
            this.branchName = branchName;
            this.logoImg = logoImg;
            this.branchCoverImg = branchCoverImg;
            this.serviceName = serviceName;
        }
    }
}
