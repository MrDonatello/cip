package com.cip.model.cip;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Cip3 extends Cip {

    @Column(name = "circulation_solution_valve")
    private boolean circulationSolutionValve;

    public Cip3() {
    }

    public Cip3(Long id, String dateTime, int route, boolean mainFeedPump, boolean steamShutValve,
                int steamRegulatingValve, int outputFlowTemperatureSensor, boolean returnFlowPump,
                int inputFlowIntensitySensor, double inputFlowConductivitySensor, double inputFlowTemperatureSensor,
                boolean inputFlowLyeValve, boolean inputFlowAcidValve, boolean inputFlowRinseWaterValve,
                boolean circulationValve, boolean drainValve, boolean outputFlowPureWaterValve,
                boolean outputFlowRinseWaterValve, boolean outputFlowAcidValve, boolean outputFlowLyeValve,
                boolean circulationSolutionValve) {
        super(id, dateTime, route, mainFeedPump, steamShutValve, steamRegulatingValve, outputFlowTemperatureSensor,
                returnFlowPump, inputFlowIntensitySensor, inputFlowConductivitySensor, inputFlowTemperatureSensor,
                inputFlowLyeValve, inputFlowAcidValve, inputFlowRinseWaterValve, circulationValve, drainValve,
                outputFlowPureWaterValve, outputFlowRinseWaterValve, outputFlowAcidValve, outputFlowLyeValve);
        this.circulationSolutionValve = circulationSolutionValve;
    }

    public boolean isCirculationSolutionValve() {
        return circulationSolutionValve;
    }

    public void setCirculationSolutionValve(boolean circulationSolutionValve) {
        this.circulationSolutionValve = circulationSolutionValve;
    }


}
