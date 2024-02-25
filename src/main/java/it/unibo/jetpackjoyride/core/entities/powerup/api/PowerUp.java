package it.unibo.jetpackjoyride.core.entities.powerup.api;

import it.unibo.jetpackjoyride.core.entities.entity.api.Entity;
import it.unibo.jetpackjoyride.core.movement.Movement;

/**
 * The {@link PowerUp} interface defines the methods used by the PowerUp
 * entities in the game. Currently, four types of powerups are implemented.
 * PowerUp are entities which give a second chance to the player if an obstacle
 * is hit. Additionally, they change the {@link Movement} and the sprite of the
 * player.
 *
 * @author gabriel.stira@studio.unibo.it
 */
public interface PowerUp extends Entity {
    /**
     * Defines the type of powerups implemented in the game.
     */
    enum PowerUpType {
        MRCUDDLES, LILSTOMPER, PROFITBIRD, DUKEFISHRON
    }

    /**
     * Defines the actions which describe more accurately the status of the powerup.
     */
    enum PerformingAction {
        WALKING, JUMPING, ASCENDING, DESCENDING, GLIDING, LANDING
    }

    /**
     * Gets the type of the powerup.
     * @return The type of the powerup.
     */
    PowerUpType getPowerUpType();

    /**
     * Gets the action the powerup is currently performing.
     * @return The action the powerup is currently performing.
     */
    PerformingAction getPerformingAction();

    /**
     * Sets the {@link PerformingAction} of the power-up
     * @param performingAction the {@link PerformingAction} to be set
     */
    void setPerformingAction(final PerformingAction performingAction);
}
