package it.unibo.jetpackjoyride.core.movement;

import it.unibo.jetpackjoyride.utilities.Pair;

import java.util.*;

public interface Movement {

    enum MovementChangers {
        BOUNCING, // Once the upper or lower bound of the screen is hit, the y speed is inverted
        SLOW, // Slower initial speed set by the SLOWMODIFIER
        SPEEDY, // Faster initial speed set by the SPEEDYMODIFIER
        STATIC, // No velocity, the object is still and doesn't move
        GRAVITY, // y speed is accelerated downwards
        INVERSEGRAVITY, // y speed is accelerated upwards
        BOUNDS, // x and y will only vary between specified limits
    }

    Pair<Double, Double> getCurrentPosition();

    Pair<Double, Double> getSpeed();

    Pair<Double, Double> getAcceleration();

    Pair<Double, Double> getRotation();

    List<MovementChangers> getMovementChangers();

    void setCurrentPosition(Pair<Double, Double> currPos);

    void setAcceleration(Pair<Double, Double> newAcceleration);

    void setSpeed(Pair<Double, Double> newSpeed);

    void setRotation(Pair<Double, Double> newRotationAngle);

    void setMovementChangers(List<MovementChangers> listOfChangers);

    void update();
}
