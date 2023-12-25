package core.entities.entity.impl;

import core.entities.entity.api.EntityFactory;
import core.entities.obstacle.api.Obstacle.ObstacleType;
import core.entities.obstacle.impl.ObstacleImpl;
import core.entities.powerup.api.PowerUp.PowerUpType;
import core.entities.powerup.impl.PowerUpImpl;
import core.hitbox.Hitbox;
import core.movement.impl.MovementImpl;

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
