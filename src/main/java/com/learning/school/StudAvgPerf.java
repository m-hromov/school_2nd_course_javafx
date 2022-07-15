/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.learning.school;

/**
 *
 * @author hromov
 */
public class StudAvgPerf {
    String name;
    Double avg;
    String perf;

    public StudAvgPerf(String name, Double avg, String perf) {
        this.name = name;
        this.avg = avg;
        this.perf = perf;
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

    public String getPerf() {
        return perf;
    }

    public void setPerf(String perf) {
        this.perf = perf;
    }
    
}
