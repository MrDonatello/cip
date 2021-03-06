package com.cip.model.cip;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.Objects;

@MappedSuperclass
public abstract class Cip {

    @Id
    private Long id;
    @Column(name = "date_time")
    private String dateTime;
    private int route;
    @Column(name = "main_feed_pump")
    private boolean mainFeedPump;
    @Column(name = "steam_shut_valve")
    private boolean steamShutValve;
    @Column(name = " steam_regulating_valve")
    private int steamRegulatingValve;
    @Column(name = "output_flow_temperature_sensor")
    private int outputFlowTemperatureSensor;
    @Column(name = "return_flow_pump")
    private boolean returnFlowPump;
    @Column(name = "input_flow_intensity_sensor")
    private int inputFlowIntensitySensor;
    @Column(name = "input_flow_conductivity_sensor")
    private double inputFlowConductivitySensor;
    @Column(name = "input_flow_temperature_sensor")
    private double inputFlowTemperatureSensor;
    @Column(name = "input_flow_lye_valve")
    private boolean inputFlowLyeValve;
    @Column(name = "input_flow_acid_valve")
    private boolean inputFlowAcidValve;
    @Column(name = "input_flow_rinse_water_valve")
    private boolean inputFlowRinseWaterValve;
    @Column(name = "circulation_valve")
    private boolean circulationValve;
    @Column(name = "drain_valve")
    private boolean drainValve;
    @Column(name = "output_flow_pure_water_valve")
    private boolean outputFlowPureWaterValve;
    @Column(name = "output_flow_rinse_water_valve")
    private boolean outputFlowRinseWaterValve;
    @Column(name = "output_flow_acid_valve")
    private boolean outputFlowAcidValve;
    @Column(name = "output_flow_lye_valve")
    private boolean outputFlowLyeValve;

    Cip() {
    }

    public Cip(Long id, String dateTime, int route, boolean mainFeedPump, boolean steamShutValve,
               int steamRegulatingValve, int outputFlowTemperatureSensor, boolean returnFlowPump,
               int inputFlowIntensitySensor, double inputFlowConductivitySensor, double inputFlowTemperatureSensor,
               boolean inputFlowLyeValve, boolean inputFlowAcidValve, boolean inputFlowRinseWaterValve,
               boolean circulationValve, boolean drainValve, boolean outputFlowPureWaterValve,
               boolean outputFlowRinseWaterValve, boolean outputFlowAcidValve, boolean outputFlowLyeValve) {
        this.id = id;
        this.dateTime = dateTime;
        this.route = route;
        this.mainFeedPump = mainFeedPump;
        this.steamShutValve = steamShutValve;
        this.steamRegulatingValve = steamRegulatingValve;
        this.outputFlowTemperatureSensor = outputFlowTemperatureSensor;
        this.returnFlowPump = returnFlowPump;
        this.inputFlowIntensitySensor = inputFlowIntensitySensor;
        this.inputFlowConductivitySensor = inputFlowConductivitySensor;
        this.inputFlowTemperatureSensor = inputFlowTemperatureSensor;
        this.inputFlowLyeValve = inputFlowLyeValve;
        this.inputFlowAcidValve = inputFlowAcidValve;
        this.inputFlowRinseWaterValve = inputFlowRinseWaterValve;
        this.circulationValve = circulationValve;
        this.drainValve = drainValve;
        this.outputFlowPureWaterValve = outputFlowPureWaterValve;
        this.outputFlowRinseWaterValve = outputFlowRinseWaterValve;
        this.outputFlowAcidValve = outputFlowAcidValve;
        this.outputFlowLyeValve = outputFlowLyeValve;
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

    public int getRoute() {
        return route;
    }

    public void setRoute(int route) {
        this.route = route;
    }

    public boolean isMainFeedPump() {
        return mainFeedPump;
    }

    public void setMainFeedPump(boolean mainFeedPump) {
        this.mainFeedPump = mainFeedPump;
    }

    public boolean isSteamShutValve() {
        return steamShutValve;
    }

    public void setSteamShutValve(boolean steamShutValve) {
        this.steamShutValve = steamShutValve;
    }

    public int getSteamRegulatingValve() {
        return steamRegulatingValve;
    }

    public void setSteamRegulatingValve(int steamRegulatingValve) {
        this.steamRegulatingValve = steamRegulatingValve;
    }

    public int getOutputFlowTemperatureSensor() {
        return outputFlowTemperatureSensor;
    }

    public void setOutputFlowTemperatureSensor(int outputFlowTemperatureSensor) {
        this.outputFlowTemperatureSensor = outputFlowTemperatureSensor;
    }

    public boolean isReturnFlowPump() {
        return returnFlowPump;
    }

    public void setReturnFlowPump(boolean returnFlowPump) {
        this.returnFlowPump = returnFlowPump;
    }

    public int getInputFlowIntensitySensor() {
        return inputFlowIntensitySensor;
    }

    public void setInputFlowIntensitySensor(int inputFlowIntensitySensor) {
        this.inputFlowIntensitySensor = inputFlowIntensitySensor;
    }

    public double getInputFlowConductivitySensor() {
        return inputFlowConductivitySensor;
    }

    public void setInputFlowConductivitySensor(double inputFlowConductivitySensor) {
        this.inputFlowConductivitySensor = inputFlowConductivitySensor;
    }

    public double getInputFlowTemperatureSensor() {
        return inputFlowTemperatureSensor;
    }

    public void setInputFlowTemperatureSensor(double inputFlowTemperatureSensor) {
        this.inputFlowTemperatureSensor = inputFlowTemperatureSensor;
    }

    public boolean isInputFlowLyeValve() {
        return inputFlowLyeValve;
    }

    public void setInputFlowLyeValve(boolean inputFlowLyeValve) {
        this.inputFlowLyeValve = inputFlowLyeValve;
    }

    public boolean isInputFlowAcidValve() {
        return inputFlowAcidValve;
    }

    public void setInputFlowAcidValve(boolean inputFlowAcidValve) {
        this.inputFlowAcidValve = inputFlowAcidValve;
    }

    public boolean isInputFlowRinseWaterValve() {
        return inputFlowRinseWaterValve;
    }

    public void setInputFlowRinseWaterValve(boolean inputFlowRinseWaterValve) {
        this.inputFlowRinseWaterValve = inputFlowRinseWaterValve;
    }

    public boolean isCirculationValve() {
        return circulationValve;
    }

    public void setCirculationValve(boolean circulationValve) {
        this.circulationValve = circulationValve;
    }

    public boolean isDrainValve() {
        return drainValve;
    }

    public void setDrainValve(boolean drainValve) {
        this.drainValve = drainValve;
    }

    public boolean isOutputFlowPureWaterValve() {
        return outputFlowPureWaterValve;
    }

    public void setOutputFlowPureWaterValve(boolean outputFlowPureWaterValve) {
        this.outputFlowPureWaterValve = outputFlowPureWaterValve;
    }

    public boolean isOutputFlowRinseWaterValve() {
        return outputFlowRinseWaterValve;
    }

    public void setOutputFlowRinseWaterValve(boolean outputFlowRinseWaterValve) {
        this.outputFlowRinseWaterValve = outputFlowRinseWaterValve;
    }

    public boolean isOutputFlowAcidValve() {
        return outputFlowAcidValve;
    }

    public void setOutputFlowAcidValve(boolean outputFlowAcidValve) {
        this.outputFlowAcidValve = outputFlowAcidValve;
    }

    public boolean isOutputFlowLyeValve() {
        return outputFlowLyeValve;
    }

    public void setOutputFlowLyeValve(boolean outputFlowLyeValve) {
        this.outputFlowLyeValve = outputFlowLyeValve;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cip)) return false;
        Cip cip = (Cip) o;
        return route == cip.route &&
                mainFeedPump == cip.mainFeedPump &&
                steamShutValve == cip.steamShutValve &&
                steamRegulatingValve == cip.steamRegulatingValve &&
                outputFlowTemperatureSensor == cip.outputFlowTemperatureSensor &&
                returnFlowPump == cip.returnFlowPump &&
                inputFlowIntensitySensor == cip.inputFlowIntensitySensor &&
                Double.compare(cip.inputFlowConductivitySensor, inputFlowConductivitySensor) == 0 &&
                Double.compare(cip.inputFlowTemperatureSensor, inputFlowTemperatureSensor) == 0 &&
                inputFlowLyeValve == cip.inputFlowLyeValve &&
                inputFlowAcidValve == cip.inputFlowAcidValve &&
                inputFlowRinseWaterValve == cip.inputFlowRinseWaterValve &&
                circulationValve == cip.circulationValve &&
                drainValve == cip.drainValve &&
                outputFlowPureWaterValve == cip.outputFlowPureWaterValve &&
                outputFlowRinseWaterValve == cip.outputFlowRinseWaterValve &&
                outputFlowAcidValve == cip.outputFlowAcidValve &&
                outputFlowLyeValve == cip.outputFlowLyeValve;
    }

    @Override
    public int hashCode() {
        return Objects.hash(route, mainFeedPump, steamShutValve, steamRegulatingValve, outputFlowTemperatureSensor, returnFlowPump, inputFlowIntensitySensor, inputFlowConductivitySensor, inputFlowTemperatureSensor, inputFlowLyeValve, inputFlowAcidValve, inputFlowRinseWaterValve, circulationValve, drainValve, outputFlowPureWaterValve, outputFlowRinseWaterValve, outputFlowAcidValve, outputFlowLyeValve);
    }
}
