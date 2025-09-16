// com/car/carservices/service/BranchService.java
package com.car.carservices.service;

import com.car.carservices.dto.BranchDTO;
import com.car.carservices.entity.Branch;
import com.car.carservices.entity.WorkDay;
import com.car.carservices.mapper.BranchMapper;
import com.car.carservices.repository.BranchRepository;
import com.car.carservices.repository.WorkDayRepository;
import com.car.carservices.util.DayNameUtil;
import com.car.carservices.util.LocationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class BranchService {

    @Autowired private BranchRepository repo;
    @Autowired private BranchMapper mapper;
    @Autowired private WorkDayRepository workDayRepo;

    private static final DateTimeFormatter HHMM = DateTimeFormatter.ofPattern("HH:mm");
    private static final String DEFAULT_FROM = "09:00";
    private static final String DEFAULT_TO   = "18:00";

    public List<BranchDTO> getAll() {
        return repo.findAll().stream()
                .map(mapper::toDTO)
                .peek(this::hydrateScheduleFieldsFromDBEnsureDefaults) // NEVER null
                .collect(Collectors.toList());
    }

    public List<BranchDTO> getByCompany(Long companyId) {
        return repo.findByCompanyCompanyId(companyId).stream()
                .map(mapper::toDTO)
                .peek(this::hydrateScheduleFieldsFromDBEnsureDefaults) // NEVER null
                .collect(Collectors.toList());
    }

    public BranchDTO get(Long id) {
        return repo.findById(id)
                .map(mapper::toDTO)
                .map(dto -> { hydrateScheduleFieldsFromDBEnsureDefaults(dto); return dto; }) // NEVER null
                .orElse(null);
    }

    @Transactional
    public BranchDTO save(BranchDTO dto) {
        Branch entity = mapper.toEntity(dto);

        // Extract lat/lon from Google Maps URL if not provided directly
        if ((entity.getLatitude() == null || entity.getLongitude() == null) && dto.getLocation() != null) {
            Double[] coords = LocationUtil.extractLatLon(dto.getLocation());
            if (coords != null) {
                entity.setLatitude(coords[0]);
                entity.setLongitude(coords[1]);
            }
        }

        Branch saved = repo.save(entity);

        // Always replace work_days (defaults if missing)
        List<String> days = normalizedOrDefaultDays(dto.getWorkDays());
        String from = (dto.getFrom() != null && !dto.getFrom().isBlank()) ? dto.getFrom() : DEFAULT_FROM;
        String to   = (dto.getTo()   != null && !dto.getTo().isBlank())   ? dto.getTo()   : DEFAULT_TO;
        upsertWorkDays(saved, days, from, to);

        BranchDTO out = mapper.toDTO(saved);
        out.setWorkDays(days);
        out.setFrom(from);
        out.setTo(to);
        return out;
    }

    @Transactional
    public BranchDTO update(Long id, BranchDTO dto) {
        Branch entity = repo.findById(id).orElseThrow();

        entity.setBranchName(dto.getBranchName());
        entity.setBranchCode(dto.getBranchCode());
        entity.setBranchManagerName(dto.getBranchManagerName());
        entity.setBranchManagerSurname(dto.getBranchManagerSurname());
        entity.setBranchAddress(dto.getBranchAddress());
        entity.setLocation(dto.getLocation());
        entity.setLoginEmail(dto.getLoginEmail());
        entity.setPassword(dto.getPassword());
        entity.setLogoImg(dto.getLogoImg());
        entity.setBranchCoverImg(dto.getBranchCoverImg());
        entity.setStatus(dto.getStatus());
        entity.setLatitude(dto.getLatitude());
        entity.setLongitude(dto.getLongitude());

        if ((entity.getLatitude() == null || entity.getLongitude() == null) && dto.getLocation() != null) {
            Double[] coords = LocationUtil.extractLatLon(dto.getLocation());
            if (coords != null) {
                entity.setLatitude(coords[0]);
                entity.setLongitude(coords[1]);
            }
        }

        Branch saved = repo.save(entity);

        // Always replace work_days (defaults if missing)
        List<String> days = normalizedOrDefaultDays(dto.getWorkDays());
        String from = (dto.getFrom() != null && !dto.getFrom().isBlank()) ? dto.getFrom() : DEFAULT_FROM;
        String to   = (dto.getTo()   != null && !dto.getTo().isBlank())   ? dto.getTo()   : DEFAULT_TO;
        upsertWorkDays(saved, days, from, to);

        BranchDTO out = mapper.toDTO(saved);
        out.setWorkDays(days);
        out.setFrom(from);
        out.setTo(to);
        return out;
    }

    @Transactional
    public void delete(Long id) {
        // 1) delete work_days for this branch
        workDayRepo.deleteByBranch_BranchId(id);
        // 2) delete the branch
        repo.deleteById(id);
    }

    /* ------------ helpers ------------ */

    private List<String> normalizedOrDefaultDays(List<String> raw) {
        if (raw == null || raw.isEmpty()) return new ArrayList<>(DayNameUtil.week()); // all active
        List<String> out = new ArrayList<>();
        for (String s : raw) {
            String n = DayNameUtil.normalize(s);
            if (n != null) out.add(n);
        }
        return out.isEmpty() ? new ArrayList<>(DayNameUtil.week()) : out;
    }

    // com/car/carservices/service/BranchService.java
// ...
private void upsertWorkDays(Branch branch, List<String> desiredActiveDays, String from, String to) {
    // Canonical week + normalize incoming active list
    List<String> week = DayNameUtil.week(); // ["monday","tuesday",...]
    Set<String> active = new java.util.LinkedHashSet<>();
    if (desiredActiveDays != null) {
        for (String s : desiredActiveDays) {
            String n = DayNameUtil.normalize(s);
            if (n != null) active.add(n);
        }
    }
    if (active.isEmpty()) active.addAll(week); // default: all active

    // Times
    java.time.LocalTime fromT = java.time.LocalTime.parse(from, HHMM);
    java.time.LocalTime toT   = java.time.LocalTime.parse(to,   HHMM);

    // UPSERT 7 rows (one per canonical day)
    for (String day : week) {
        com.car.carservices.entity.WorkDay wd = workDayRepo
                .findByBranch_BranchIdAndWorkingDay(branch.getBranchId(), day)
                .orElseGet(() -> {
                    com.car.carservices.entity.WorkDay w = new com.car.carservices.entity.WorkDay();
                    w.setBranch(branch);
                    w.setWorkingDay(day);
                    return w;
                });

        wd.setFrom(fromT);
        wd.setTo(toT);
        wd.setStatus(active.contains(day) ? "active" : "inactive");
        workDayRepo.save(wd); // update or insert
    }

    // OPTIONAL: clean up any legacy rows with non-canonical day names
    workDayRepo.deleteByBranchAndWorkingDayNotIn(branch.getBranchId(), week);
}

    /** Always fills workDays/from/to so responses are never null (defaults when no rows). */
    private void hydrateScheduleFieldsFromDBEnsureDefaults(BranchDTO dto) {
        if (dto == null || dto.getBranchId() == null) {
            // still return defaults so they are not null
            dto.setWorkDays(new ArrayList<>(DayNameUtil.week()));
            dto.setFrom(DEFAULT_FROM);
            dto.setTo(DEFAULT_TO);
            return;
        }
        List<WorkDay> rows = workDayRepo.findByBranch_BranchId(dto.getBranchId());
        if (rows == null || rows.isEmpty()) {
            dto.setWorkDays(new ArrayList<>(DayNameUtil.week()));
            dto.setFrom(DEFAULT_FROM);
            dto.setTo(DEFAULT_TO);
            return;
        }
        List<String> active = rows.stream()
                .filter(w -> "active".equalsIgnoreCase(w.getStatus()))
                .map(WorkDay::getWorkingDay)
                .toList();
        dto.setWorkDays(active.isEmpty() ? new ArrayList<>(DayNameUtil.week()) : active);

        LocalTime minFrom = rows.stream().map(WorkDay::getFrom).min(LocalTime::compareTo).orElse(LocalTime.parse(DEFAULT_FROM, HHMM));
        LocalTime maxTo   = rows.stream().map(WorkDay::getTo).max(LocalTime::compareTo).orElse(LocalTime.parse(DEFAULT_TO,   HHMM));
        dto.setFrom(minFrom.format(HHMM));
        dto.setTo(maxTo.format(HHMM));
    }
}
