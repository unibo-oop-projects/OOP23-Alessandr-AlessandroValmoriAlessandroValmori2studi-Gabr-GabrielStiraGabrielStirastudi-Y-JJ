package it.unibo.jetpackjoyride.core.entities.obstacle.impl;
import static it.unibo.jetpackjoyride.core.entities.obstacle.api.Obstacle.ObstacleStatus.DEACTIVATED;

import it.unibo.jetpackjoyride.core.entities.obstacle.api.AbstractObstacle;
import it.unibo.jetpackjoyride.core.hitbox.Hitbox;
import it.unibo.jetpackjoyride.core.movement.Movement;

public class Laser extends AbstractObstacle {
    public Laser(Movement movement, Hitbox hitbox) {
        super(ObstacleType.LASER, movement, hitbox);
        this.obstacleStatus = ObstacleStatus.CHARGING;
    }

    @Override
    public void update() {
        this.movement.update();
        this.hitbox.updateHitbox(this.movement.getCurrentPosition(), this.movement.getRotation().get2());
        this.lifetime++;

        if(this.obstacleStatus.equals(DEACTIVATED) && this.lifetime < 250) {
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