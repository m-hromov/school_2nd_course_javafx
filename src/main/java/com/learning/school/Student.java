/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.learning.school;

/**
 *
 * @author hromov
 */
public class Student {
    private Integer id;
    private String name;
    private String phone;
    private String phone_parent;
    private Class cl;

    public Student(Integer id, String name, String phone, String phone_parent, Class cl) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.phone_parent = phone_parent;
        this.cl = cl;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone_parent() {
        return phone_parent;
    }

    public void setPhone_parent(String phone_parent) {
        this.phone_parent = phone_parent;
    }

    public Class getCl() {
        return cl;
    }

    public void setCl(Class cl) {
        this.cl = cl;
    }

    @Override
    public String toString() {
        return name;
    }

    
    
}
