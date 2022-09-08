/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.learning.school.dao;

/**
 *
 * @author hromov
 */
public class SubjectDAO {

    private Integer id;
    private String description;
    private String number_audience;

    @Override
    public String toString() {
        if (description != null) {
            return description;
        }
        return id.toString();
    }

    public SubjectDAO(Integer id, String description, String number_audience) {
        this.id = id;
        this.description = description;
        this.number_audience = number_audience;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNumber_audience() {
        return number_audience;
    }

    public void setNumber_audience(String number_audience) {
        this.number_audience = number_audience;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SubjectDAO d = (SubjectDAO) o;
        return id == d.getId();
    }
}
