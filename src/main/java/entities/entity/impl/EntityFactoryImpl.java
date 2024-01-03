package entities.entity.impl;

import entities.entity.api.EntityFactory;
import entities.obstacle.api.Obstacle.ObstacleType;
import entities.obstacle.impl.ObstacleImpl;
import entities.powerup.api.PowerUp.PowerUpType;
import entities.powerup.impl.PowerUpImpl;
import hitbox.Hitbox;
import movement.impl.MovementImpl;

public class EntityFactoryImpl implements EntityFactory {

    @Override
    public <X extends MovementImpl> ObstacleImpl generateObstacle(ObstacleType obstacleType, X movement, Hitbox hitbox) {
        return new ObstacleImpl(obstacleType, movement, hitbox);
    }

    @Override
    public <X extends MovementImpl> PowerUpImpl generatePowerUp(PowerUpType powerUpType, X movement, Hitbox hitbox) {
       return new PowerUpImpl(powerUpType, movement, hitbox);
    }
}
