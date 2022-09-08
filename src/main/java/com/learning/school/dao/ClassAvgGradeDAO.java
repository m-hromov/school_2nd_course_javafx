/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.learning.school.dao;

/**
 *
 * @author hromov
 */
public class ClassAvgGradeDAO {
    String number;
    Double avg;

    public ClassAvgGradeDAO(String number, Double avg) {
        this.number = number;
        this.avg = avg;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Double getAvg() {
        return avg;
    }

    public void setAvg(Double avg) {
        this.avg = avg;
    }
    
}
