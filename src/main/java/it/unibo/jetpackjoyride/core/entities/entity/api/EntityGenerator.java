package it.unibo.jetpackjoyride.core.entities.entity.api;

import it.unibo.jetpackjoyride.core.movement.Movement;

import java.util.List;

import it.unibo.jetpackjoyride.core.entities.obstacle.api.Obstacle;
import it.unibo.jetpackjoyride.core.entities.obstacle.api.Obstacle.ObstacleType;
import it.unibo.jetpackjoyride.core.entities.pickups.api.PickUp;
import it.unibo.jetpackjoyride.core.entities.pickups.api.PickUp.PickUpType;
import it.unibo.jetpackjoyride.core.entities.powerup.api.PowerUp;
import it.unibo.jetpackjoyride.core.entities.powerup.api.PowerUp.PowerUpType;
import it.unibo.jetpackjoyride.core.handler.generic.GenericController;
import it.unibo.jetpackjoyride.core.handler.powerup.PowerUpView;
import it.unibo.jetpackjoyride.core.hitbox.api.Hitbox;

public interface EntityGenerator {
    Obstacle generateObstacle(ObstacleType obstacleType, Movement obstacleMovement, Hitbox obstacleHitbox);

    List<GenericController<PowerUp, PowerUpView>> generatePowerUp(PowerUpType powerUpType);

    PickUp generatePickUp(PickUpType pickUpType, Movement pickUpMovement, Hitbox pickUpHitbox);
}
