package it.unibo.jetpackjoyride.core.entities.powerup.impl;

import it.unibo.jetpackjoyride.core.entities.powerup.api.AbstractPowerUp;
import it.unibo.jetpackjoyride.core.hitbox.api.Hitbox;
import it.unibo.jetpackjoyride.core.movement.Movement;

public class ProfitBird extends AbstractPowerUp{
    
    private final static Double BASEJUMPSPEED = 8.0;
    private static final Double LANDINGHEIGHT = 630.0;
    private Boolean intervalBewteenJumps;

    public ProfitBird(final Movement movement, final Hitbox hitbox) {
        super(PowerUpType.PROFITBIRD, movement, hitbox);
        this.intervalBewteenJumps = true;
        this.performingAction = PerformingAction.WALKING;
    }

    @Override
    public void updateStatus(final boolean isSpaceBarPressed) {
        switch (this.performingAction) {
            case WALKING:
                if(isSpaceBarPressed && this.intervalBewteenJumps) {
                    this.performingAction = PerformingAction.JUMPING;
                    this.intervalBewteenJumps = false;
                }
                break;
            case JUMPING:
                this.performingAction = PerformingAction.ASCENDING;
                break;
            case ASCENDING:
                if (this.movement.getSpeed().get2() >= 0) {
                    this.performingAction = PerformingAction.DESCENDING;
                }
                if(isSpaceBarPressed && this.intervalBewteenJumps) {
                    this.performingAction = PerformingAction.JUMPING;
                    this.intervalBewteenJumps = false;
                }
                break;
            case DESCENDING:
                if(isSpaceBarPressed && this.intervalBewteenJumps) {
                    this.performingAction = PerformingAction.JUMPING;
                    this.intervalBewteenJumps = false;
                }
                if(this.movement.getPosition().get2() >= LANDINGHEIGHT) {
                    this.performingAction = PerformingAction.WALKING;
                }
                break;
            default:
                break;
        }

        if(!this.intervalBewteenJumps && !isSpaceBarPressed) {
            this.intervalBewteenJumps = true;
        }

        this.movement = new Movement.Builder()
			.setAcceleration(this.movement.getAcceleration())
			.setSpeed(this.movement.getSpeed().get1(), this.performingAction.equals(PerformingAction.JUMPING) ? -BASEJUMPSPEED : this.movement.getSpeed().get2())
			.setPosition(this.movement.getPosition())
			.setRotation(this.movement.getSpeed().get2(),0.0)
			.setMovementChangers(this.movement.getMovementChangers()).build();
    }
}
