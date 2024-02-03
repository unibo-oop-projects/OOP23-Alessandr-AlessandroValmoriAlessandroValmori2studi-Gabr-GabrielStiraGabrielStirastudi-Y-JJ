package it.unibo.jetpackjoyride.core.entities.obstacle.impl;

import it.unibo.jetpackjoyride.core.entities.obstacle.api.AbstractObstacle;
import it.unibo.jetpackjoyride.core.hitbox.api.Hitbox;
import it.unibo.jetpackjoyride.core.movement.Movement;

public class Zapper extends AbstractObstacle {
    public Zapper(Movement movement, Hitbox hitbox) {
        super(ObstacleType.ZAPPER, movement, hitbox);
        this.obstacleStatus = ObstacleStatus.ACTIVE;
    }

    @Override
    protected void updateStatus() {

        if(this.movement.getCurrentPosition().get1() < -200) {
            this.obstacleStatus = ObstacleStatus.INACTIVE;
        }
    }
}