package it.unibo.jetpackjoyride.core.movement;

import java.util.ArrayList;
import java.util.List;

import it.unibo.jetpackjoyride.core.movement.MovementGenerator.MovementChangers;
import it.unibo.jetpackjoyride.utilities.Pair;

public abstract class AbstractMovement implements Movement {

    private Pair<Double, Double> currentPosition;
    private Pair<Double,Double> xyacceleration;
    private Pair<Double,Double> xyspeed;
    private Pair<Double,Double> rotationInfo;

    private List<MovementChangers> listOfChangers = new ArrayList<>();

    public final static Pair<Double,Double> playerPos = new Pair<>(100.0, 400.0);
    public final static Double TIME = 0.1;

    public AbstractMovement(Pair<Double, Double> startingPosition, Pair<Double, Double> startingSpeed, Pair<Double, Double> startingAcceleration, Pair<Double, Double> rotationInfo, List<MovementChangers> listOfChangers) {
        this.currentPosition = startingPosition;
        this.xyspeed = startingSpeed;
        this.xyacceleration = startingAcceleration;
        this.listOfChangers = listOfChangers;
        this.rotationInfo = rotationInfo;
    }

    @Override
    public Pair<Double, Double> getCurrentPosition() {
        return this.currentPosition;
    }

    @Override
    public void setCurrentPosition(Pair<Double, Double> currPos) {
        this.currentPosition = currPos;
    }

    @Override
    public void setAcceleration(Pair<Double, Double> newAcceleration) {
        this.xyacceleration = newAcceleration;
    }

    @Override
    public Pair<Double, Double> getAcceleration() {
        return this.xyacceleration;
    }

    @Override
    public void setSpeed(Pair<Double, Double> newSpeed) {
        this.xyspeed = newSpeed;
    }

    @Override
    public Pair<Double, Double> getSpeed() {
        return this.xyspeed;
    }

    public List<MovementChangers> getMovementChangers() {
        return this.listOfChangers;
    }

    public void setMovementChangers(List<MovementChangers> listOfChangers) {
        this.listOfChangers = listOfChangers;
    }

    public void setRotation(Pair<Double, Double> newRotationInfo) {
        this.rotationInfo = newRotationInfo;
    }

    public Pair<Double, Double> getRotation() {
        return this.rotationInfo;
    }

    protected abstract void applyModifiers();

    @Override
    public abstract void update();
}
