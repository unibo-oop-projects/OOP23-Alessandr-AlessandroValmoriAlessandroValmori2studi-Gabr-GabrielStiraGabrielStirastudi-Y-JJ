package it.unibo.jetpackjoyride.core.entities.obstacle.impl;

import it.unibo.jetpackjoyride.core.entities.obstacle.api.AbstractObstacle;
import it.unibo.jetpackjoyride.core.hitbox.api.Hitbox;
import it.unibo.jetpackjoyride.core.movement.Movement;

public final class Laser extends AbstractObstacle {
    public Laser(final Movement movement, final Hitbox hitbox) {
        super(ObstacleType.LASER, movement, hitbox);
        this.entityStatus = EntityStatus.CHARGING;
    }

    @Override
    public void updateStatus(final boolean isSpaceBarPressed) {
        if (this.entityStatus.equals(EntityStatus.DEACTIVATED) && this.lifetime < 250) {
            this.lifetime = 250;
        }

        switch (this.lifetime) {
            case 100:
                this.entityStatus = EntityStatus.ACTIVE;
                break;
            case 250:
                this.entityStatus = EntityStatus.DEACTIVATED;
                break;
            case 330:
                this.entityStatus = EntityStatus.INACTIVE;
            default:
                break;
        }
    }
}