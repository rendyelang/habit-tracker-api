package com.rexxeagle.habittracker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rexxeagle.habittracker.entity.HabitProgress;
import com.rexxeagle.habittracker.repository.HabitProgressRepository;

@Service
public class HabitProgressService extends AbstractHabitProgress {
    @Autowired
    private HabitProgressRepository habitProgressRepository;

    @Override
    public HabitProgress addHabitProgress(HabitProgress habitProgress) {
        return habitProgressRepository.save(habitProgress);
    }

    @Override
    public HabitProgress findHabitProgressById(Long id) {
        return habitProgressRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Habit progress not found with id: " + id));
    }

    @Override
    public void deleteHabitProgress(Long id) {
        HabitProgress habitProgress = findHabitProgressById(id);
        habitProgressRepository.delete(habitProgress);
    }

    @Override
    public HabitProgress updateHabitProgress(HabitProgress habitProgress) {
        HabitProgress existingHabitProgress = findHabitProgressById(habitProgress.getProgressId());
        existingHabitProgress.setDate(habitProgress.getDate());
        existingHabitProgress.setStatus(habitProgress.getStatus());
        existingHabitProgress.setHabit(habitProgress.getHabit());
        return habitProgressRepository.save(existingHabitProgress);
    }
}
