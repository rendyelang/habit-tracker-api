package com.rexxeagle.habittracker.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rexxeagle.habittracker.entity.Habit;

public interface HabitRepository extends JpaRepository<Habit, Long> {
    List<Habit> findByUser_UserId(Long userId);
    
}
