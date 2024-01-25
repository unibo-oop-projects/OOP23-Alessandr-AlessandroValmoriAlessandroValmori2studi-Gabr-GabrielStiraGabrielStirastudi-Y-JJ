package it.unibo.jetpackjoyride.core.movement;

import it.unibo.jetpackjoyride.utilities.Pair;

public abstract class AbstractMovement implements Movement {

    public Pair<Double, Double> startingPosition;
    public Pair<Double, Double> currentPosition;
    public Pair<Double,Double> xyacceleration;
    public Pair<Double,Double> xyspeed;
    public Double time = 0.1;
    public Double speedModifier = 1.0;

    public AbstractMovement(Pair<Double, Double> startingPosition, Pair<Double, Double> startingSpeed, Pair<Double, Double> startingAcceleration) {
        this.startingPosition = startingPosition;
        this.currentPosition = startingPosition;
        this.xyspeed = startingSpeed;
        this.xyacceleration = startingAcceleration;
    }

    public Pair<Double, Double> getStartingPosition() {
        return this.startingPosition;
    }

    public Pair<Double, Double> getCurrentPosition() {
        return this.currentPosition;
    }

    @Override
    public void setStartingPosition(Pair<Double, Double> startPos) {
        this.startingPosition = startPos;
    }

    @Override
    public void setCurrentPosition(Pair<Double, Double> currPos) {
        this.currentPosition = currPos;
    }

    public void setAcceleration(Pair<Double, Double> newAcceleration) {
        this.xyacceleration = newAcceleration;
    }

    public Pair<Double, Double> getAcceleration() {
        return this.xyacceleration;
    }

    public void setSpeed(Pair<Double, Double> newSpeed) {
        this.xyspeed = newSpeed;
    }

    public Pair<Double, Double> getSpeed() {
        return this.xyspeed;
    }

    public abstract void update();

    
}
