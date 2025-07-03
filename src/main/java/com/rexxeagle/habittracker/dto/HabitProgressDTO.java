package com.rexxeagle.habittracker.dto;

import java.sql.Date;

import com.rexxeagle.habittracker.entity.ProgressStatus;

public class HabitProgressDTO {
    String title;
    Date date;
    ProgressStatus status;
    Long habitId;

    public HabitProgressDTO() {
    }

    public HabitProgressDTO(Date date, ProgressStatus status, Long habitId) {
        this.date = date;
        this.status = status;
        this.habitId = habitId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public ProgressStatus getStatus() {
        return status;
    }
    public void setStatus(ProgressStatus status) {
        this.status = status;
    }
    public Long getHabitId() {
        return habitId;
    }
    public void setHabitId(Long habitId) {
        this.habitId = habitId;
    }
}
