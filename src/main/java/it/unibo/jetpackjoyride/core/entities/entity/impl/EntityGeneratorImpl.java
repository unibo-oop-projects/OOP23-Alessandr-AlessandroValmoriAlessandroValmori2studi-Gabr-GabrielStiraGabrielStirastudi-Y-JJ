package it.unibo.jetpackjoyride.core.entities.entity.impl;


import java.util.List;

import it.unibo.jetpackjoyride.core.entities.entity.api.EntityGenerator;
import it.unibo.jetpackjoyride.core.entities.obstacle.api.Obstacle;
import it.unibo.jetpackjoyride.core.entities.obstacle.api.Obstacle.ObstacleType;

import it.unibo.jetpackjoyride.core.entities.obstacle.impl.*;
import it.unibo.jetpackjoyride.core.entities.powerup.api.PowerUp;
import it.unibo.jetpackjoyride.core.entities.powerup.api.PowerUp.PowerUpType;
import it.unibo.jetpackjoyride.core.entities.powerup.impl.DukeFishron;
import it.unibo.jetpackjoyride.core.entities.powerup.impl.LilStomper;
import it.unibo.jetpackjoyride.core.entities.powerup.impl.MrCuddlesGenerator;
import it.unibo.jetpackjoyride.core.entities.powerup.impl.ProfitBird;
import it.unibo.jetpackjoyride.core.hitbox.api.Hitbox;
import it.unibo.jetpackjoyride.core.movement.Movement;

public class EntityGeneratorImpl implements EntityGenerator {

    @Override
    public Obstacle generateObstacle(final ObstacleType obstacleType, final Movement obstacleMovement, final Hitbox obstacleHitbox) {
        switch (obstacleType) {
            case MISSILE:
                return new Missile(obstacleMovement, obstacleHitbox); //Canon obstacle existing in the original game
            case ZAPPER:
                return new Zapper(obstacleMovement, obstacleHitbox); //Canon obstacle existing in the original game
            case LASER:
                return new Laser(obstacleMovement, obstacleHitbox); //Canon obstacle existing in the original game
            default:
                break;
  
        }
        throw new IllegalArgumentException("EntityGenerator could not generate the obstacle");
    }

    @Override
    public List<PowerUp> generatePowerUp(final PowerUpType powerUpType, final Movement powerUpMovement, final Hitbox powerUpHitbox) {
        switch (powerUpType) {
            case LILSTOMPER:
                return List.of(new LilStomper(powerUpMovement, powerUpHitbox)); //Canon powerup existing in the original game
            case PROFITBIRD:
                return List.of(new ProfitBird(powerUpMovement, powerUpHitbox)); //Canon powerup existing in the original game
            case MRCUDDLES:
                return new MrCuddlesGenerator(powerUpMovement, powerUpHitbox).generateMrCuddle(); //Canon powerup existing in the original game
            case DUKEFISHRON:
                return List.of(new DukeFishron(powerUpMovement, powerUpHitbox)); //Non canon powerup. An easter egg for Terraria players ;)
            default:
                break;
        }
        throw new IllegalArgumentException("EntityGenerator could not generate the powerup");
    }
}
