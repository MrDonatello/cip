package com.cip.model;

import javax.persistence.Entity;

@Entity
public class Cip {

    private Long id;
    private boolean main_feed_pump;
    private boolean output_flow_lye_valve;
    private int output_flow_temperature_sensor;

    public Cip(boolean main_feed_pump, boolean output_flow_lye_valve, int output_flow_temperature_sensor) {
        this.main_feed_pump = main_feed_pump;
        this.output_flow_lye_valve = output_flow_lye_valve;
        this.output_flow_temperature_sensor = output_flow_temperature_sensor;
    }

    public Cip() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isMain_feed_pump() {
        return main_feed_pump;
    }

    public void setMain_feed_pump(boolean main_feed_pump) {
        this.main_feed_pump = main_feed_pump;
    }

    public boolean isOutput_flow_lye_valve() {
        return output_flow_lye_valve;
    }

    public void setOutput_flow_lye_valve(boolean output_flow_lye_valve) {
        this.output_flow_lye_valve = output_flow_lye_valve;
    }

    public int getOutput_flow_temperature_sensor() {
        return output_flow_temperature_sensor;
    }

    public void setOutput_flow_temperature_sensor(int output_flow_temperature_sensor) {
        this.output_flow_temperature_sensor = output_flow_temperature_sensor;
    }
}
