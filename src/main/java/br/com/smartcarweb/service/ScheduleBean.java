package br.com.smartcarweb.service;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class ScheduleBean {

    @PersistenceContext(unitName = "myh2")
    private EntityManager em;

    public void getAllSchedules() {
        System.out.println("getAllSchedules"+em.getEntityManagerFactory());

    }
}