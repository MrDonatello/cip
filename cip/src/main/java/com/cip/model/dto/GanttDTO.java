package com.cip.model.dto;

public class GanttDTO {

    private String cipNumber;
    private String rout;
    private String start;
    private String end;

    public GanttDTO() {
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
