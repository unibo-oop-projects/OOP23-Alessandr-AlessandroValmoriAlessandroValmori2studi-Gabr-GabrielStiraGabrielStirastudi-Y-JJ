package it.unibo.jetpackjoyride.core.entities.coin.api;

import java.util.List;

import it.unibo.jetpackjoyride.utilities.Pair;

public interface CoinShapeFactory {

    List<Pair<Double, Double>> regularShapes();
    
}
