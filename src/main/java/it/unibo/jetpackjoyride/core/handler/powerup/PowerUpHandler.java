package it.unibo.jetpackjoyride.core.handler.powerup;

import java.util.ArrayList;
import java.util.List;

import it.unibo.jetpackjoyride.core.entities.entity.impl.EntityModelGeneratorImpl;
import it.unibo.jetpackjoyride.core.entities.powerup.api.PowerUp;
import it.unibo.jetpackjoyride.core.entities.powerup.api.PowerUp.PowerUpType;
import it.unibo.jetpackjoyride.core.entities.entity.api.EntityModelGenerator;
import it.unibo.jetpackjoyride.core.entities.entity.api.Entity.EntityStatus;
import java.util.Collections;

 /**
  * The {@link PowerUpHandler} class is one of the main handlers of the game, 
  * which are classes used to manage the various entities in the game.
  * This class in particular deals with the generation, updating and interaction
  * managing of all {@link PowerUp} in the game.
  *
  * @author gabriel.stira@studio.unibo.it
  */
public final class PowerUpHandler {
    /**
     * Is used to store all non INACTIVE powerups in game.
     */
    private final List<PowerUp> listOfPowerUp;
    /**
     * Is used to generate the powerups.
     */
    private final EntityModelGenerator entityModelGenerator;

    /**
     * Constructor used to create an instance of PowerUpHandler and initialize it.
     */
    public PowerUpHandler() {
        this.listOfPowerUp = new ArrayList<>();
        this.entityModelGenerator = new EntityModelGeneratorImpl();
    }

    /**
     * Updates all powerups in the game. If a powerup has a INACTIVE entityStatus,
     * it is removed from the set of all powerup. 
     * 
     * @param isSpaceBarPressed The value used by powerups to consistently update their movement.
     */
    public void update(final boolean isSpaceBarPressed) {
        final var iterator = listOfPowerUp.iterator();
        while (iterator.hasNext()) {
            final var model = iterator.next();

            model.update(isSpaceBarPressed);

            if (model.getEntityStatus().equals(EntityStatus.INACTIVE)) {
                iterator.remove();
            }
        }
    }

    /**
     * Defines a method which generates a powerups which corresponds to the provided {@link PowerUpType}.
     * 
     * @param powerUpType
     */
    public void spawnPowerUp(final PowerUpType powerUpType) {
        final List<PowerUp> powerup = this.entityModelGenerator.generatePowerUp(powerUpType);
        this.listOfPowerUp.addAll(powerup);
    }

    /**
     * Gets the list of all non INACTVE powerups in game.
     * @return The list of all non INACTIVE powerups.
     */
    public List<PowerUp> getAllPowerUps() {
        return Collections.unmodifiableList(this.listOfPowerUp);
    }

    /**
     * Set the powerup status to INACTIVE. Is used by {@link EntityHandler} when a powerup 
     * collides with an obstacle.
     */
    public void destroyAllPowerUps() {
        this.listOfPowerUp.forEach(model -> model.setEntityStatus(EntityStatus.INACTIVE));
    }
}
