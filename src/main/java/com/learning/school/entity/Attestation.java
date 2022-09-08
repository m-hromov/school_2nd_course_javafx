/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.learning.school.entity;

import java.time.LocalDate;

/**
 *
 * @author hromov
 */
public class Attestation {
    private Integer id;
    private LocalDate datePassed;
    private LocalDate dateNext;
    private Speciality speciality;
    private Integer category;
    private Teacher teacher;

    public Attestation(Integer id, LocalDate datePassed, LocalDate dateNext, Speciality speciality, Integer category, Teacher teacher) {
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

    public Speciality getSpeciality() {
        return speciality;
    }

    public void setSpeciality(Speciality speciality) {
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
        Attestation d = (Attestation) o;
        return id.equals(d.id);
    }
    
    
}
