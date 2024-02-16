package it.unibo.jetpackjoyride.core.handler.entity;

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

    public enum Event {
        OBSTACLEHIT, NONE
    }


    public void initialize() {
        obstacleHandler = new ObstacleHandler();
        powerUpHandler = new PowerUpHandler();
        pickUpHandler = new PickUpHandler();

        obstacleHandler.initialize();
    }

    public Event update(final Group entityGroup, final Hitbox playerHitbox, final boolean isSpaceBarPressed) {

        obstacleHandler.update(entityGroup, playerHitbox);
        if(pickUpHandler.update(entityGroup, playerHitbox)) {
            powerUpHandler.spawnPowerUp(PowerUpType.LILSTOMPER);
        }
        powerUpHandler.update(entityGroup, isSpaceBarPressed);
        return Event.NONE;
    }

    public void stop() {
        obstacleHandler.over();
    }
}
