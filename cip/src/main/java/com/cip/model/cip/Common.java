package com.cip.model.cip;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Common {

    @Id
    private Long id;
    @Column(name = "date_time")
    private String dateTime;
    @Column(name = "water_to_lye_tank_valve")
    private boolean waterToLyeTankValve;
    @Column(name = "water_to_acid_tank_valve")
    private boolean waterToAcidTankValve;
    @Column(name = "water_to_water_tank_valve")
    private boolean waterToWaterTankValve;
    @Column(name = "lye_to_lye_tank_pump")
    private boolean lyeToLyeTankPump;
    @Column(name = "acid_to_acid_tank_pump")
    private boolean acidToAcidTankPump;
    @Column(name = "upper_level_lye_tank_valve")
    private boolean upperLevelLyeTankValve;
    @Column(name = "lower_level_lye_tank_valve")
    private boolean lowerLevelLyeTankValve;
    @Column(name = "upper_level_acid_tank_valve")
    private boolean upperLevelAcidTankValve;
    @Column(name = "lower_level_acid_tank_valve")
    private boolean lower_level_acid_tank_valve;
    @Column(name = "upper_level_rinse_water_tank_valve")
    private boolean upperLevelRinseWaterTankValve;
    @Column(name = "lower_level_rinse_water_tank_valve")
    private boolean lowerLevelRinseWaterTankValve;
    @Column(name = "upper_level_pure_water_tank_valve")
    private boolean upperLevelPureWaterTankValve;
    @Column(name = "lower_level_pure_water_tank_valve")
    private boolean lowerLevelPureWaterTankValve;

    public Common() {
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

    public boolean isWaterToLyeTankValve() {
        return waterToLyeTankValve;
    }

    public void setWaterToLyeTankValve(boolean waterToLyeTankValve) {
        this.waterToLyeTankValve = waterToLyeTankValve;
    }

    public boolean isWaterToAcidTankValve() {
        return waterToAcidTankValve;
    }

    public void setWaterToAcidTankValve(boolean waterToAcidTankValve) {
        this.waterToAcidTankValve = waterToAcidTankValve;
    }

    public boolean isWaterToWaterTankValve() {
        return waterToWaterTankValve;
    }

    public void setWaterToWaterTankValve(boolean waterToWaterTankValve) {
        this.waterToWaterTankValve = waterToWaterTankValve;
    }

    public boolean isLyeToLyeTankPump() {
        return lyeToLyeTankPump;
    }

    public void setLyeToLyeTankPump(boolean lyeToLyeTankPump) {
        this.lyeToLyeTankPump = lyeToLyeTankPump;
    }

    public boolean isAcidToAcidTankPump() {
        return acidToAcidTankPump;
    }

    public void setAcidToAcidTankPump(boolean acidToAcidTankPump) {
        this.acidToAcidTankPump = acidToAcidTankPump;
    }

    public boolean isUpperLevelLyeTankValve() {
        return upperLevelLyeTankValve;
    }

    public void setUpperLevelLyeTankValve(boolean upperLevelLyeTankValve) {
        this.upperLevelLyeTankValve = upperLevelLyeTankValve;
    }

    public boolean isLowerLevelLyeTankValve() {
        return lowerLevelLyeTankValve;
    }

    public void setLowerLevelLyeTankValve(boolean lowerLevelLyeTankValve) {
        this.lowerLevelLyeTankValve = lowerLevelLyeTankValve;
    }

    public boolean isUpperLevelAcidTankValve() {
        return upperLevelAcidTankValve;
    }

    public void setUpperLevelAcidTankValve(boolean upperLevelAcidTankValve) {
        this.upperLevelAcidTankValve = upperLevelAcidTankValve;
    }

    public boolean isLower_level_acid_tank_valve() {
        return lower_level_acid_tank_valve;
    }

    public void setLower_level_acid_tank_valve(boolean lower_level_acid_tank_valve) {
        this.lower_level_acid_tank_valve = lower_level_acid_tank_valve;
    }

    public boolean isUpperLevelRinseWaterTankValve() {
        return upperLevelRinseWaterTankValve;
    }

    public void setUpperLevelRinseWaterTankValve(boolean upperLevelRinseWaterTankValve) {
        this.upperLevelRinseWaterTankValve = upperLevelRinseWaterTankValve;
    }

    public boolean isLowerLevelRinseWaterTankValve() {
        return lowerLevelRinseWaterTankValve;
    }

    public void setLowerLevelRinseWaterTankValve(boolean lowerLevelRinseWaterTankValve) {
        this.lowerLevelRinseWaterTankValve = lowerLevelRinseWaterTankValve;
    }

    public boolean isUpperLevelPureWaterTankValve() {
        return upperLevelPureWaterTankValve;
    }

    public void setUpperLevelPureWaterTankValve(boolean upperLevelPureWaterTankValve) {
        this.upperLevelPureWaterTankValve = upperLevelPureWaterTankValve;
    }

    public boolean isLowerLevelPureWaterTankValve() {
        return lowerLevelPureWaterTankValve;
    }

    public void setLowerLevelPureWaterTankValve(boolean lowerLevelPureWaterTankValve) {
        this.lowerLevelPureWaterTankValve = lowerLevelPureWaterTankValve;
    }
}
