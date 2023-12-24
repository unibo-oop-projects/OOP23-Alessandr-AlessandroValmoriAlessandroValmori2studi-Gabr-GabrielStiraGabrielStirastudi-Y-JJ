package core.movement.custom;

import core.movement.impl.MovementImpl;
import core.utilities.Pair;

public class StandardMissileMovement extends MovementImpl {

    public StandardMissileMovement(final Pair<Double, Double> startingPosition) {
        super(startingPosition, startingPosition);
    } 
}
