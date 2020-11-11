package com.cip.model.cip;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Cip1 extends Cip {

    @Id
    private Long id;
    private boolean steam_shut_valve;

    public Cip1() {
    }

    public boolean isSteam_shut_valve() {
        return steam_shut_valve;
    }

    public void setSteam_shut_valve(boolean steam_shut_valve) {
        this.steam_shut_valve = steam_shut_valve;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
