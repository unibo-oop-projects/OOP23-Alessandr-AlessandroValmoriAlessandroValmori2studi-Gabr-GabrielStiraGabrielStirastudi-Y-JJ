package it.unibo.jetpackjoyride.core.map.api;


import it.unibo.jetpackjoyride.utilities.Pair;

public interface MapBackgroundModel {
    
    void updateBackgroundModel();

    Pair<Double,Double> getPosX();

    Pair<Double,Double> getSize();

    void reset();
}
