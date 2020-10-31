package com.cip.model;

import javax.persistence.Entity;

/*@Entity*/
public class Operator extends User {

    public Operator() {
    }

    public Operator(String firstName, String lastName, String patronymic, String login, String password, int id, Role role) {
        super(firstName, lastName, patronymic, login, password, id, role);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
