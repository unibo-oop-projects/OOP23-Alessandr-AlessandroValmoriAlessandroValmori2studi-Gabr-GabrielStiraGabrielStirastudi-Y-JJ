package it.unibo.jetpackjoyride.core.entities.obstacle.api;

import it.unibo.jetpackjoyride.core.entities.entity.api.Entity;

/**
 * The {@link Obstacle} interface defines the methods used by the Obstacle
 * entities in the game. Currently, three types of obstacles are implemented.
 * Obstacles are entities which can cause the player to lose the game if hit.
 *
 * @author gabriel.stira@studio.unibo.it
 */
public interface Obstacle extends Entity {
    /**
     * Defines the type of obstacles implemented in the game.
     */
    enum ObstacleType {
        /**
         * The {@link Missile} obstacle
         */
        MISSILE, 

        /**
         * The {@link Zapper} obstacle
         */
        ZAPPER,

        /**
         * The {@link Laser} obstacle
         */
        LASER
    }

    /**
     * Gets the type of this obstacle.
     *
     * @return The type of this obstacle.
     */
    ObstacleType getObstacleType();
}
