package com.rexxeagle.habittracker.dto;

import com.rexxeagle.habittracker.entity.Users;

public class HabitDTO {
    private String title;
    private String description;
    private int currentStreak;
    private int bestStreak;
    private Users user;

    // Getters and Setters
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public int getCurrentStreak() {
        return currentStreak;
    }
    public void setCurrentStreak(int currentStreak) {
        this.currentStreak = currentStreak;
    }
    public int getBestStreak() {
        return bestStreak;
    }
    public void setBestStreak(int bestStreak) {
        this.bestStreak = bestStreak;
    }
    public Users getUser() {
        return user;
    }
    public void setUser(Users user) {
        this.user = user;
    }
}
