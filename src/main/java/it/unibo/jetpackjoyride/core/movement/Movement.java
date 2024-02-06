package it.unibo.jetpackjoyride.core.movement;

import it.unibo.jetpackjoyride.core.movement.MovementGenerator.MovementChangers;
import it.unibo.jetpackjoyride.utilities.Pair;

import java.util.*;

public interface Movement {

    Pair<Double, Double> getCurrentPosition();

    Pair<Double, Double> getSpeed();

    Pair<Double, Double> getAcceleration();

    Pair<Double, Double> getRotation();

    List<MovementChangers> getMovementChangers();

    void setMovementChangers(List<MovementChangers> listOfChangers);

    void setCurrentPosition(Pair<Double, Double> currPos);

    void setAcceleration(Pair<Double, Double> newAcceleration);

    void setSpeed(Pair<Double, Double> newSpeed);

    void setRotation(Pair<Double, Double> newRotationAngle);

    void update();
}
