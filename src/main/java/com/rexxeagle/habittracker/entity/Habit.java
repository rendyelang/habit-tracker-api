package com.rexxeagle.habittracker.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Habit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long habitId;

    @NotBlank
    @Column(length = 100, nullable = false)
    private String title;

    @NotBlank
    @Column(length = 200, nullable = false)
    private String description;

    @Column(nullable = false)
    private int currentStreak;

    @Column(nullable = false)
    private int bestStreak;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    // Constructors
    public Habit() {
    }

    public Habit(String title, String description, int currentStreak, int bestStreak, Users user) {
        this.title = title;
        this.description = description;
        this.currentStreak = currentStreak;
        this.bestStreak = bestStreak;
        this.user = user;
    }

    // Getters and Setters
    public Long getHabitId() {
        return habitId;
    }

    public void setHabitId(Long habitId) {
        this.habitId = habitId;
    }

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
