package it.unibo.jetpackjoyride.core.entities.powerup.impl;

import it.unibo.jetpackjoyride.core.entities.powerup.api.AbstractPowerUp;
import it.unibo.jetpackjoyride.core.hitbox.api.Hitbox;
import it.unibo.jetpackjoyride.core.movement.Movement;
import it.unibo.jetpackjoyride.utilities.GameInfo;
import it.unibo.jetpackjoyride.utilities.Pair;

public class ProfitBird extends AbstractPowerUp{
    
    private final static Double BASEJUMPSPEED = 5.0;
    private Boolean intervalBewteenJumps;

    public ProfitBird(final Movement movement, final Hitbox hitbox) {
        super(PowerUpType.PROFITBIRD, movement, hitbox);
        this.intervalBewteenJumps = true;
        this.performingAction = PerformingAction.WALKING;
    }

    @Override
    public void updateStatus(final boolean isSpaceBarPressed) {
        final GameInfo infoResolution = GameInfo.getInstance();
        final Double screenSizeY = infoResolution.getScreenHeight();

        switch (this.performingAction) {
            case WALKING:
                if(isSpaceBarPressed && this.intervalBewteenJumps) {
                    this.performingAction = PerformingAction.JUMPING;
                    this.intervalBewteenJumps = false;
                }
                break;
            case JUMPING:
                this.movement.setSpeed(new Pair<>(this.movement.getSpeed().get1(), -BASEJUMPSPEED));
                this.performingAction = PerformingAction.ASCENDING;
                break;
            case ASCENDING:
                if (this.movement.getSpeed().get2() >= 0) {
                    this.performingAction = PerformingAction.DESCENDING;
                }
                if(isSpaceBarPressed && this.intervalBewteenJumps) {
                    this.performingAction = PerformingAction.JUMPING;
                    this.movement.setSpeed(new Pair<>(this.movement.getSpeed().get1(), -BASEJUMPSPEED));
                    this.intervalBewteenJumps = false;
                }
                break;
            case DESCENDING:
                if(isSpaceBarPressed && this.intervalBewteenJumps) {
                    this.performingAction = PerformingAction.JUMPING;
                    this.movement.setSpeed(new Pair<>(this.movement.getSpeed().get1(), -BASEJUMPSPEED));
                    this.intervalBewteenJumps = false;
                }
                if(this.movement.getCurrentPosition().get2() >= screenSizeY - screenSizeY/8) {
                    this.performingAction = PerformingAction.WALKING;
                }
                break;
            default:
                break;
            
            
        }

        if(!this.intervalBewteenJumps && !isSpaceBarPressed) {
            this.intervalBewteenJumps = true;
        }

        final Double rotationAngle = this.movement.getSpeed().get2();
        this.movement.setRotation(new Pair<>(rotationAngle, 0.0));

        this.movement.update();
    }
}
