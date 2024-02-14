package it.unibo.jetpackjoyride.core.entities.obstacle.impl;

import it.unibo.jetpackjoyride.core.entities.obstacle.api.AbstractObstacle;
import it.unibo.jetpackjoyride.core.hitbox.api.Hitbox;
import it.unibo.jetpackjoyride.core.movement.Movement;

public final class Laser extends AbstractObstacle {
    public Laser(final Movement movement, final Hitbox hitbox) {
        super(ObstacleType.LASER, movement, hitbox);
        this.obstacleStatus = ObstacleStatus.CHARGING;
    }

    @Override
    public void updateStatus() {
        if (this.obstacleStatus.equals(ObstacleStatus.DEACTIVATED) && this.lifetime < 250) {
            this.lifetime = 250;
        }

        switch (this.lifetime) {
            case 100:
                this.obstacleStatus = ObstacleStatus.ACTIVE;
                break;
            case 250:
                this.obstacleStatus = ObstacleStatus.DEACTIVATED;
                break;
            case 330:
                this.obstacleStatus = ObstacleStatus.INACTIVE;
            default:
                break;
        }
    }

}