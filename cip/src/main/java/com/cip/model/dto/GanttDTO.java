package com.cip.model.dto;

import java.util.Date;

public class GanttDTO {

    private String cipNumber;
    private String rout;
    private String start;
    private String end;
    String type;
    String cip;
    String object;
    Date startSearch;
    Date endSearch;


    public GanttDTO() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCip() {
        return cip;
    }

    public void setCip(String cip) {
        this.cip = cip;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
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

    public String getCipNumber() {
        return cipNumber;
    }

    public void setCipNumber(String cipNumber) {
        this.cipNumber = cipNumber;
    }

    public String getRout() {
        return rout;
    }

    public void setRout(String rout) {
        this.rout = rout;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }
}
