package com.simran.consultantmanagement.repository;

import com.simran.consultantmanagement.entity.Consultant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConsultantRepository extends JpaRepository<Consultant, Long> {

    List<Consultant> findByNameContainingIgnoreCaseOrTechnologyContainingIgnoreCase(
            String name,
            String technology
    );
}