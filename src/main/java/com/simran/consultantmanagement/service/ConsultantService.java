package com.simran.consultantmanagement.service;

import com.simran.consultantmanagement.entity.Consultant;
import com.simran.consultantmanagement.repository.ConsultantRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ConsultantService {

    private final ConsultantRepository consultantRepository;

    public ConsultantService(ConsultantRepository consultantRepository) {
        this.consultantRepository = consultantRepository;
    }

    // ============================================================
    // BASIC CRUD OPERATIONS
    // ============================================================

    // Get all consultants
    public List<Consultant> getAllConsultants() {
        return consultantRepository.findAll();
    }

    // Get consultant by ID
    public Consultant getConsultantById(Long id) {
        return consultantRepository.findById(id).orElse(null);
    }

    // Save consultant
    public Consultant saveConsultant(Consultant consultant) {
        return consultantRepository.save(consultant);
    }

    // Delete consultant
    public void deleteConsultant(Long id) {
        consultantRepository.deleteById(id);
    }

    // ============================================================
    // BASIC SEARCH
    // ============================================================

    public List<Consultant> searchConsultants(String keyword) {
        return consultantRepository
                .findByNameContainingIgnoreCaseOrTechnologyContainingIgnoreCase(
                        keyword,
                        keyword
                );
    }

    // ============================================================
    // SEARCH + FILTER + SORTING + PAGINATION
    // ============================================================

    public Page<Consultant> getConsultants(
            String keyword,
            String status,
            int page,
            int size,
            String sortBy,
            String sortDirection) {

        // Prevent invalid page number
        if (page < 0) {
            page = 0;
        }

        // Prevent invalid page size
        if (size <= 0) {
            size = 10;
        }

        // Prevent invalid sorting field
        if (sortBy == null || sortBy.isBlank()) {
            sortBy = "id";
        }

        // Determine sorting direction
        Sort.Direction direction;

        if ("desc".equalsIgnoreCase(sortDirection)) {
            direction = Sort.Direction.DESC;
        } else {
            direction = Sort.Direction.ASC;
        }

        // Create pagination and sorting
        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(direction, sortBy)
        );

        // Check whether keyword was provided
        boolean hasKeyword =
                keyword != null && !keyword.trim().isEmpty();

        // Check whether status was provided
        boolean hasStatus =
                status != null && !status.trim().isEmpty();

        // ========================================================
        // SEARCH + STATUS FILTER
        // ========================================================

        if (hasKeyword && hasStatus) {

            return consultantRepository
                    .findByStatusAndNameContainingIgnoreCaseOrStatusAndTechnologyContainingIgnoreCase(
                            status,
                            keyword,
                            status,
                            keyword,
                            pageable
                    );
        }

        // ========================================================
        // SEARCH ONLY
        // ========================================================

        if (hasKeyword) {

            return consultantRepository
                    .findByNameContainingIgnoreCaseOrTechnologyContainingIgnoreCase(
                            keyword,
                            keyword,
                            pageable
                    );
        }

        // ========================================================
        // STATUS FILTER ONLY
        // ========================================================

        if (hasStatus) {

            return consultantRepository.findByStatus(
                    status,
                    pageable
            );
        }

        // ========================================================
        // NO SEARCH / NO FILTER
        // ========================================================

        return consultantRepository.findAll(pageable);
    }

    // ============================================================
    // DASHBOARD STATISTICS
    // ============================================================

    // Total number of consultants
    public long getTotalConsultants() {
        return consultantRepository.count();
    }

    // Number of Active consultants
    public long getActiveConsultants() {
        return consultantRepository.countByStatus("Active");
    }

    // Number of Available consultants
    public long getAvailableConsultants() {
        return consultantRepository.countByStatus("Available");
    }

    // Number of consultants On Project
    public long getOnProjectConsultants() {
        return consultantRepository.countByStatus("On Project");
    }

    // Number of consultants who joined this month
    public long getNewConsultants() {

        LocalDate today = LocalDate.now();

        LocalDate startOfMonth =
                today.withDayOfMonth(1);

        return consultantRepository.countByJoinedDateBetween(
                startOfMonth,
                today
        );
    }

    // Number of Inactive consultants
    public long getInactiveConsultants() {

        return consultantRepository
                .findAll()
                .stream()
                .filter(c ->
                        "Inactive".equalsIgnoreCase(
                                c.getStatus()
                        )
                )
                .count();
    }
    // ============================================================
// REPORT DATA
// ============================================================

    public java.util.Map<String, Long> getConsultantsByTechnology() {

        return consultantRepository.findAll()
                .stream()
                .collect(
                        java.util.stream.Collectors.groupingBy(
                                Consultant::getTechnology,
                                java.util.stream.Collectors.counting()
                        )
                );
    }


    public java.util.Map<String, Long> getConsultantsByStatus() {

        return consultantRepository.findAll()
                .stream()
                .collect(
                        java.util.stream.Collectors.groupingBy(
                                Consultant::getStatus,
                                java.util.stream.Collectors.counting()
                        )
                );
    }


    public double getAverageExperience() {

        return consultantRepository.findAll()
                .stream()
                .filter(c -> c.getExperience() != null)
                .mapToInt(Consultant::getExperience)
                .average()
                .orElse(0.0);
    }
}