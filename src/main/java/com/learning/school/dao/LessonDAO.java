/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.learning.school.dao;

import java.time.LocalDate;

/**
 *
 * @author hromov
 */
public class LessonDAO {
    private Integer id;
    private ScheduleDAO schedule;
    private LocalDate date;

    public LessonDAO(Integer id, ScheduleDAO schedule, LocalDate date) {
        this.id = id;
        this.schedule = schedule;
        this.date = date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ScheduleDAO getSchedule() {
        return schedule;
    }

    public void setSchedule(ScheduleDAO schedule) {
        this.schedule = schedule;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
