package it.unibo.jetpackjoyride.core.entities.obstacle.impl;

import it.unibo.jetpackjoyride.core.entities.obstacle.api.AbstractObstacle;
import it.unibo.jetpackjoyride.core.hitbox.api.Hitbox;
import it.unibo.jetpackjoyride.core.movement.Movement;

public final class Zapper extends AbstractObstacle {
    private static final Double OUTOFBOUNDSSX = -100.0;
    private static final Double SPAWNINGXCOORDINATE = 1350.0;

    public Zapper(final Movement newMovement, final Hitbox hitbox) {
        super(ObstacleType.ZAPPER, newMovement, hitbox);
        this.entityStatus = EntityStatus.ACTIVE;

        this.movement = new Movement.Builder()
        .setPosition(SPAWNINGXCOORDINATE, this.movement.getPosition().get2())
        .setSpeed(this.movement.getSpeed())
        .setAcceleration(this.movement.getAcceleration())
        .setRotation(this.movement.getRotation())
        .setMovementChangers(this.movement.getMovementChangers())
        .build();
    }

    @Override
    public void updateStatus(final boolean isSpaceBarPressed) {
        if (this.movement.getPosition().get1() < OUTOFBOUNDSSX) {
            this.entityStatus = EntityStatus.INACTIVE;
        }
    }
}