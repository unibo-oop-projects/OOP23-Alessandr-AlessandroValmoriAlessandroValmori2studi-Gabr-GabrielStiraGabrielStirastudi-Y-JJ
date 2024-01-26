package it.unibo.jetpackjoyride.core.movement;

import it.unibo.jetpackjoyride.utilities.Pair;

public interface Movement {

    Pair<Double, Double> getStartingPosition();

    Pair<Double, Double> getCurrentPosition();

    Pair<Double, Double> getSpeed();

    Pair<Double, Double> getAcceleration();

    void setStartingPosition(Pair<Double, Double> startPos);

    void setCurrentPosition(Pair<Double, Double> currPos);

    void setAcceleration(Pair<Double, Double> newAcceleration);

    void setSpeed(Pair<Double, Double> newSpeed);

    void update();
}
