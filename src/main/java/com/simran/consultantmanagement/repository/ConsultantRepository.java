package com.simran.consultantmanagement.repository;

import com.simran.consultantmanagement.entity.Consultant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ConsultantRepository extends JpaRepository<Consultant, Long> {

    // Basic search
    List<Consultant> findByNameContainingIgnoreCaseOrTechnologyContainingIgnoreCase(
            String name,
            String technology
    );

    // Search with pagination
    Page<Consultant> findByNameContainingIgnoreCaseOrTechnologyContainingIgnoreCase(
            String name,
            String technology,
            Pageable pageable
    );

    // Search + status filter + pagination
    Page<Consultant>
    findByStatusAndNameContainingIgnoreCaseOrStatusAndTechnologyContainingIgnoreCase(
            String status1,
            String name,
            String status2,
            String technology,
            Pageable pageable
    );

    // Status filter + pagination
    Page<Consultant> findByStatus(
            String status,
            Pageable pageable
    );

    // Dashboard statistics
    long countByStatus(String status);

    long countByJoinedDateBetween(
            LocalDate startDate,
            LocalDate endDate
    );
}