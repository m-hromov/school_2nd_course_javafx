/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.learning.school.dao;

/**
 *
 * @author hromov
 */
public class DayDAO {
    private Integer id;
    private String day;

    public DayDAO(Integer id, String day) {
        this.id = id;
        this.day = day;
    }

    @Override
    public String toString() {
        if (day != null) {
            return day;
        }
        return id.toString();
    }
    

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DayDAO d = (DayDAO) o;
        return id.equals(d.id);
    }
}
