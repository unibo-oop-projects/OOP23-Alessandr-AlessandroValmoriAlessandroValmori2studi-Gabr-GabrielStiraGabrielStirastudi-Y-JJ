package it.unibo.jetpackjoyride.core.entities.entity.impl;

import java.util.ArrayList;
import java.util.List;

import it.unibo.jetpackjoyride.core.entities.entity.api.EntityModelGenerator;
import it.unibo.jetpackjoyride.core.entities.obstacle.api.Obstacle;
import it.unibo.jetpackjoyride.core.entities.obstacle.api.Obstacle.ObstacleType;
import it.unibo.jetpackjoyride.core.entities.obstacle.impl.Laser;
import it.unibo.jetpackjoyride.core.entities.obstacle.impl.Missile;
import it.unibo.jetpackjoyride.core.entities.obstacle.impl.Zapper;
import it.unibo.jetpackjoyride.core.entities.pickups.api.PickUp;
import it.unibo.jetpackjoyride.core.entities.pickups.api.PickUp.PickUpType;
import it.unibo.jetpackjoyride.core.entities.pickups.impl.VehiclePickUp;
import it.unibo.jetpackjoyride.core.entities.powerup.api.PowerUp;
import it.unibo.jetpackjoyride.core.entities.powerup.api.PowerUp.PowerUpType;
import it.unibo.jetpackjoyride.core.entities.powerup.impl.DukeFishron;
import it.unibo.jetpackjoyride.core.entities.powerup.impl.LilStomper;
import it.unibo.jetpackjoyride.core.entities.powerup.impl.MrCuddlesGenerator;
import it.unibo.jetpackjoyride.core.entities.powerup.impl.ProfitBird;
import it.unibo.jetpackjoyride.core.hitbox.api.Hitbox;
import it.unibo.jetpackjoyride.core.hitbox.impl.HitboxImpl;
import it.unibo.jetpackjoyride.core.movement.Movement;
import it.unibo.jetpackjoyride.core.movement.Movement.MovementChangers;
import it.unibo.jetpackjoyride.core.movement.MovementImpl;
import it.unibo.jetpackjoyride.utilities.GameInfo;
import it.unibo.jetpackjoyride.utilities.Pair;

public class EntityModelGeneratorImpl implements EntityModelGenerator{

    @Override
    public Obstacle generateObstacle(final ObstacleType obstacleType, final Movement obstacleMovement) {
        final Double screenSizeX = GameInfo.getInstance().getScreenWidth();
        final Double screenSizeY = GameInfo.getInstance().getScreenHeight();
        Hitbox obstacleHitbox;
        final Obstacle obstacleModel;
        
        switch (obstacleType) {
            case MISSILE: //Canon obstacle existing in the original game
                obstacleHitbox = new HitboxImpl(obstacleMovement.getCurrentPosition(), new Pair<>(screenSizeX / 32, screenSizeY / 48));
                obstacleModel = new Missile(obstacleMovement, obstacleHitbox);
                break;
            case ZAPPER: //Canon obstacle existing in the original game
                obstacleHitbox = new HitboxImpl(obstacleMovement.getCurrentPosition(), new Pair<>(screenSizeX / 8, screenSizeY / 24));
                obstacleModel = new Zapper(obstacleMovement, obstacleHitbox);
                break;
            case LASER: //Canon obstacle existing in the original game
                obstacleHitbox = new HitboxImpl(obstacleMovement.getCurrentPosition(), new Pair<>(screenSizeX, screenSizeY / 30));
                obstacleModel = new Laser(obstacleMovement, obstacleHitbox);
                break;
            default:
                throw new IllegalArgumentException("EntityModelGenerator could not generate the obstacle");
        }

        return obstacleModel;
    }

    @Override
    public List<PowerUp> generatePowerUp(final PowerUpType powerUpType) {
        final Double screenSizeX = GameInfo.getInstance().getScreenWidth();
        final Double screenSizeY = GameInfo.getInstance().getScreenHeight();
        Movement powerUpMovement;
        Hitbox powerUpHitbox;
        final List<PowerUp> powerUpModels = new ArrayList<>();
        
        switch (powerUpType) {
            case LILSTOMPER: //Canon powerup existing in the original game
                powerUpMovement = new MovementImpl(new Pair<>(screenSizeX / 4, screenSizeY - screenSizeY / 8), new Pair<>(0.0, 0.0),new Pair<>(0.0, 0.0), new Pair<>(0.0, 0.0),List.of(MovementChangers.GRAVITY, MovementChangers.BOUNDS));
                powerUpHitbox = new HitboxImpl(powerUpMovement.getCurrentPosition(), new Pair<>(screenSizeX / 8, screenSizeY / 5));
                powerUpModels.add(new LilStomper(powerUpMovement, powerUpHitbox));
                break;
            case MRCUDDLES: //Canon powerup existing in the original game
                powerUpMovement = new MovementImpl(new Pair<>(screenSizeX/5, screenSizeY/8), new Pair<>(0.0, 0.0), new Pair<>(0.0, 0.0),new Pair<>(0.0, 0.0), List.of(MovementChangers.INVERSEGRAVITY, MovementChangers.BOUNDS));
                powerUpHitbox = new HitboxImpl(powerUpMovement.getCurrentPosition(), new Pair<>(screenSizeX / 10, screenSizeY / 10));
                powerUpModels.addAll(new MrCuddlesGenerator(powerUpMovement, powerUpHitbox).generateMrCuddle());
                break;
            case PROFITBIRD: //Canon powerup existing in the original game
                powerUpMovement = new MovementImpl(new Pair<>(screenSizeX / 4, screenSizeY - screenSizeY / 8), new Pair<>(0.0, 0.0),new Pair<>(0.0, 0.0), new Pair<>(0.0, 0.0),List.of(MovementChangers.GRAVITY, MovementChangers.BOUNDS));
                powerUpHitbox = new HitboxImpl(powerUpMovement.getCurrentPosition(), new Pair<>(screenSizeX / 10, screenSizeY / 10));
                powerUpModels.add(new ProfitBird(powerUpMovement, powerUpHitbox));
                break;
            case DUKEFISHRON: //Non canon powerup. An easter egg for Terraria players ;)
                powerUpMovement = new MovementImpl(new Pair<>(screenSizeX / 4, screenSizeY - screenSizeY / 8), new Pair<>(0.0, 10.0),new Pair<>(0.0, 0.0), new Pair<>(0.0, 0.0),List.of(MovementChangers.BOUNCING));
                powerUpHitbox = new HitboxImpl(powerUpMovement.getCurrentPosition(), new Pair<>(screenSizeX / 10, screenSizeY / 10));
                powerUpModels.add(new DukeFishron(powerUpMovement, powerUpHitbox));
                break;
            default:
            throw new IllegalArgumentException("EntityGenerator could not generate the powerup");
        }
        return powerUpModels;
    }

    @Override
    public PickUp generatePickUp(final PickUpType pickUpType) {
        final Double screenSizeX = GameInfo.getInstance().getScreenWidth();
        final Double screenSizeY = GameInfo.getInstance().getScreenHeight();
        Movement pickUpMovement;
        Hitbox pickUpHitbox;
        final PickUp pickUpModel;

        switch (pickUpType) {
            case VEHICLE: // Canon pickup existing in the original game
                pickUpMovement = new MovementImpl(new Pair<>(screenSizeX, screenSizeY/2), new Pair<>(-3.0, 0.0),new Pair<>(0.0, 0.0), new Pair<>(0.0, 0.0),List.of(MovementChangers.GRAVITY));
                pickUpHitbox = new HitboxImpl(pickUpMovement.getCurrentPosition(), new Pair<>(screenSizeX / 15, screenSizeY / 9));
                pickUpModel = new VehiclePickUp(pickUpMovement, pickUpHitbox);
                break;
        
            default:
            throw new IllegalArgumentException("EntityGenerator could not generate the pickup");
            
        }
        return pickUpModel;
    }
}
