package it.unibo.jetpackjoyride.core.entities.entity.impl;


import java.util.ArrayList;
import java.util.List;

import it.unibo.jetpackjoyride.core.entities.entity.api.EntityGenerator;
import it.unibo.jetpackjoyride.core.entities.obstacle.api.Obstacle;
import it.unibo.jetpackjoyride.core.entities.obstacle.api.Obstacle.ObstacleType;

import it.unibo.jetpackjoyride.core.entities.obstacle.impl.*;
import it.unibo.jetpackjoyride.core.entities.pickups.api.PickUp;
import it.unibo.jetpackjoyride.core.entities.pickups.impl.VehiclePickUp;
import it.unibo.jetpackjoyride.core.entities.powerup.api.PowerUp;
import it.unibo.jetpackjoyride.core.entities.powerup.api.PowerUp.PowerUpType;
import it.unibo.jetpackjoyride.core.entities.powerup.impl.DukeFishron;
import it.unibo.jetpackjoyride.core.entities.powerup.impl.LilStomper;
import it.unibo.jetpackjoyride.core.entities.powerup.impl.MrCuddlesGenerator;
import it.unibo.jetpackjoyride.core.entities.powerup.impl.ProfitBird;
import it.unibo.jetpackjoyride.core.handler.generic.GenericController;
import it.unibo.jetpackjoyride.core.handler.pickup.PickUpView;
import it.unibo.jetpackjoyride.core.handler.powerup.PowerUpView;
import it.unibo.jetpackjoyride.core.hitbox.api.Hitbox;
import it.unibo.jetpackjoyride.core.hitbox.impl.HitboxImpl;
import it.unibo.jetpackjoyride.core.movement.Movement;
import it.unibo.jetpackjoyride.core.movement.Movement.MovementChangers;
import it.unibo.jetpackjoyride.core.movement.MovementImpl;
import it.unibo.jetpackjoyride.utilities.GameInfo;
import it.unibo.jetpackjoyride.utilities.Pair;
import javafx.scene.image.Image;

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
    public List<GenericController<PowerUp, PowerUpView>> generatePowerUp(final PowerUpType powerUpType) {
        final Double screenSizeX = GameInfo.getInstance().getScreenWidth();
        final Double screenSizeY = GameInfo.getInstance().getScreenHeight();
        Movement powerUpMovement;
        Hitbox powerUpHitbox;
        PowerUpView powerUpView;
        final List<PowerUp> powerUpModel = new ArrayList<>();
        final List<GenericController<PowerUp, PowerUpView>> powerUp = new ArrayList<>();
        Image[] images;
        
        switch (powerUpType) {
            case LILSTOMPER: //Canon powerup existing in the original game
                powerUpMovement = new MovementImpl(new Pair<>(screenSizeX / 4, screenSizeY - screenSizeY / 8), new Pair<>(0.0, 0.0),new Pair<>(0.0, 0.0), new Pair<>(0.0, 0.0),List.of(MovementChangers.GRAVITY, MovementChangers.BOUNDS));
                powerUpHitbox = new HitboxImpl(powerUpMovement.getCurrentPosition(), new Pair<>(screenSizeX / 4, screenSizeY / 3), powerUpMovement.getRotation().get1());
                powerUpModel.add(new LilStomper(powerUpMovement, powerUpHitbox));

                images = imageLoader(24, "sprites/entities/powerups/lilstomper/lilstomper_");

                powerUpView = new PowerUpView(images);

                powerUp.add(new GenericController<PowerUp,PowerUpView>(powerUpModel.get(0), powerUpView));
                break;

            case MRCUDDLES: //Canon powerup existing in the original game
                powerUpMovement = new MovementImpl(new Pair<>(screenSizeX/5, screenSizeY/8), new Pair<>(0.0, 0.0), new Pair<>(0.0, 0.0),new Pair<>(0.0, 0.0), List.of(MovementChangers.INVERSEGRAVITY, MovementChangers.BOUNDS));
                powerUpHitbox = new HitboxImpl(powerUpMovement.getCurrentPosition(), new Pair<>(screenSizeX / 10, screenSizeY / 10), powerUpMovement.getRotation().get1());
                powerUpModel.addAll(new MrCuddlesGenerator(powerUpMovement, powerUpHitbox).generateMrCuddle());

                images = imageLoader(6, "sprites/entities/powerups/mrcuddles/mrcuddles_");

                for (var powerUpInstance : powerUpModel) {
                    powerUpView = new PowerUpView(images);
                    powerUp.add(new GenericController<PowerUp,PowerUpView>(powerUpInstance, powerUpView));
                }
                break;

            case PROFITBIRD: //Canon powerup existing in the original game
                powerUpMovement = new MovementImpl(new Pair<>(screenSizeX / 4, screenSizeY - screenSizeY / 8), new Pair<>(0.0, 0.0),new Pair<>(0.0, 0.0), new Pair<>(0.0, 0.0),List.of(MovementChangers.GRAVITY, MovementChangers.BOUNDS));
                powerUpHitbox = new HitboxImpl(powerUpMovement.getCurrentPosition(), new Pair<>(screenSizeX / 7, screenSizeY / 6), powerUpMovement.getRotation().get1());
                powerUpModel.add(new ProfitBird(powerUpMovement, powerUpHitbox));

                images = imageLoader(12, "sprites/entities/powerups/profitbird/profitbird_");

                powerUpView = new PowerUpView(images);

                powerUp.add(new GenericController<PowerUp,PowerUpView>(powerUpModel.get(0), powerUpView));

                break;
            case DUKEFISHRON: //Non canon powerup. An easter egg for Terraria players ;)
                powerUpMovement = new MovementImpl(new Pair<>(screenSizeX / 4, screenSizeY - screenSizeY / 8), new Pair<>(0.0, 10.0),new Pair<>(0.0, 0.0), new Pair<>(0.0, 0.0),List.of(MovementChangers.BOUNCING));
                powerUpHitbox = new HitboxImpl(powerUpMovement.getCurrentPosition(), new Pair<>(screenSizeX / 4, screenSizeY / 3), powerUpMovement.getRotation().get1());
                powerUpModel.add(new DukeFishron(powerUpMovement, powerUpHitbox));

                images = imageLoader(12, "sprites/entities/powerups/dukefishron/dukefishron_");

                powerUpView = new PowerUpView(images);

                powerUp.add(new GenericController<PowerUp,PowerUpView>(powerUpModel.get(0), powerUpView));
                break;

            default:
            throw new IllegalArgumentException("EntityGenerator could not generate the powerup");
        }
        return powerUp;
    }

    @Override
    public GenericController<PickUp, PickUpView> generateVehiclePickUp(final PowerUpType spawnedVehicle) {
        final Double screenSizeX = GameInfo.getInstance().getScreenWidth();
        final Double screenSizeY = GameInfo.getInstance().getScreenHeight();

        final Movement pickUpMovement = new MovementImpl(new Pair<>(screenSizeX, screenSizeY/2), new Pair<>(-3.0, 0.0),new Pair<>(0.0, 0.0), new Pair<>(0.0, 0.0),List.of(MovementChangers.GRAVITY));
        final Hitbox pickUpHitbox = new HitboxImpl(pickUpMovement.getCurrentPosition(), new Pair<>(screenSizeX / 15, screenSizeY / 9), pickUpMovement.getRotation().get1());
        final PickUp pickUpModel = new VehiclePickUp(spawnedVehicle, pickUpMovement, pickUpHitbox);
        
        final Image[] images = imageLoader(21, "sprites/entities/pickups/vehiclepickup/vehiclepickup_");

        PickUpView pickUpView = new PickUpView(images);
        
        return new GenericController<PickUp,PickUpView>(pickUpModel, pickUpView);
    }

    private Image[] imageLoader(final Integer numberOfImages, final String pathName) {
        Image[] images = new Image[numberOfImages];
        for (int i = 0; i < numberOfImages; i++) {
            images[i] = new Image(getClass().getClassLoader()
            .getResource(pathName + (i + 1) + ".png")
            .toExternalForm());
        }
        return images;
    } 
}
