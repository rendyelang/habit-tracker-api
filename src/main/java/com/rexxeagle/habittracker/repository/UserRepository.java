package com.rexxeagle.habittracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rexxeagle.habittracker.entity.Users;

// Inheritance & Interface implementation
public interface UserRepository extends JpaRepository<Users, Long> {
    Users findByEmail(String email);
}
