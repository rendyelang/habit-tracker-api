package com.rexxeagle.habittracker.service;

import java.util.List;

import com.rexxeagle.habittracker.entity.Habit;

public abstract class AbstractHabitService {
    public abstract Habit addHabit(Habit habit);
    public abstract Habit findHabitById(Long id);
    public abstract void deleteHabit(Long id);
    public abstract Habit updateHabit(Habit habit);
    public abstract List<Habit> findAllHabitsByUserId(Long userId);
}
