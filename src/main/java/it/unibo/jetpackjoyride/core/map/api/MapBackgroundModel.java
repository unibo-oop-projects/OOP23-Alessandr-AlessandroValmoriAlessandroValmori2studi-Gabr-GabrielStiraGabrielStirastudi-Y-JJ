package it.unibo.jetpackjoyride.core.map.api;

import java.util.List;

import it.unibo.jetpackjoyride.utilities.Pair;

public interface MapBackgroundModel {
    
    void updateBackgroundModel();

    List<Double> getPosX();

    Pair<Double,Double> getSize();

    void reset();
}
