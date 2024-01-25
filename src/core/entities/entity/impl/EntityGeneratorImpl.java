package core.entities.entity.impl;


import core.entities.entity.api.EntityGenerator;
import core.entities.obstacle.api.Obstacle.ObstacleType;

import core.entities.obstacle.impl.ObstacleImpl;
import core.hitbox.Hitbox;
import core.movement.Movement;


public class EntityGeneratorImpl implements EntityGenerator {

    @Override
    public ObstacleImpl generateObstacle(ObstacleType obstacleType, Movement obstacleMovement, Hitbox obstacleHitbox) {
        return new ObstacleImpl(obstacleType, obstacleMovement, obstacleHitbox);
    }

}
