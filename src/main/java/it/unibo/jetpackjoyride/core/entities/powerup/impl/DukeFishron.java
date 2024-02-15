package it.unibo.jetpackjoyride.core.entities.powerup.impl;

import it.unibo.jetpackjoyride.core.entities.powerup.api.AbstractPowerUp;
import it.unibo.jetpackjoyride.core.hitbox.api.Hitbox;
import it.unibo.jetpackjoyride.core.movement.Movement;
import it.unibo.jetpackjoyride.utilities.Pair;

public class DukeFishron extends AbstractPowerUp{
    private final static Integer DEFAULTENRAGETIMER = 200;
    private final static Integer DEFAULTENRAGEDURATION = 100;

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
                    this.movement.setSpeed(new Pair<>(this.movement.getSpeed().get1(), -this.movement.getSpeed().get2()));
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
            this.movement.setSpeed(new Pair<>(this.movement.getSpeed().get1(), this.movement.getSpeed().get2()*2.0));
        }

        if(this.timerForRage.equals(DEFAULTENRAGETIMER + DEFAULTENRAGEDURATION)) {
            this.performingAction = PerformingAction.ASCENDING;
            this.timerForRage = 0;
            this.movement.setSpeed(new Pair<>(this.movement.getSpeed().get1(), this.movement.getSpeed().get2()/2.0));
        }

        final Double rotationAngle = this.movement.getSpeed().get2();
        this.movement.setRotation(new Pair<>(rotationAngle, 0.0));

    }
}
