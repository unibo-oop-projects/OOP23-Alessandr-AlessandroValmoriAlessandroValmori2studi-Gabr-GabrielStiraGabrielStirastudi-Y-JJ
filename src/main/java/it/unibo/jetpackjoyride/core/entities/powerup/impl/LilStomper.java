package it.unibo.jetpackjoyride.core.entities.powerup.impl;

import it.unibo.jetpackjoyride.core.entities.powerup.api.AbstractPowerUp;
import it.unibo.jetpackjoyride.core.hitbox.Hitbox;
import it.unibo.jetpackjoyride.core.movement.Movement;
import it.unibo.jetpackjoyride.utilities.Pair;

public class LilStomper extends AbstractPowerUp{
    private int loadJump;
    private PerformingAction performingAction;

    public enum PerformingAction {
        WALKING, JUMPING, ASCENDING, DESCENDING, LANDING
    }

    public LilStomper(Movement movement, Hitbox hitbox) {
        super(PowerUpType.LILSTOMPER, movement, hitbox);
        this.loadJump = 0;
        this.performingAction = PerformingAction.WALKING;
    }

    @Override
    public void update(boolean isSpaceBarPressed) {
        this.movement.update();

        switch (this.performingAction) {
            case WALKING:
                this.movement.setAcceleration(new Pair<>(this.movement.getAcceleration().get1(), 0.0));
                this.movement.setSpeed(new Pair<>(this.movement.getSpeed().get1(), 0.0));
                if(isSpaceBarPressed) {
                    this.performingAction = PerformingAction.JUMPING;
                }
                break;
            case JUMPING:
                if(isSpaceBarPressed) {
                    this.loadJump++;
                }
                if(this.loadJump == 10 || !isSpaceBarPressed) {
                    this.movement.setAcceleration(new Pair<>(this.movement.getAcceleration().get1(), -this.loadJump*4.0 - 130.0));
                    this.loadJump = 0;
                    this.performingAction = PerformingAction.ASCENDING;
                }
                break;
            case ASCENDING:
                if(this.movement.getSpeed().get2()>=0) {
                    this.performingAction = PerformingAction.DESCENDING;
                }
                break;
            case DESCENDING:
                if(this.movement.getSpeed().get2()>10) {
                    if(isSpaceBarPressed) {
                        this.movement.setSpeed(new Pair<>(this.movement.getSpeed().get1(), 30.0));
                    } else {
                        this.movement.setAcceleration(new Pair<>(this.movement.getAcceleration().get1(), this.movement.getAcceleration().get2()-15.0));
                    }
                }

                if(this.movement.getCurrentPosition().get2()>650) {
                    this.movement.setAcceleration(new Pair<>(this.movement.getAcceleration().get1(), 0.0));
                    this.movement.setSpeed(new Pair<>(this.movement.getSpeed().get1(), 0.0));
                    this.movement.setCurrentPosition(new Pair<>(this.movement.getCurrentPosition().get1(), 650.0));
                    this.performingAction = PerformingAction.LANDING;
                    this.loadJump = -20;
                }
                break;
            case LANDING:
                this.loadJump++;
                if(this.loadJump == 0) {
                    this.performingAction = PerformingAction.WALKING;
                }
                break;
            default:
                break;
        }
    }

    public PerformingAction getPerformingAction() {
        return this.performingAction;
    }



    
}
