/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.learning.school.dao;

/**
 *
 * @author hromov
 */
public class ClassNameAvgDAO {
    String number;
    String name;
    Double avg;

    public ClassNameAvgDAO(String number, String name, Double avg) {
        this.number = number;
        this.name = name;
        this.avg = avg;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getAvg() {
        return avg;
    }

    public void setAvg(Double avg) {
        this.avg = avg;
    }
    
}
