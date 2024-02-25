package it.unibo.jetpackjoyride.core.entities.entity.api;

import it.unibo.jetpackjoyride.core.movement.Movement;
import java.util.List;
import it.unibo.jetpackjoyride.core.entities.barry.api.Barry;
import it.unibo.jetpackjoyride.core.entities.obstacle.api.Obstacle;
import it.unibo.jetpackjoyride.core.entities.obstacle.api.Obstacle.ObstacleType;
import it.unibo.jetpackjoyride.core.entities.pickups.api.PickUp;
import it.unibo.jetpackjoyride.core.entities.pickups.api.PickUp.PickUpType;
import it.unibo.jetpackjoyride.core.entities.powerup.api.PowerUp;
import it.unibo.jetpackjoyride.core.entities.powerup.api.PowerUp.PowerUpType;

/**
 * The {@link EntityModelGenerator} interface defines the methods used for generating all entities 
 * in the game. Currently, Barry, obstacles, powerups and pickups are entities and therefore can
 * be generated by these methods.
 *
 * @author gabriel.stira@studio.unibo.it
 */
public interface EntityModelGenerator {
    /**
     * Generates an obstacle with the specified type and movement behavior.
     *
     * @param obstacleType    The type of obstacle to generate.
     * @param obstacleMovement The movement behavior of the obstacle. Since obstacle's type of movement is neither random 
     * nor fixated (can be dinamically chosen), a Movement has to be provided.
     * @return An obstacle which has the type and the movement specified.
     */
    Obstacle generateObstacle(ObstacleType obstacleType, Movement obstacleMovement); 

    /**
     * Generates a list of power-ups of the specified type.
     * This decision of generating not one but more powerups has been made 
     * to allow the generation of a powerup which can be considered as many
     * (see MrCuddle powerup)
     *
     * @param powerUpType The type of power-up to generate.
     * @return A list of power-ups which have the type specified.
     */
    List<PowerUp> generatePowerUp(PowerUpType powerUpType);

     /**
     * Generates a pickup of the specified type.
     *
     * @param pickUpType The type of pickup to generate.
     * @return The generated pickup.
     */
    PickUp generatePickUp(PickUpType pickUpType);

    /**
     * Generates Barry.
     *
     * @return The generated Barry entity.
     */
    Barry generateBarry();

        /* Since one of the goals was to create a clone of the game as similar as 
     * possible to the original one, the choice of returning one or more entities
     * was made based on the fact that in the original game, power-ups can be
     * considered as a series of entities, each with autonomous or differentiated
     * movement and collision, even though all controlled by the player which
     * 'unify' them. The same does not apply to obstacles and pickups, which in 
     * all cases are single entities.
     */
}
