package it.unibo.jetpackjoyride.core.entities.powerup.impl;

import java.util.ArrayList;
import java.util.List;

import it.unibo.jetpackjoyride.core.entities.powerup.api.AbstractPowerUp;
import it.unibo.jetpackjoyride.core.hitbox.api.Hitbox;
import it.unibo.jetpackjoyride.core.movement.Movement;
import it.unibo.jetpackjoyride.core.movement.MovementGenerator.MovementChangers;
import it.unibo.jetpackjoyride.utilities.Pair;

public class MrCuddles extends AbstractPowerUp{
    private List<Boolean> lastFrames;

    public MrCuddles(Movement movement, Hitbox hitbox, Integer indexOfBody) {
        super(PowerUpType.MRCUDDLES, movement, hitbox);
        this.performingAction = PerformingAction.ASCENDING;
        this.lastFrames = new ArrayList<>(indexOfBody+1);

        for(int i=0; i<indexOfBody+1; i++) {
            this.lastFrames.add(false);
        }
        if(indexOfBody==0) {
            this.powerUpStatus = PowerUpStatus.ACTIVE;
        } else {
            this.powerUpStatus = PowerUpStatus.DEACTIVATED;
        }
    }

    @Override
    public void update(boolean isSpaceBarPressed) {
        this.lastFrames.remove(0);
        this.lastFrames.add(isSpaceBarPressed);

        Double rotationAngle = this.movement.getSpeed().get2()/5;
        this.movement.setRotation(new Pair<>(rotationAngle,0.0));
        this.movement.update();

        if(lastFrames.get(0)) {
            this.performingAction = PerformingAction.DESCENDING;
        } else {
           this.performingAction = PerformingAction.ASCENDING;
        }
        

        switch (this.performingAction) {
            case ASCENDING:
                this.movement.setMovementChangers(List.of(MovementChangers.INITIALLYSTILL, MovementChangers.INVERSEGRAVITY, MovementChangers.BOUNDS));             
                break;
            case DESCENDING:
                this.movement.setMovementChangers(List.of(MovementChangers.INITIALLYSTILL, MovementChangers.GRAVITY, MovementChangers.BOUNDS));
                break;
            default:
                break;
        }
    }   
}
