package core.entities.entity.api;

import core.entities.obstacle.impl.ObstacleImpl;
import core.entities.powerup.impl.PowerUpImpl;
import core.hitbox.Hitbox;
import core.movement.impl.MovementImpl;
import static core.entities.obstacle.api.Obstacle.ObstacleType;
import static core.entities.powerup.api.PowerUp.PowerUpType;

public interface EntityFactory {
    <X extends MovementImpl> ObstacleImpl generateObstacle(ObstacleType obstacleType, X movement, Hitbox hitbox);

    <X extends MovementImpl> PowerUpImpl generatePowerUp(PowerUpType powerUpType, X movement, Hitbox hitbox);
}
