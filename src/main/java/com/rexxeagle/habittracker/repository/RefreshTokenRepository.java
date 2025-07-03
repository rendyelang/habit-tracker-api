package com.rexxeagle.habittracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rexxeagle.habittracker.entity.RefreshToken;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    
}
