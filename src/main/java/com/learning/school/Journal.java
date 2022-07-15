/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.learning.school;

import com.learning.school.Student;

/**
 *
 * @author hromov
 */
public class Journal {
    private Integer id;
    private Student student;
    private Lesson lesson;
    private Double grade;

    public Journal(Integer id, Student student, Lesson lesson, Double grade) {
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

    public Lesson getLesson() {
        return lesson;
    }

    public void setLesson(Lesson lesson) {
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
