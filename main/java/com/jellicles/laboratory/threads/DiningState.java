package com.jellicles.laboratory.threads;

/**
 * Philosopher state transitions.
 * 
 * @author ron
 * 
 */
public enum DiningState {

    THINKING("thinking"), HUNGRY("hungry"), EATING("eating");

    final private String state;

    private DiningState(String state) {
        this.state = state;
    }

    public String toString() {
        return state;
    }

}
