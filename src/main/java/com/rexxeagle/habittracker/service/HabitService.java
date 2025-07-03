package com.rexxeagle.habittracker.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rexxeagle.habittracker.entity.Habit;
import com.rexxeagle.habittracker.repository.HabitRepository;

@Service
public class HabitService extends AbstractHabitService {

    @Autowired
    private HabitRepository habitRepository;

    @Override
    public Habit addHabit(Habit habit) {
        return habitRepository.save(habit);
    }

    @Override
    public Habit findHabitById(Long id) {
        return habitRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Habit not found with id: " + id));
    }

    @Override
    public void deleteHabit(Long id) {
        Habit habit = findHabitById(id);
        habitRepository.delete(habit);
    }

    @Override
    public Habit updateHabit(Habit habit) {
        Habit existingHabit = findHabitById(habit.getHabitId());
        existingHabit.setTitle(habit.getTitle());
        existingHabit.setDescription(habit.getDescription());
        existingHabit.setCurrentStreak(habit.getCurrentStreak());
        existingHabit.setUser(habit.getUser());
        return habitRepository.save(existingHabit);
    }

    @Override
    public List<Habit> findAllHabitsByUserId(Long userId) {
        List<Habit> habits = habitRepository.findByUser_UserId(userId);
        if (habits.isEmpty()) {
            throw new RuntimeException("No habits found for user with id: " + userId);
        }
        return habits;
    }
}
