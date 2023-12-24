package core.entities.obstacle.impl;

import static core.entities.entity.api.Entity.EntityType.OBSTACLE;
import static core.entities.obstacle.api.Obstacle.ObstacleStatus.*;

import core.entities.entity.api.AbstractEntity;
import core.entities.obstacle.api.Obstacle;
import core.hitbox.Hitbox;
import core.movement.impl.MovementImpl;

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
