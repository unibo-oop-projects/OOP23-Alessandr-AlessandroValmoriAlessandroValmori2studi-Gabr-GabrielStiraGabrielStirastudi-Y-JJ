package it.unibo.jetpackjoyride.core.entities.obstacle.impl;

import it.unibo.jetpackjoyride.core.entities.obstacle.api.AbstractObstacle;
import it.unibo.jetpackjoyride.core.hitbox.api.Hitbox;
import it.unibo.jetpackjoyride.core.movement.Movement;

public final class Laser extends AbstractObstacle {
    private final static Integer TIMEFORCHARGING = 100;
    private final static Integer LASERDURATION = 150;
    private final static Integer TIMEFORDECHARGING = 80;

    public Laser(final Movement movement, final Hitbox hitbox) {
        super(ObstacleType.LASER, movement, hitbox);
        this.entityStatus = EntityStatus.CHARGING;
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