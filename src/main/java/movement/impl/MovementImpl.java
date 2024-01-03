package movement.impl;

import movement.api.Movement;
import utilities.Pair;

public abstract class MovementImpl implements Movement {

    public Pair<Double, Double> startingPosition;
    public Pair<Double, Double> currentPosition;

    protected MovementImpl(final Pair<Double, Double> startingPosition, final Pair<Double, Double> currentPosition) {
        this.startingPosition = startingPosition;
        this.currentPosition = currentPosition;
    }


    public Pair<Double, Double> getStartingPosition() {
        return this.startingPosition;
    }

    public Pair<Double, Double> getCurrentPosition() {
        return this.currentPosition;
    }
}