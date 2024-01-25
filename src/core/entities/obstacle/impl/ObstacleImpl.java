package core.entities.obstacle.impl;

import static core.entities.entity.api.Entity.EntityType.*;
import static core.entities.obstacle.api.Obstacle.ObstacleStatus.*;

import core.entities.entity.api.AbstractEntity;
import core.entities.obstacle.api.Obstacle;
import core.hitbox.Hitbox;
import core.movement.Movement;

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
    
}
