/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.learning.school.dao;

/**
 *
 * @author hromov
 */
public class OrganizatorEventDAO {
    private OrganizatorDAO organizator;
    private EventDAO event;

    public OrganizatorEventDAO(OrganizatorDAO organizator, EventDAO event) {
        this.organizator = organizator;
        this.event = event;
    }

    public OrganizatorDAO getOrganizator() {
        return organizator;
    }

    public void setOrganizator(OrganizatorDAO organizator) {
        this.organizator = organizator;
    }

    public EventDAO getEvent() {
        return event;
    }

    public void setEvent(EventDAO event) {
        this.event = event;
    }
    
}
