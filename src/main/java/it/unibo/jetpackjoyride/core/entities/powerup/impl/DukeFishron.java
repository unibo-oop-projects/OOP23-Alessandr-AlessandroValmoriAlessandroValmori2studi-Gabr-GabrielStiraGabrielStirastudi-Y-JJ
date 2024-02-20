package it.unibo.jetpackjoyride.core.entities.powerup.impl;

import it.unibo.jetpackjoyride.core.entities.powerup.api.AbstractPowerUp;
import it.unibo.jetpackjoyride.core.hitbox.api.Hitbox;
import it.unibo.jetpackjoyride.core.movement.Movement;
import it.unibo.jetpackjoyride.utilities.Pair;

public class DukeFishron extends AbstractPowerUp{
    private final static Integer DEFAULTENRAGETIMER = 200;
    private final static Integer DEFAULTENRAGEDURATION = 100;
    private final static Double RAGESPEEDMODIFIER = 2.0;
    private final static Double ROTATEDANGLE = 20.0;

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
					    .setPosition(this.movement.getRealPosition())
					    .setRotation(this.movement.getSpeed().get2() > 0 ? new Pair<>(ROTATEDANGLE,0.0) : new Pair<>(-ROTATEDANGLE,0.0))
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

        if(this.timerForRage.equals(DEFAULTENRAGETIMER)) {
            this.performingAction = PerformingAction.DESCENDING;
            this.movement = new Movement.Builder()
					    .setAcceleration(this.movement.getAcceleration())
					    .setSpeed(this.movement.getSpeed().get1(), this.movement.getSpeed().get2()*RAGESPEEDMODIFIER)
					    .setPosition(this.movement.getRealPosition())
					    .setRotation(this.movement.getRotation())
					    .setMovementChangers(this.movement.getMovementChangers()).build();
        }

        if(this.timerForRage.equals(DEFAULTENRAGETIMER + DEFAULTENRAGEDURATION)) {
            this.performingAction = PerformingAction.ASCENDING;
            this.timerForRage = 0;
            this.movement = new Movement.Builder()
					    .setAcceleration(this.movement.getAcceleration())
					    .setSpeed(this.movement.getSpeed().get1(), this.movement.getSpeed().get2()/RAGESPEEDMODIFIER)
					    .setPosition(this.movement.getRealPosition())
					    .setRotation(this.movement.getRotation())
					    .setMovementChangers(this.movement.getMovementChangers()).build();
        }
    }
}
