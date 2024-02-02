package it.unibo.jetpackjoyride.core.entities.obstacle.impl;

import it.unibo.jetpackjoyride.core.entities.obstacle.api.AbstractObstacle;
import it.unibo.jetpackjoyride.core.hitbox.Hitbox;
import it.unibo.jetpackjoyride.core.movement.Movement;

import java.util.*;

public class Zapper extends AbstractObstacle {
    public Zapper(Movement movement, Hitbox hitbox) {
        super(ObstacleType.ZAPPER, movement, hitbox);
        this.obstacleStatus = ObstacleStatus.ACTIVE;
    }

    @Override
    public void update() {
        this.movement.update();
        this.hitbox.updateHitbox(this.movement.getCurrentPosition(), this.movement.getRotation().get2());
        this.lifetime++;

        if(this.movement.getCurrentPosition().get1() < -200) {
            this.obstacleStatus = ObstacleStatus.INACTIVE;
        }
    }
}