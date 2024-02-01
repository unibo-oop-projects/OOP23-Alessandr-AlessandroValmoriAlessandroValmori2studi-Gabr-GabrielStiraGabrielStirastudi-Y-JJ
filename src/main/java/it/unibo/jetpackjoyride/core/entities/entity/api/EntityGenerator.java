package it.unibo.jetpackjoyride.core.entities.entity.api;


import it.unibo.jetpackjoyride.core.movement.Movement;

import static it.unibo.jetpackjoyride.core.entities.obstacle.api.Obstacle.ObstacleType;

import it.unibo.jetpackjoyride.core.entities.obstacle.api.Obstacle;
import it.unibo.jetpackjoyride.core.hitbox.Hitbox;


public interface EntityGenerator {
    Obstacle generateObstacle(ObstacleType obstacleType, Movement obstacleMovement, Hitbox obstacleHitbox);
}
