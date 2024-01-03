package movement.custom;

import movement.impl.MovementImpl;
import utilities.Pair;

public class StandardMissileMovement extends MovementImpl {

    public StandardMissileMovement(final Pair<Double, Double> startingPosition) {
        super(startingPosition, startingPosition);
    } 
}
