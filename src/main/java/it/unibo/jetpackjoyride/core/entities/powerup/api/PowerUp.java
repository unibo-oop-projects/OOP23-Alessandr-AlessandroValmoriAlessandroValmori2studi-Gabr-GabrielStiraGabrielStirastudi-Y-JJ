package it.unibo.jetpackjoyride.core.entities.powerup.api;

import it.unibo.jetpackjoyride.core.entities.entity.api.Entity;
import it.unibo.jetpackjoyride.core.entities.powerup.impl.DukeFishron;
import it.unibo.jetpackjoyride.core.entities.powerup.impl.LilStomper;
import it.unibo.jetpackjoyride.core.entities.powerup.impl.MrCuddles;
import it.unibo.jetpackjoyride.core.entities.powerup.impl.ProfitBird;
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
        /**
         * Defines the {@link MrCuddles} powerup.
         */
        MRCUDDLES, 
        /**
         * Defines the {@link LilStomper} powerup.
         */
        LILSTOMPER, 
        /**
         * Defines the {@link ProfitBird} powerup.
         */
        PROFITBIRD, 
        /**
         * Defines the {@link DukeFishron} powerup.
         */
        DUKEFISHRON
    }

    /**
     * Defines the actions which describe more accurately the status of the powerup.
     */
    enum PerformingAction {
        /**
         * Is the action the powerup is performing when touching the ground.
         */
        WALKING,
        /**
         * Is the action the powerup is performing when jumping.
         */
        JUMPING,
        /**
         * Is the action the powerup is performing when gaining altitude.
         */
        ASCENDING,
        /**
         * Is the action the powerup is performing when losing altitude.
         */ 
        DESCENDING,
        /**
         * Is the action the powerup is performing when losing altitude,
         * but slower than DESCENDING, because the player controls it.
         */
        GLIDING,
        /**
         * Is the action the powerup is performing when is about to land.
         */
        LANDING
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
     * Sets the {@link PerformingAction} of the power-up.
     * @param performingAction the {@link PerformingAction} to be set.
     */
    void setPerformingAction(PerformingAction performingAction);
}
