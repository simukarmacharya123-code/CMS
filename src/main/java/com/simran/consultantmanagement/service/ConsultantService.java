package com.simran.consultantmanagement.service;

import com.simran.consultantmanagement.entity.Consultant;
import com.simran.consultantmanagement.repository.ConsultantRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsultantService {

    private final ConsultantRepository consultantRepository;

    public ConsultantService(ConsultantRepository consultantRepository) {
        this.consultantRepository = consultantRepository;
    }

    public List<Consultant> getAllConsultants() {
        return consultantRepository.findAll();
    }

    public Consultant getConsultantById(Long id) {
        return consultantRepository.findById(id).orElse(null);
    }

    public Consultant saveConsultant(Consultant consultant) {
        return consultantRepository.save(consultant);
    }

    public void deleteConsultant(Long id) {
        consultantRepository.deleteById(id);
    }
    public List<Consultant> searchConsultants(String keyword) {
        return consultantRepository
                .findByNameContainingIgnoreCaseOrTechnologyContainingIgnoreCase(
                        keyword,
                        keyword
                );
    }
}