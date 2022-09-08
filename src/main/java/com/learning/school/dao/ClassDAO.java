/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.learning.school.dao;

import com.learning.school.entity.Teacher;

/**
 *
 * @author hromov
 */
public class ClassDAO {

    private String number;
    private Teacher teacher;

    public ClassDAO(String number, Teacher teacher) {
        this.number = number;
        this.teacher = teacher;
    }

    public ClassDAO() {

    }

    @Override
    public String toString() {
        return number;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ClassDAO cl = (ClassDAO) o;
        return number.equals(cl.number);
    }

}
