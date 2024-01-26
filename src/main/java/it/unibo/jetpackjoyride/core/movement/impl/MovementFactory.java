package it.unibo.jetpackjoyride.core.movement.impl;

import it.unibo.jetpackjoyride.core.movement.Movement;
import it.unibo.jetpackjoyride.utilities.Pair;

public interface MovementFactory {
    Movement defaultMovement(Pair<Double, Double> position, Pair<Double, Double> speed, Pair<Double, Double> acceleration);

    Movement homingMovement(Pair<Double, Double> position, Pair<Double, Double> speed, Pair<Double, Double> acceleration);

    Movement speedyMovement(Pair<Double, Double> position, Pair<Double, Double> speed, Pair<Double, Double> acceleration);

    Movement slowMovement(Pair<Double, Double> position, Pair<Double, Double> speed, Pair<Double, Double> acceleration);

    Movement bouncingMovement(Pair<Double, Double> position, Pair<Double, Double> speed, Pair<Double, Double> acceleration);

    Movement combineMore(Movement firstMovement, Movement secondMovement);
}
