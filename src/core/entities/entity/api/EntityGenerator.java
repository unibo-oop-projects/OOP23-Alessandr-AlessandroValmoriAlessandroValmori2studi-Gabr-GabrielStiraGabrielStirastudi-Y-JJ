package core.entities.entity.api;


import core.movement.Movement;

import static core.entities.obstacle.api.Obstacle.ObstacleType;

import core.entities.obstacle.impl.ObstacleImpl;
import core.hitbox.Hitbox;


public interface EntityGenerator {
    ObstacleImpl generateObstacle(ObstacleType obstacleType, Movement obstacleMovement, Hitbox obstacleHitbox);
}
