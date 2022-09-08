/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.learning.school.entity;

/**
 *
 * @author hromov
 */
public class ClassEvent {
    private Event event;
    private Class cl;

    public ClassEvent(Event event, Class cl) {
        this.event = event;
        this.cl = cl;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Class getCl() {
        return cl;
    }

    public void setCl(Class cl) {
        this.cl = cl;
    }
    
}
