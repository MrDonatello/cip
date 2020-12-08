package com.cip.model.cip;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Cip3 extends Cip {

    @Column(name = "circulation_solution_valve")
    private boolean circulationSolutionValve;

    public Cip3() {
    }

    public boolean isCirculationSolutionValve() {
        return circulationSolutionValve;
    }

    public void setCirculationSolutionValve(boolean circulationSolutionValve) {
        this.circulationSolutionValve = circulationSolutionValve;
    }
}
