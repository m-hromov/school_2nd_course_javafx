/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.learning.school.entity;

import java.time.LocalTime;

/**
 *
 * @author hromov
 */
public class Schedule {

    private Integer id;
    private LocalTime time_start;
    private LocalTime time_end;
    private Day day;
    private Subject subject;
    private Teacher teacher;
    private Class cl;

    public Schedule(Schedule sch) {
        this.id = sch.getId();
        this.time_start = sch.getTime_start();
        this.time_end = sch.getTime_end();
        this.day = sch.getDay();
        this.subject = sch.getSubject();
        this.teacher = sch.getTeacher();
        this.cl = sch.getCl();
    }
    
    public Schedule(Integer id, LocalTime time_start, LocalTime time_end, Day day, Subject subject, Teacher teacher, Class cl) {
        this.id = id;
        this.time_start = time_start;
        this.time_end = time_end;
        this.day = day;
        this.subject = subject;
        this.teacher = teacher;
        this.cl = cl;
    }

    @Override
    public String toString() {
        return cl.getNumber() + " " + subject.getDescription() + " " 
                + teacher.getName() + " " + day.getDay() + " " + time_start 
                + " " + time_end; 
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalTime getTime_start() {
        return time_start;
    }

    public void setTime_start(LocalTime time_start) {
        this.time_start = time_start;
    }

    public LocalTime getTime_end() {
        return time_end;
    }

    public void setTime_end(LocalTime time_end) {
        this.time_end = time_end;
    }

    public Day getDay() {
        return day;
    }

    public void setDay(Day day) {
        this.day = day;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Class getCl() {
        return cl;
    }

    public void setCl(Class cl) {
        this.cl = cl;
    }

    
    
    
}
