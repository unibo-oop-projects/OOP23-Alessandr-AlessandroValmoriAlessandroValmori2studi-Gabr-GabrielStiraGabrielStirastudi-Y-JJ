package it.unibo.jetpackjoyride.core.entities.entity.api;

import it.unibo.jetpackjoyride.core.movement.Movement;

import it.unibo.jetpackjoyride.core.entities.obstacle.api.Obstacle;
import it.unibo.jetpackjoyride.core.entities.obstacle.api.Obstacle.ObstacleType;
import it.unibo.jetpackjoyride.core.entities.powerup.api.PowerUp;
import it.unibo.jetpackjoyride.core.entities.powerup.api.PowerUp.PowerUpType;
import it.unibo.jetpackjoyride.core.hitbox.Hitbox;


public interface EntityGenerator {
    Obstacle generateObstacle(ObstacleType obstacleType, Movement obstacleMovement, Hitbox obstacleHitbox);

    PowerUp generatePowerUp(PowerUpType powerUpType, Movement powerUpMovement, Hitbox powerUpHitbox);
}
