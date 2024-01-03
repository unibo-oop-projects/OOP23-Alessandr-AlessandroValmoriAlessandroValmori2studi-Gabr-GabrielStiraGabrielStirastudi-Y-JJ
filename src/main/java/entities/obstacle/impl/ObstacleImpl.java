package entities.obstacle.impl;

import static entities.entity.api.Entity.EntityType.OBSTACLE;
import static entities.obstacle.api.Obstacle.ObstacleStatus.*;

import entities.entity.api.AbstractEntity;
import entities.obstacle.api.Obstacle;
import hitbox.Hitbox;
import movement.impl.MovementImpl;

public class ObstacleImpl extends AbstractEntity implements Obstacle {

    private final ObstacleType obstacleType;
    private ObstacleStatus obstacleStatus;

    public ObstacleImpl(ObstacleType obstacleType, MovementImpl movement, Hitbox hitbox) {
        super(OBSTACLE, movement, hitbox);
        this.obstacleType = obstacleType;
    }

    public ObstacleType getObstacleType() {
        return this.obstacleType;
    }

    @Override
    public boolean deactivateObstacle() {
        if(this.obstacleStatus == ACTIVE) {
            this.obstacleStatus = DEACTIVATED;
            return true;
        }
        return false;
    }
    
}
