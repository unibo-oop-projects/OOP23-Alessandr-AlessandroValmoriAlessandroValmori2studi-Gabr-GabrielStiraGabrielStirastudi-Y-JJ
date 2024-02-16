package it.unibo.jetpackjoyride.core.handler.entity;

import it.unibo.jetpackjoyride.core.entities.pickups.api.PickUp;
import it.unibo.jetpackjoyride.core.entities.pickups.api.PickUp.PickUpType;
import it.unibo.jetpackjoyride.core.entities.powerup.api.PowerUp.PowerUpType;
import it.unibo.jetpackjoyride.core.handler.obstacle.ObstacleHandler;
import it.unibo.jetpackjoyride.core.handler.pickup.PickUpHandler;
import it.unibo.jetpackjoyride.core.handler.powerup.PowerUpHandler;
import it.unibo.jetpackjoyride.core.hitbox.api.Hitbox;
import javafx.scene.Group;

public class EntityHandler {
    private ObstacleHandler obstacleHandler;
    private PowerUpHandler powerUpHandler;
    private PickUpHandler pickUpHandler;

    private Event eventHappening;
    private boolean isUsingPowerUp;
    private Integer counter;

    public enum Event {
        BARRYHIT, POWERUPHIT, NONE, POWERUPSPAWNED, PICKUPPICKEDUP
    }


    public void initialize() {
        this.obstacleHandler = new ObstacleHandler();
        this.powerUpHandler = new PowerUpHandler();
        this.pickUpHandler = new PickUpHandler();

        this.obstacleHandler.initialize();
        this.isUsingPowerUp = false;
        this.counter = 0;
    }

    public Event update(final Group entityGroup, final Hitbox playerHitbox, final boolean isSpaceBarPressed) {
        this.eventHappening = Event.NONE;

        if(!this.isUsingPowerUp && this.counter % 500 == 0) {//Every 500m spawns a pickUp if Barry is not using a powerUp
            this.spawnPickUp(PickUpType.VEHICLE);
        }

        if(this.obstacleHandler.update(entityGroup, playerHitbox)) {
            this.eventHappening = isUsingPowerUp ? Event.POWERUPHIT : Event.BARRYHIT;
        }

        this.powerUpHandler.update(entityGroup, isSpaceBarPressed);

        if(this.pickUpHandler.update(entityGroup, playerHitbox)) {
            this.isUsingPowerUp = true;
            this.eventHappening = Event.PICKUPPICKEDUP;
            PickUp pickUpPickedUp = this.pickUpHandler.getAllPickUps().get(0).getEntityModel();
            switch (pickUpPickedUp.getPickUpType()) {
                case VEHICLE:
                    this.spawnPowerUp(PowerUpType.LILSTOMPER);
                    break;
                default:
                    break;
            }
        }

        this.counter++;
        return this.eventHappening;
    }

    public void spawnPickUp(final PickUpType pickUpType) {
        this.pickUpHandler.spawnPickUp(pickUpType);
    }

    public void spawnPowerUp(final PowerUpType powerUpType) {
        this.powerUpHandler.spawnPowerUp(powerUpType);
    }

    public void stop() {
        this.obstacleHandler.over();
    }
}
