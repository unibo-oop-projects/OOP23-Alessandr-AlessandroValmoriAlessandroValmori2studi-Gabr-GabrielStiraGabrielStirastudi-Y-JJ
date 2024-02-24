package it.unibo.jetpackjoyride.core.entities.obstacle.impl;

import it.unibo.jetpackjoyride.core.entities.obstacle.api.AbstractObstacle;
import it.unibo.jetpackjoyride.core.hitbox.api.Hitbox;
import it.unibo.jetpackjoyride.core.movement.Movement;
import it.unibo.jetpackjoyride.utilities.GameInfo;

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
        if(this.lifetime.equals(1)) {
            final Double startingXSpeed = Double.valueOf(GameInfo.MOVE_SPEED.get());

            this.movement = new Movement.Builder()
                    .setPosition(this.movement.getPosition())
                    .setSpeed(-startingXSpeed, this.movement.getSpeed().get2())
                    .setAcceleration(this.movement.getAcceleration())
                    .setRotation(this.movement.getRotation())
                    .setMovementChangers(this.movement.getMovementChangers())
                    .build();
        }
        if (this.movement.getPosition().get1() < OUTOFBOUNDSSX) {
            this.entityStatus = EntityStatus.INACTIVE;
        }
    }
}