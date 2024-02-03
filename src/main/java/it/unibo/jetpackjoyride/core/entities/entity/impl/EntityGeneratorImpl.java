package it.unibo.jetpackjoyride.core.entities.entity.impl;


import it.unibo.jetpackjoyride.core.entities.entity.api.EntityGenerator;
import it.unibo.jetpackjoyride.core.entities.obstacle.api.Obstacle;
import it.unibo.jetpackjoyride.core.entities.obstacle.api.Obstacle.ObstacleType;

import it.unibo.jetpackjoyride.core.entities.obstacle.impl.*;
import it.unibo.jetpackjoyride.core.entities.powerup.api.PowerUp;
import it.unibo.jetpackjoyride.core.entities.powerup.api.PowerUp.PowerUpType;
import it.unibo.jetpackjoyride.core.entities.powerup.impl.LilStomper;
import it.unibo.jetpackjoyride.core.hitbox.api.Hitbox;
import it.unibo.jetpackjoyride.core.movement.Movement;

public class EntityGeneratorImpl implements EntityGenerator {

    @Override
    public Obstacle generateObstacle(ObstacleType obstacleType, Movement obstacleMovement, Hitbox obstacleHitbox) {
        switch (obstacleType) {
            case MISSILE:
                return new Missile(obstacleMovement, obstacleHitbox); //Canon obstacle existing in the original game
            case ZAPPER:
                return new Zapper(obstacleMovement, obstacleHitbox); //Canon obstacle existing in the original game
            case LASER:
                return new Laser(obstacleMovement, obstacleHitbox); //Canon obstacle existing in the original game
            default:
                return null;
        }
    }

    @Override
    public PowerUp generatePowerUp(PowerUpType powerUpType, Movement powerUpMovement, Hitbox powerUpHitbox) {
        switch (powerUpType) {
            case LILSTOMPER:
                return new LilStomper(powerUpMovement, powerUpHitbox); //Canon powerup existing in the original game
            case BADASHOG:
                return null; //Canon powerup existing in the original game
            case MRCUDDLES:
                return null; //new MrCuddles(powerUpMovement, powerUpHitbox); //Canon powerup existing in the original game
            default:
                return null;
        }
    }
}
