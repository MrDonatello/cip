package com.cip.model.dto;

import java.util.Date;

public class Efficiency {

    private String type;
    private int object;
    private Date startSearch;
    private Date endSearch;


    public Efficiency() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public int getObject() {
        return object;
    }

    public void setObject(int object) {
        this.object = object;
    }

    public Date getStartSearch() {
        return startSearch;
    }

    public void setStartSearch(Date startSearch) {
        this.startSearch = startSearch;
    }

    public Date getEndSearch() {
        return endSearch;
    }

    public void setEndSearch(Date endSearch) {
        this.endSearch = endSearch;
    }
}
