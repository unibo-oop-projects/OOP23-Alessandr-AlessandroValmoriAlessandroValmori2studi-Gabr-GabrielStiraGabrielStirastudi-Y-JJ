package it.unibo.jetpackjoyride.core.entities.powerup.impl;

import it.unibo.jetpackjoyride.core.entities.powerup.api.AbstractPowerUp;
import it.unibo.jetpackjoyride.core.hitbox.api.Hitbox;
import it.unibo.jetpackjoyride.core.movement.Movement;
import it.unibo.jetpackjoyride.utilities.Pair;

public class LilStomper extends AbstractPowerUp{
    private int loadJump;

    public LilStomper(Movement movement, Hitbox hitbox) {
        super(PowerUpType.LILSTOMPER, movement, hitbox);
        this.loadJump = 0;
        this.performingAction = PerformingAction.WALKING;
    }

    @Override
    public void update(boolean isSpaceBarPressed) {
        this.movement.update();
        System.out.println("Later : Speed " + this.movement.getSpeed() + " pos " + this.movement.getCurrentPosition() + " acc " + this.movement.getAcceleration());
        switch (this.performingAction) {
            case WALKING:
                if(isSpaceBarPressed) {
                    this.performingAction = PerformingAction.JUMPING;
                }
                break;
            case JUMPING:
                if(isSpaceBarPressed) {
                    this.loadJump++;
                }
                if(this.loadJump == 15 || !isSpaceBarPressed) {
                    this.movement.setSpeed(new Pair<>(this.movement.getSpeed().get1(), -this.loadJump*6 - 70.0));
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
                if(isSpaceBarPressed) {
                    if(this.movement.getSpeed().get2()>30.0) {
                        this.movement.setSpeed(new Pair<>(this.movement.getSpeed().get1(), 30.0));
                    }
                }

                if(this.movement.getCurrentPosition().get2()>600) {
                    this.performingAction = PerformingAction.LANDING;
                    this.loadJump = -40;
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
}