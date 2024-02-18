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
import it.unibo.jetpackjoyride.core.handler.obstacle.ObstacleView;
import it.unibo.jetpackjoyride.core.handler.pickup.PickUpView;
import it.unibo.jetpackjoyride.core.handler.powerup.PowerUpView;

public interface EntityGenerator {
    List<GenericController<Obstacle, ObstacleView>> generateObstacle(ObstacleType obstacleType, Movement obstacleMovement);

    List<GenericController<PowerUp, PowerUpView>> generatePowerUp(PowerUpType powerUpType);

    List<GenericController<PickUp, PickUpView>> generatePickUp(PickUpType pickUpType);
}
