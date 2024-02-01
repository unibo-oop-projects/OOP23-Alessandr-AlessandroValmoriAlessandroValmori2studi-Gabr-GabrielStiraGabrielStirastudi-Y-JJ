package it.unibo.jetpackjoyride.core.entities.obstacle.impl;
import static it.unibo.jetpackjoyride.core.entities.obstacle.api.Obstacle.ObstacleStatus.CHARGING;

import it.unibo.jetpackjoyride.core.entities.obstacle.api.AbstractObstacle;
import it.unibo.jetpackjoyride.core.hitbox.Hitbox;
import it.unibo.jetpackjoyride.core.movement.Movement;

public class CustomObstacle extends AbstractObstacle {
    public CustomObstacle(Movement movement, Hitbox hitbox) {
        super(ObstacleType.CUSTOMOBSTACLE, movement, hitbox);
        this.obstacleStatus = CHARGING;
    }

    @Override
    public void update() {
        this.movement.update();
        this.hitbox.updateHitbox(this.movement.getCurrentPosition(), this.movement.getRotation().get2());
        this.lifetime++;
    }
}