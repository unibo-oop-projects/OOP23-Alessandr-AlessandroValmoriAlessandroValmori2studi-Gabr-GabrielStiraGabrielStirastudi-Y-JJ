package it.unibo.jetpackjoyride.core.entities.obstacle.impl;

import static it.unibo.jetpackjoyride.core.entities.entity.api.Entity.EntityType.*;
import static it.unibo.jetpackjoyride.core.entities.obstacle.api.Obstacle.ObstacleStatus.*;

import it.unibo.jetpackjoyride.core.entities.entity.api.AbstractEntity;
import it.unibo.jetpackjoyride.core.entities.obstacle.api.Obstacle;
import it.unibo.jetpackjoyride.core.hitbox.Hitbox;
import it.unibo.jetpackjoyride.core.movement.Movement;

public class ObstacleImpl extends AbstractEntity implements Obstacle {

    private final ObstacleType obstacleType;
    private ObstacleStatus obstacleStatus;

    public ObstacleImpl(ObstacleType obstacleType, Movement movement, Hitbox hitbox) {
        super(OBSTACLE, movement, hitbox);
        this.obstacleType = obstacleType;
        this.obstacleStatus = INACTIVE;
    }

    public ObstacleType getObstacleType() {
        return this.obstacleType;
    }

    public ObstacleStatus getObstacleStatus() {
        return this.obstacleStatus;
    }

    public void changeObstacleStatus(ObstacleStatus newObstacleStatus) {
        this.obstacleStatus = newObstacleStatus;
    }

    public void changeObstacleMovement(Movement newMovement) {
        this.movement= newMovement;
    }

    public boolean isOutOfBounds() {
        return this.movement.getCurrentPosition().get1() < -200;
    }
    
}
