package com.rexxeagle.habittracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rexxeagle.habittracker.entity.HabitProgress;

public interface HabitProgressRepository extends JpaRepository<HabitProgress, Long> {
    
}
