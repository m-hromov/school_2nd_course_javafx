/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.learning.school;

public class Teacher {

    private Integer id;
    private String name;
    private String phone;

    public Teacher(Integer id, String name, String phone) {
        this.id = id;
        this.name = name;
        this.phone = phone;
    }

    public Teacher(Teacher teacher) {
        this.id = teacher.getId();
        this.name = teacher.getName();
        this.phone = teacher.getPhone();
    }

    @Override
    public String toString() {
        if (name != null) {
            return name;
        }
        return id.toString();
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Teacher d = (Teacher) o;
        return id == d.getId();
    }
}
