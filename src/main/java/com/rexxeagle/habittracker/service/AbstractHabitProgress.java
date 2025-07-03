package com.rexxeagle.habittracker.service;

import com.rexxeagle.habittracker.entity.HabitProgress;

public abstract class AbstractHabitProgress {
    public abstract HabitProgress addHabitProgress(HabitProgress habitProgress);
    public abstract HabitProgress findHabitProgressById(Long id);
    public abstract void deleteHabitProgress(Long id);
    public abstract HabitProgress updateHabitProgress(HabitProgress habitProgress);
}
