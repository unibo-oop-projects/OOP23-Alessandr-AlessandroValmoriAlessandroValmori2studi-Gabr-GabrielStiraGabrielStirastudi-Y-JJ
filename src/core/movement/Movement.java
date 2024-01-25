package core.movement;

import core.utilities.Pair;

public interface Movement {

    Pair<Double, Double> getStartingPosition();

    Pair<Double, Double> getCurrentPosition();

    void setStartingPosition(Pair<Double, Double> startPos);

    void setCurrentPosition(Pair<Double, Double> currPos);

    void setAcceleration(Pair<Double, Double> newAcceleration);

    Pair<Double, Double> getAcceleration();

    void setSpeed(Pair<Double, Double> newSpeed);

    Pair<Double, Double> getSpeed();

    void update();
}
