package com.cip.model.cip;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Cip1 extends Cip {

    @Column(name = "change_route_valve")
    private boolean changeRouteValve;

    public Cip1() {
    }

    public boolean isChangeRouteValve() {
        return changeRouteValve;
    }

    public void setChangeRouteValve(boolean changeRouteValve) {
        this.changeRouteValve = changeRouteValve;
    }

}
