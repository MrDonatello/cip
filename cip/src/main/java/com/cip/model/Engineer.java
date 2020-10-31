package com.cip.model;

import javax.persistence.Entity;

/*@Entity*/
public class Engineer extends User {
    private String position;

    public Engineer() {

    }

    public Engineer(String firstName, String lastName, String patronymic, String login, String password, int id, Role role, String position) {
        super(firstName, lastName, patronymic, login, password, id, role);
        this.position = position;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

}
