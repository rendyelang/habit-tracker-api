package com.rexxeagle.habittracker.controller;

import java.util.List;
import java.util.Map;

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

import com.rexxeagle.habittracker.dto.HabitDTO;
import com.rexxeagle.habittracker.entity.Habit;
import com.rexxeagle.habittracker.service.HabitService;

@RestController
@RequestMapping("/api/habit")
public class HabitController {

    @Autowired
    private HabitService habitService;

    // Add new habbit endpoint
    @PostMapping("/add-habit")
    public ResponseEntity<?> addHabit(@RequestBody HabitDTO habitDTO) {
        try {
            Habit habit = new Habit(habitDTO.getTitle(),
                                    habitDTO.getDescription(),
                                    habitDTO.getCurrentStreak(),
                                    habitDTO.getBestStreak(),
                                    habitDTO.getUser());
            habitService.addHabit(habit);
            return ResponseEntity.status(201).body(
                Map.of(
                    "message", "Habit added successfully",
                    "title", habit.getTitle()
                )
            );
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error adding habit: " + e.getMessage());
        }
    }

    // Display habits by userId endpoint
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getHabitsByUserId(@PathVariable Long userId) {
        try {
            List<Habit> habits = habitService.findAllHabitsByUserId(userId);
            List<HabitDTO> response = habits.stream().map(habit -> {
                HabitDTO dto = new HabitDTO();
                dto.setTitle(habit.getTitle());
                dto.setDescription(habit.getDescription());
                dto.setCurrentStreak(habit.getCurrentStreak());
                dto.setBestStreak(habit.getBestStreak());
                // dto.setUser(habit.getUser());
                return dto;
            }).toList();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // TODO: handle exception
            return ResponseEntity.status(500).body(
                Map.of(
                    "message", "Error retrieving habits",
                    "error", e.getMessage()
                )
            );
        }
    }

    // Get detail habit by id endpoint
    @GetMapping("/{id}")
    public ResponseEntity<?> getHabitById(@PathVariable Long id) {
        try {
            Habit habit = habitService.findHabitById(id);
            HabitDTO dto = new HabitDTO();
            dto.setTitle(habit.getTitle());
            dto.setDescription(habit.getDescription());
            dto.setCurrentStreak(habit.getCurrentStreak());
            dto.setBestStreak(habit.getBestStreak());
            // dto.setUser(habit.getUser());
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            return ResponseEntity.status(404).body(
                Map.of(
                    "message", "Habit not found",
                    "error", e.getMessage()
                )
            );
        }
    }

    // Edit habit by id endpoint
    @PatchMapping("{id}")
    public ResponseEntity<?> editHabit(@PathVariable Long id, @RequestBody HabitDTO habitDTO) {
        try {
            Habit habit = habitService.findHabitById(id);
            if (habit != null) {
                // Data before update
                Map<String, String> beforeUpdate = Map.of(
                    "title", habit.getTitle(),
                    "description", habit.getDescription(),
                    "currentStreak", String.valueOf(habit.getCurrentStreak()),
                    "bestStreak", String.valueOf(habit.getBestStreak())
                );

                habit.setTitle(habitDTO.getTitle());
                habit.setDescription(habitDTO.getDescription());
                habit.setCurrentStreak(habitDTO.getCurrentStreak());
                habit.setBestStreak(habitDTO.getBestStreak());
                // habit.setUser(habitDTO.getUser());

                Habit updatedHabit = habitService.updateHabit(habit);

                // Data after update
                Map<String, String> afterUpdate = Map.of(
                    "title", updatedHabit.getTitle(),
                    "description", updatedHabit.getDescription(),
                    "currentStreak", String.valueOf(updatedHabit.getCurrentStreak()),
                    "bestStreak", String.valueOf(updatedHabit.getBestStreak())
                );

                return ResponseEntity.ok(
                    Map.of(
                        "message", "Habit updated successfully",
                        "beforeUpdate", beforeUpdate,
                        "afterUpdate", afterUpdate
                    )
                );
            } else {
                return ResponseEntity.status(404).body(
                    Map.of("message", "Habit not found")
                );
            }
        } catch (Exception e) {
            // TODO: handle exception
            return ResponseEntity.status(500).body(
                Map.of(
                    "message", "Error updating habit",
                    "error", e.getMessage()
                )
            );
        }
    }

    // Delete habit by id endpoint
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteHabit(@PathVariable Long id) {
        try {
            habitService.deleteHabit(id);
            return ResponseEntity.ok(
                Map.of("message", "Habit deleted successfully")
            );
        } catch (Exception e) {
            return ResponseEntity.status(404).body(
                Map.of(
                    "message", "Error deleting habit",
                    "error", e.getMessage()
                )
            );
        }
    }
}
