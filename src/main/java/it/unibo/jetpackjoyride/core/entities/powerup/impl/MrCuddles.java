package it.unibo.jetpackjoyride.core.entities.powerup.impl;

import java.util.ArrayList;
import java.util.List;

import it.unibo.jetpackjoyride.core.entities.powerup.api.AbstractPowerUp;
import it.unibo.jetpackjoyride.core.hitbox.api.Hitbox;
import it.unibo.jetpackjoyride.core.movement.Movement;
import it.unibo.jetpackjoyride.utilities.MovementChangers;

public final class MrCuddles extends AbstractPowerUp {
    private final List<Boolean> lastFrames;

    public MrCuddles(final Movement movement, final Hitbox hitbox, final Integer indexOfBody) {
        super(PowerUpType.MRCUDDLES, movement, hitbox);
        this.performingAction = PerformingAction.ASCENDING;
        this.lastFrames = new ArrayList<>(indexOfBody + 1);

        for (int i = 0; i < indexOfBody + 1; i++) {
            this.lastFrames.add(false);
        }
        
        if (indexOfBody == 0) {
            this.entityStatus = EntityStatus.ACTIVE;
        } else {
            this.entityStatus = EntityStatus.DEACTIVATED;
        }
    }

    @Override
    public void updateStatus(final boolean isSpaceBarPressed) {
        this.lastFrames.remove(0);
        this.lastFrames.add(isSpaceBarPressed);


        if (lastFrames.get(0)) {
            this.performingAction = PerformingAction.DESCENDING;
        } else {
            this.performingAction = PerformingAction.ASCENDING;
        }

        this.movement = new Movement.Builder()
                .setPosition(this.movement.getRealPosition())
                .setSpeed(this.movement.getSpeed())
                .setAcceleration(this.movement.getAcceleration())
                .setRotation(this.movement.getSpeed().get2(), 0.0)
                .setMovementChangers(List.of(MovementChangers.BOUNDS,
                     this.performingAction.equals(PerformingAction.ASCENDING) ? MovementChangers.INVERSEGRAVITY : MovementChangers.GRAVITY))
                .build();
    }
}
