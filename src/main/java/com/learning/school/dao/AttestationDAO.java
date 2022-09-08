/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.learning.school.dao;

import com.learning.school.entity.Teacher;

import java.time.LocalDate;

/**
 *
 * @author hromov
 */
public class AttestationDAO {
    private Integer id;
    private LocalDate datePassed;
    private LocalDate dateNext;
    private SpecialityDAO speciality;
    private Integer category;
    private Teacher teacher;

    public AttestationDAO(Integer id, LocalDate datePassed, LocalDate dateNext, SpecialityDAO speciality, Integer category, Teacher teacher) {
        this.id = id;
        this.datePassed = datePassed;
        this.dateNext = dateNext;
        this.speciality = speciality;
        this.category = category;
        this.teacher = teacher;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getDatePassed() {
        return datePassed;
    }

    public void setDatePassed(LocalDate datePassed) {
        this.datePassed = datePassed;
    }

    public LocalDate getDateNext() {
        return dateNext;
    }

    public void setDateNext(LocalDate dateNext) {
        this.dateNext = dateNext;
    }

    public SpecialityDAO getSpeciality() {
        return speciality;
    }

    public void setSpeciality(SpecialityDAO speciality) {
        this.speciality = speciality;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    @Override
    public String toString() {
        return  speciality.getDescription();
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AttestationDAO d = (AttestationDAO) o;
        return id.equals(d.id);
    }
    
    
}
