package it.unibo.jetpackjoyride.core.entities.powerup.impl;

import it.unibo.jetpackjoyride.core.entities.powerup.api.AbstractPowerUp;
import it.unibo.jetpackjoyride.core.hitbox.api.Hitbox;
import it.unibo.jetpackjoyride.core.movement.Movement;

public class DukeFishron extends AbstractPowerUp{
    private final static Integer DEFAULTENRAGETIMER = 100;
    private final static Integer DEFAULTENRAGEDURATION = 100;
    private final static Double RAGESPEEDMODIFIER = 2.0;

    private Boolean intervalBewteenJumps;
    private Integer timerForRage;

    public DukeFishron(final Movement movement, final Hitbox hitbox) {
        super(PowerUpType.DUKEFISHRON, movement, hitbox);
        this.intervalBewteenJumps = true;
        this.timerForRage = 0;
        this.performingAction = PerformingAction.ASCENDING;
    }

    @Override
    public void updateStatus(final boolean isSpaceBarPressed) {
        switch (this.performingAction) {
            case ASCENDING:
            case DESCENDING:
                if(isSpaceBarPressed && this.intervalBewteenJumps) {
                    this.movement = new Movement.Builder()
					    .setAcceleration(this.movement.getAcceleration())
					    .setSpeed(this.movement.getSpeed().get1(), -this.movement.getSpeed().get2())
					    .setPosition(this.movement.getPosition())
					    .setRotation((this.movement.getSpeed().get2() > 0 ? -1.0 : 1.0 )*(Math.abs(this.movement.getRotation().get1())), this.movement.getRotation().get2())
					    .setMovementChangers(this.movement.getMovementChangers()).build();
                    this.intervalBewteenJumps = false;
                }
                break;
            default:
                break;
        }

        if(!this.intervalBewteenJumps && !isSpaceBarPressed ) {
            this.intervalBewteenJumps = true;
        }

        this.timerForRage++;

        if(this.timerForRage.equals(DEFAULTENRAGETIMER) || this.timerForRage.equals(DEFAULTENRAGETIMER + DEFAULTENRAGEDURATION)) {
            this.performingAction = this.timerForRage.equals(DEFAULTENRAGETIMER) ? PerformingAction.DESCENDING : PerformingAction.ASCENDING;
            if(this.timerForRage.equals(DEFAULTENRAGETIMER + DEFAULTENRAGEDURATION)) {
                this.timerForRage = 0;
            }
            this.movement = new Movement.Builder()
					    .setAcceleration(this.movement.getAcceleration())
					    .setSpeed(this.movement.getSpeed().get1(), this.movement.getSpeed().get2()*(this.timerForRage.equals(DEFAULTENRAGETIMER) ? RAGESPEEDMODIFIER :  1/RAGESPEEDMODIFIER))
					    .setPosition(this.movement.getPosition())
					    .setRotation(this.movement.getRotation())
					    .setMovementChangers(this.movement.getMovementChangers()).build();
        }
    }
}
