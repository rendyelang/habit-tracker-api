package com.rexxeagle.habittracker.controller;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Map;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rexxeagle.habittracker.dto.HabitProgressDTO;
import com.rexxeagle.habittracker.entity.Habit;
import com.rexxeagle.habittracker.entity.HabitProgress;
import com.rexxeagle.habittracker.service.HabitProgressService;
import com.rexxeagle.habittracker.service.HabitService;

@RestController
@RequestMapping("/api/habit")
public class HabitProgressController {

    @Autowired
    private HabitProgressService habitProgressService;

    @Autowired
    private HabitService habitService;

    // Add new habit progress endpoint
    @PostMapping("/{habitId}/add-progress")
    public ResponseEntity<?> addHabitProgress(@PathVariable Long habitId, @RequestBody HabitProgressDTO habitProgressDTO) {
        try {
            Habit habit = habitService.findHabitById(habitId);

            HabitProgress habitProgress = new HabitProgress();
            habitProgress.setDate(Date.valueOf(LocalDate.now()));
            habitProgress.setStatus(habitProgressDTO.getStatus());
            habitProgress.setHabit(habit);

            habitProgressService.addHabitProgress(habitProgress);

            return ResponseEntity.status(201).body(
                Map.of(
                    "message", "Habit progress added successfully",
                    "progress", Map.of(
                        "progressId", habitProgress.getProgressId(),
                        "date", habitProgress.getDate(),
                        "status", habitProgress.getStatus(),
                        "habitId", habit.getHabitId()
                    )
                )
            );
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error adding habit progress: " + e.getMessage());
        }
    }

    // Get habit progress by habitId endpoint
    @GetMapping("/{habitId}/progress")
    public ResponseEntity<?> getHabitProgressByHabitId(@PathVariable Long habitId) {
        try {
            Habit habit = habitService.findHabitById(habitId);
            if (habit == null) {
                return ResponseEntity.status(404).body("Habit not found with id: " + habitId);
            }

            HabitProgress habitProgress = habitProgressService.findHabitProgressById(habitId);
            if (habitProgress == null) {
                return ResponseEntity.status(404).body("No progress found for habit with id: " + habitId);
            }

            HabitProgressDTO response = new HabitProgressDTO();
            response.setTitle(habit.getTitle());
            response.setDate(habitProgress.getDate());
            response.setStatus(habitProgress.getStatus());
            response.setHabitId(habit.getHabitId());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error retrieving habit progress: " + e.getMessage());
        }
    }

    // Update habit progress endpoint
    @PatchMapping("/{habitId}/progress/{progressId}")
    public ResponseEntity<?> updateHabitProgress(@PathVariable Long habitId, @PathVariable Long progressId, @RequestBody HabitProgressDTO habitProgressDTO) {
        try {
            Habit habit = habitService.findHabitById(habitId);
            if (habit == null) {
                return ResponseEntity.status(404).body("Habit not found with id: " + habitId);
            }

            HabitProgress existingProgress = habitProgressService.findHabitProgressById(progressId);
            if (existingProgress == null) {
                return ResponseEntity.status(404).body("Habit progress not found with id: " + progressId);
            }

            existingProgress.setDate(Date.valueOf(LocalDate.now()));
            existingProgress.setStatus(habitProgressDTO.getStatus());
            existingProgress.setHabit(habit);

            HabitProgress updatedProgress = habitProgressService.updateHabitProgress(existingProgress);

            return ResponseEntity.ok(
                Map.of(
                    "message", "Habit progress updated successfully",
                    "progress", Map.of(
                        "progressId", updatedProgress.getProgressId(),
                        "date", updatedProgress.getDate(),
                        "status", updatedProgress.getStatus(),
                        "habitId", updatedProgress.getHabit().getHabitId()
                    )
                )
            );
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error updating habit progress: " + e.getMessage());
        }
    }

    // Delete habit progress endpoint
    @DeleteMapping("/progress/delete/{progressId}")
    public ResponseEntity<?> deleteHabitProgress(@PathVariable Long progressId) {
    try {
        HabitProgress existingProgress = habitProgressService.findHabitProgressById(progressId);
        if (existingProgress == null) {
            return ResponseEntity.status(404).body("Habit progress not found with id: " + progressId);
        }

        habitProgressService.deleteHabitProgress(progressId);

        return ResponseEntity.ok(Map.of("message", "Habit progress deleted successfully"));
    } catch (Exception e) {
        return ResponseEntity.status(500).body("Error deleting habit progress: " + e.getMessage());
    }
}
}
