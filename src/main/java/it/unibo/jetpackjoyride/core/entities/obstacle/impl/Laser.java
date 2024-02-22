package it.unibo.jetpackjoyride.core.entities.obstacle.impl;

import it.unibo.jetpackjoyride.core.entities.obstacle.api.AbstractObstacle;
import it.unibo.jetpackjoyride.core.hitbox.api.Hitbox;
import it.unibo.jetpackjoyride.core.movement.Movement;

public final class Laser extends AbstractObstacle {
    private final static Integer TIMEFORCHARGING = 100;
    private final static Integer LASERDURATION = 150;
    private final static Integer TIMEFORDECHARGING = 80;
    private final static Double SPAWNINGXCOORDINATE = 640.0;

    public Laser(final Movement newMovement, final Hitbox hitbox) {
        super(ObstacleType.LASER, newMovement, hitbox);
        this.entityStatus = EntityStatus.CHARGING;

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
        if (this.entityStatus.equals(EntityStatus.DEACTIVATED) && this.lifetime < TIMEFORCHARGING+LASERDURATION) {
            this.lifetime = TIMEFORCHARGING+LASERDURATION;
        }

        if(this.lifetime.equals(TIMEFORCHARGING)) {
            this.entityStatus = EntityStatus.ACTIVE;
        }
        if(this.lifetime.equals(TIMEFORCHARGING+LASERDURATION)) {
            this.entityStatus = EntityStatus.DEACTIVATED;
        }
        if(this.lifetime.equals(TIMEFORCHARGING+LASERDURATION+TIMEFORDECHARGING)) {
            this.entityStatus = EntityStatus.INACTIVE;
        }
    }
}