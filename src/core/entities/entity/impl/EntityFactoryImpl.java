package core.entities.entity.impl;

import core.entities.entity.api.EntityFactory;
import core.entities.obstacle.api.Obstacle.ObstacleType;
import core.entities.obstacle.impl.ObstacleImpl;
import core.hitbox.Hitbox;
import core.movement.impl.MovementImpl;

public class EntityFactoryImpl implements EntityFactory {

    @Override
    public <X extends MovementImpl> ObstacleImpl generateObstacle(ObstacleType obstacleType, X movement, Hitbox hitbox) {
        return new ObstacleImpl(obstacleType, movement, hitbox);
    }
}
