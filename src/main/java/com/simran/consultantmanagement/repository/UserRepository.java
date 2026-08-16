package com.simran.consultantmanagement.repository;

import com.simran.consultantmanagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}