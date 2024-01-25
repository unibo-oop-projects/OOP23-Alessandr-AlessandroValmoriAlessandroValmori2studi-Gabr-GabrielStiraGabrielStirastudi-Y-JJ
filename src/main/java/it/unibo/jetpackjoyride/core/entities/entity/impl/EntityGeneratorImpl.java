package it.unibo.jetpackjoyride.core.entities.entity.impl;


import it.unibo.jetpackjoyride.core.entities.entity.api.EntityGenerator;
import it.unibo.jetpackjoyride.core.entities.obstacle.api.Obstacle.ObstacleType;

import it.unibo.jetpackjoyride.core.entities.obstacle.impl.ObstacleImpl;
import it.unibo.jetpackjoyride.core.hitbox.Hitbox;
import it.unibo.jetpackjoyride.core.movement.Movement;


public class EntityGeneratorImpl implements EntityGenerator {

    @Override
    public ObstacleImpl generateObstacle(ObstacleType obstacleType, Movement obstacleMovement, Hitbox obstacleHitbox) {
        return new ObstacleImpl(obstacleType, obstacleMovement, obstacleHitbox);
    }

}
