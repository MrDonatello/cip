package com.cip.model.cip;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Cip1 extends Cip {

    @Column(name = "change_route_valve")
    private boolean changeRouteValve;

    public Cip1() {
    }

    public Cip1(Long id, String dateTime, int route, boolean mainFeedPump, boolean steamShutValve,
                int steamRegulatingValve, int outputFlowTemperatureSensor, boolean returnFlowPump,
                int inputFlowIntensitySensor, double inputFlowConductivitySensor, double inputFlowTemperatureSensor,
                boolean inputFlowLyeValve, boolean inputFlowAcidValve, boolean inputFlowRinseWaterValve,
                boolean circulationValve, boolean drainValve, boolean outputFlowPureWaterValve,
                boolean outputFlowRinseWaterValve, boolean outputFlowAcidValve, boolean outputFlowLyeValve, boolean changeRouteValve) {
        super(id, dateTime, route, mainFeedPump, steamShutValve, steamRegulatingValve, outputFlowTemperatureSensor,
                returnFlowPump, inputFlowIntensitySensor, inputFlowConductivitySensor, inputFlowTemperatureSensor,
                inputFlowLyeValve, inputFlowAcidValve, inputFlowRinseWaterValve, circulationValve, drainValve,
                outputFlowPureWaterValve, outputFlowRinseWaterValve, outputFlowAcidValve, outputFlowLyeValve);
        this.changeRouteValve = changeRouteValve;
    }

    public boolean isChangeRouteValve() {
        return changeRouteValve;
    }

    public void setChangeRouteValve(boolean changeRouteValve) {
        this.changeRouteValve = changeRouteValve;
    }

}
