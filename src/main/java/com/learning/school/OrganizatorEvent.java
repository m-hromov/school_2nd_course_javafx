/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.learning.school;

import java.util.logging.Logger;

/**
 *
 * @author hromov
 */
public class OrganizatorEvent {
    private Organizator organizator;
    private Event event;

    public OrganizatorEvent(Organizator organizator, Event event) {
        this.organizator = organizator;
        this.event = event;
    }

    public Organizator getOrganizator() {
        return organizator;
    }

    public void setOrganizator(Organizator organizator) {
        this.organizator = organizator;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
    
}
