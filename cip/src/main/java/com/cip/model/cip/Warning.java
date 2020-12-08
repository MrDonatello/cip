package com.cip.model.cip;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Warning {

    @Id
    private Long id;
    @Column(name = "date_time")
    private String dateTime;
    private int cip;
    private int route;
    @Column(name = "warning_code")
    private int warningCode;

    public Warning() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public int getCip() {
        return cip;
    }

    public void setCip(int cip) {
        this.cip = cip;
    }

    public int getRoute() {
        return route;
    }

    public void setRoute(int route) {
        this.route = route;
    }

    public int getWarningCode() {
        return warningCode;
    }

    public void setWarningCode(int warningCode) {
        this.warningCode = warningCode;
    }
}
