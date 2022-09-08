/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.learning.school.dao;

import com.learning.school.entity.Student;

/**
 *
 * @author hromov
 */
public class JournalDAO {
    private Integer id;
    private Student student;
    private LessonDAO lesson;
    private Double grade;

    public JournalDAO(Integer id, Student student, LessonDAO lesson, Double grade) {
        this.id = id;
        this.student = student;
        this.lesson = lesson;
        this.grade = grade;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public LessonDAO getLesson() {
        return lesson;
    }

    public void setLesson(LessonDAO lesson) {
        this.lesson = lesson;
    }

    public Double getGrade() {
        return grade;
    }

    public void setGrade(Double grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return lesson.getDate().toString();
    }
    
    
    
}
