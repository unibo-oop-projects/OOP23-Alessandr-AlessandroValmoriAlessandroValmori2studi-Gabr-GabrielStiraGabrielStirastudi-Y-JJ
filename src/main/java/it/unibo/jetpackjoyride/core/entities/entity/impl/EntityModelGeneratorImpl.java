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
import it.unibo.jetpackjoyride.core.entities.pickups.impl.ShieldPickUp;
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
import it.unibo.jetpackjoyride.utilities.MovementChangers;
import it.unibo.jetpackjoyride.utilities.Pair;

public class EntityModelGeneratorImpl implements EntityModelGenerator{
    private final static Pair<Double,Double> MISSILEHITBOXDIMENSIONS = new Pair<>(40.0, 15.0);
    private final static Pair<Double,Double> ZAPPERHITBOXDIMENSIONS = new Pair<>(160.0, 30.0);
    private final static Pair<Double,Double> LASERHITBOXDIMENSIONS = new Pair<>(1180.0, 24.0);

    private final static Pair<Double,Double> LILSTOMPERSPAWNINGCOORDINATES = new Pair<>(200.0, 360.0);
    private final static Pair<Double,Double> LILSTOMPERHITBOXDIMENSIONS = new Pair<>(160.0, 140.0);

    private final static Pair<Double,Double> MRCUDDLESPAWNINGCOORDINATES = new Pair<>(200.0, 0.0);
    private final static Pair<Double,Double> MRCUDDLEHITBOXDIMENSIONS = new Pair<>(120.0, 70.0);

    private final static Pair<Double,Double> PROFITBIRDSPAWNINGCOORDINATES = new Pair<>(200.0, 360.0);
    private final static Pair<Double,Double> PROFITBIRDHITBOXDIMENSIONS = new Pair<>(120.0, 70.0);

    private final static Pair<Double,Double> DUKEFISHRONSPAWNINGCOORDINATES = new Pair<>(200.0, 360.0);
    private final static Pair<Double,Double> DUKEFISHRONHITBOXDIMENSIONS = new Pair<>(150.0, 100.0);
    private final static Pair<Double,Double> DUKEFISHRONBASESPEED = new Pair<>(0.0, 10.0);
    private final static Double DUKEROTATIONANGLE = 20.0;

    private final static Pair<Double,Double> VEHICLEPICKUPSPAWNINGCOORDINATES = new Pair<>(300.0, 360.0);
    private final static Pair<Double,Double> VEHICLEPICKUPHITBOXDIMENSIONS = new Pair<>(80.0, 80.0);
    private final static Pair<Double,Double> VEHICLEPICKUPBASESPEED = new Pair<>(-3.0, 0.0);

    private final static Pair<Double,Double> SHIELDPICKUPSPAWNINGCOORDINATES = new Pair<>(800.0, 360.0);
    private final static Pair<Double,Double> SHIELDPICKUPHITBOXDIMENSIONS = new Pair<>(50.0, 50.0);
    private final static Pair<Double,Double> SHIELDPICKUPBASESPEED = new Pair<>(-5.0, 0.0);

    @Override
    public Obstacle generateObstacle(final ObstacleType obstacleType, final Movement obstacleMovement) {
        Hitbox obstacleHitbox;
        final Obstacle obstacleModel;
        
        switch (obstacleType) {
            case MISSILE: //Canon obstacle existing in the original game
                obstacleHitbox = new HitboxImpl(obstacleMovement.getRelativePosition(), MISSILEHITBOXDIMENSIONS);
                obstacleModel = new Missile(obstacleMovement, obstacleHitbox);
                break;
            case ZAPPER: //Canon obstacle existing in the original game
                obstacleHitbox = new HitboxImpl(obstacleMovement.getRelativePosition(), ZAPPERHITBOXDIMENSIONS);
                obstacleModel = new Zapper(obstacleMovement, obstacleHitbox);
                break;
            case LASER: //Canon obstacle existing in the original game
                obstacleHitbox = new HitboxImpl(obstacleMovement.getRelativePosition(), LASERHITBOXDIMENSIONS);
                obstacleModel = new Laser(obstacleMovement, obstacleHitbox);
                break;
            default:
                throw new IllegalArgumentException("EntityModelGenerator could not generate the obstacle");
        }

        return obstacleModel;
    }

    @Override
    public List<PowerUp> generatePowerUp(final PowerUpType powerUpType) {
        Movement powerUpMovement;
        Hitbox powerUpHitbox;
        final List<PowerUp> powerUpModels = new ArrayList<>();
        
        switch (powerUpType) {
            case LILSTOMPER: //Canon powerup existing in the original game
                powerUpMovement = new Movement.Builder().setPosition(LILSTOMPERSPAWNINGCOORDINATES).setMovementChangers(List.of(MovementChangers.GRAVITY, MovementChangers.BOUNDS)).build();
                powerUpHitbox = new HitboxImpl(powerUpMovement.getRelativePosition(), LILSTOMPERHITBOXDIMENSIONS);
                powerUpModels.add(new LilStomper(powerUpMovement, powerUpHitbox));
                break;
            case MRCUDDLES: //Canon powerup existing in the original game
                powerUpMovement = new Movement.Builder().setPosition(MRCUDDLESPAWNINGCOORDINATES).setMovementChangers(List.of(MovementChangers.INVERSEGRAVITY, MovementChangers.BOUNDS)).build();
                powerUpHitbox = new HitboxImpl(powerUpMovement.getRelativePosition(), MRCUDDLEHITBOXDIMENSIONS);
                powerUpModels.addAll(new MrCuddlesGenerator(powerUpMovement, powerUpHitbox).generateMrCuddle());
                break;
            case PROFITBIRD: //Canon powerup existing in the original game
                powerUpMovement = new Movement.Builder().setPosition(PROFITBIRDSPAWNINGCOORDINATES).setMovementChangers(List.of(MovementChangers.GRAVITY, MovementChangers.BOUNDS)).build();
                powerUpHitbox = new HitboxImpl(powerUpMovement.getRelativePosition(), PROFITBIRDHITBOXDIMENSIONS);
                powerUpModels.add(new ProfitBird(powerUpMovement, powerUpHitbox));
                break;
            case DUKEFISHRON: //Non canon powerup. An easter egg for Terraria players ;)
                powerUpMovement = new Movement.Builder().setPosition(DUKEFISHRONSPAWNINGCOORDINATES).setSpeed(DUKEFISHRONBASESPEED).setRotation(DUKEROTATIONANGLE,0.0).setMovementChangers(List.of(MovementChangers.BOUNCING)).build();
                powerUpHitbox = new HitboxImpl(powerUpMovement.getRelativePosition(), DUKEFISHRONHITBOXDIMENSIONS);
                powerUpModels.add(new DukeFishron(powerUpMovement, powerUpHitbox));
                break;
            default:
            throw new IllegalArgumentException("EntityGenerator could not generate the powerup");
        }
        return powerUpModels;
    }

    @Override
    public PickUp generatePickUp(final PickUpType pickUpType) {
        Movement pickUpMovement;
        Hitbox pickUpHitbox;
        final PickUp pickUpModel;

        switch (pickUpType) {
            case VEHICLE: // Canon pickup existing in the original game
                pickUpMovement = new Movement.Builder().setPosition(VEHICLEPICKUPSPAWNINGCOORDINATES).setSpeed(VEHICLEPICKUPBASESPEED).setMovementChangers(List.of(MovementChangers.GRAVITY)).build();
                pickUpHitbox = new HitboxImpl(pickUpMovement.getRelativePosition(), VEHICLEPICKUPHITBOXDIMENSIONS);
                pickUpModel = new VehiclePickUp(pickUpMovement, pickUpHitbox);
                break;
            case SHIELD: // Canon pickup existing in the original game
                pickUpMovement = new Movement.Builder().setPosition(SHIELDPICKUPSPAWNINGCOORDINATES).setSpeed(SHIELDPICKUPBASESPEED).build();
                pickUpHitbox = new HitboxImpl(pickUpMovement.getRelativePosition(), SHIELDPICKUPHITBOXDIMENSIONS);
                pickUpModel = new ShieldPickUp(pickUpMovement, pickUpHitbox);
                break;
        
            default:
            throw new IllegalArgumentException("EntityGenerator could not generate the pickup");
            
        }
        return pickUpModel;
    }
}
