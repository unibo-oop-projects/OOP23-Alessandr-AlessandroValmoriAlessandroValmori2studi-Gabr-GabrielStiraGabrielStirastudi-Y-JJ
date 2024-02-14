package it.unibo.jetpackjoyride.core.entities.powerup.impl;

import it.unibo.jetpackjoyride.core.entities.powerup.api.AbstractPowerUp;
import it.unibo.jetpackjoyride.core.hitbox.api.Hitbox;
import it.unibo.jetpackjoyride.core.movement.Movement;
import it.unibo.jetpackjoyride.utilities.GameInfo;
import it.unibo.jetpackjoyride.utilities.Pair;

public class DukeFishron extends AbstractPowerUp{
    private final static Double DEFAULTFISHRONSPEED = 10.0;

    private Boolean intervalBewteenJumps;

    public DukeFishron(final Movement movement, final Hitbox hitbox) {
        super(PowerUpType.DUKEFISHRON, movement, hitbox);
        this.intervalBewteenJumps = true;
        this.performingAction = PerformingAction.ASCENDING;
    }

    @Override
    public void update(final boolean isSpaceBarPressed) {
        final GameInfo infoResolution = GameInfo.getInstance();
        final Double screenSizeY = infoResolution.getScreenHeight();

        switch (this.performingAction) {
            case ASCENDING:
                if(isSpaceBarPressed && this.intervalBewteenJumps) {
                    this.movement.setSpeed(new Pair<>(this.movement.getSpeed().get1(), -this.movement.getSpeed().get2()));
                    this.intervalBewteenJumps = false;
                }
            
            default:
                break;
            
            
        }

        if(!this.intervalBewteenJumps) {
            if(!isSpaceBarPressed) {
                this.intervalBewteenJumps = true;
            }
        }

        Double rotationAngle = this.movement.getSpeed().get2();
        this.movement.setRotation(new Pair<>(rotationAngle, 0.0));

        this.movement.update();
    }
}
