package it.unibo.jetpackjoyride.core.entities.powerup.impl;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import it.unibo.jetpackjoyride.core.entities.powerup.api.AbstractPowerUp;
import it.unibo.jetpackjoyride.core.hitbox.api.Hitbox;
import it.unibo.jetpackjoyride.core.movement.Movement;
import it.unibo.jetpackjoyride.utilities.Pair;

public class MrCuddlesBody extends AbstractPowerUp{
    public MrCuddlesBody(Movement movement, Hitbox hitbox) {
        super(PowerUpType.MRCUDDLES, movement, hitbox);
        this.performingAction = PerformingAction.ASCENDING;
    }

    @Override
    public void update(boolean isSpaceBarPressed) {
        this.movement.update();

        if(isSpaceBarPressed) {
            this.performingAction = PerformingAction.DESCENDING;
        } else {
            this.performingAction = PerformingAction.ASCENDING;
        }

        if(this.movement.getCurrentPosition().get2()<150) {
            this.movement.setSpeed(new Pair<>(this.movement.getSpeed().get1(), 0.0));
            this.movement.setCurrentPosition(new Pair<>(this.movement.getCurrentPosition().get1(), 150.0));
        }
        if(this.movement.getCurrentPosition().get2()>650) {
            this.movement.setSpeed(new Pair<>(this.movement.getSpeed().get1(), 0.0));
            this.movement.setCurrentPosition(new Pair<>(this.movement.getCurrentPosition().get1(), 650.0));
        }
        
        switch (this.performingAction) {
            case ASCENDING:
                if(this.movement.getSpeed().get2()<-80) {
                    this.movement.setSpeed(new Pair<>(this.movement.getSpeed().get1(), 10.0));
                }
                break;
            case DESCENDING:
                this.movement.setSpeed(new Pair<>(this.movement.getSpeed().get1(), 70.0));
                break;
            default:
                break;
        }
    }   
}
