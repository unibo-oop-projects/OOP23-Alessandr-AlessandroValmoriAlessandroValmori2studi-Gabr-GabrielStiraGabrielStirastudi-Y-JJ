package it.unibo.jetpackjoyride.core.entities.coin.api;

import java.util.List;

import it.unibo.jetpackjoyride.utilities.Pair;

/**
 * The CoinShapeFactory interface defines a method for generating regular shapes for coins.
 * @author yukai.zhou@studio.unibo.it
 */
public interface CoinShapeFactory {

/**
 * Generates a list of regular shapes for coins.
 *
 * @return a list of pairs containing the positions of regular coin shapes
 */
    List<Pair<Double, Double>> regularShapes(); 
}
