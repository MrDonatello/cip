package com.cip.model.cip;

import javax.persistence.Entity;
import java.util.Date;

@Entity
public class Cip2 extends Cip {

    public Cip2() {
    }

    public Cip2(Long id, String dateTime, int route, boolean mainFeedPump, boolean steamShutValve,
                int steamRegulatingValve, int outputFlowTemperatureSensor, boolean returnFlowPump,
                int inputFlowIntensitySensor, double inputFlowConductivitySensor, double inputFlowTemperatureSensor,
                boolean inputFlowLyeValve, boolean inputFlowAcidValve, boolean inputFlowRinseWaterValve,
                boolean circulationValve, boolean drainValve, boolean outputFlowPureWaterValve,
                boolean outputFlowRinseWaterValve, boolean outputFlowAcidValve, boolean outputFlowLyeValve) {
        super(id, dateTime, route, mainFeedPump, steamShutValve, steamRegulatingValve, outputFlowTemperatureSensor,
                returnFlowPump, inputFlowIntensitySensor, inputFlowConductivitySensor, inputFlowTemperatureSensor,
                inputFlowLyeValve, inputFlowAcidValve, inputFlowRinseWaterValve, circulationValve, drainValve,
                outputFlowPureWaterValve, outputFlowRinseWaterValve, outputFlowAcidValve, outputFlowLyeValve);
    }
}
