package entities.entity.api;

import entities.obstacle.impl.ObstacleImpl;
import entities.powerup.impl.PowerUpImpl;
import hitbox.Hitbox;
import movement.impl.MovementImpl;
import static entities.obstacle.api.Obstacle.ObstacleType;
import static entities.powerup.api.PowerUp.PowerUpType;

public interface EntityFactory {
    <X extends MovementImpl> ObstacleImpl generateObstacle(ObstacleType obstacleType, X movement, Hitbox hitbox);

    <X extends MovementImpl> PowerUpImpl generatePowerUp(PowerUpType powerUpType, X movement, Hitbox hitbox);
}
