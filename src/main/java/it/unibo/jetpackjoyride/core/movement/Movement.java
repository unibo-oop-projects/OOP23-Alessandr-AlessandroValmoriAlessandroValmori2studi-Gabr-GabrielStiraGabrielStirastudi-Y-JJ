package it.unibo.jetpackjoyride.core.movement;

import it.unibo.jetpackjoyride.utilities.Pair;

public interface Movement {

    Pair<Double, Double> getCurrentPosition();

    Pair<Double, Double> getSpeed();

    Pair<Double, Double> getAcceleration();

    Double getRotation();

    void setCurrentPosition(Pair<Double, Double> currPos);

    void setAcceleration(Pair<Double, Double> newAcceleration);

    void setSpeed(Pair<Double, Double> newSpeed);

    void setRotation(Double newRotationAngle);

    void update();
}
