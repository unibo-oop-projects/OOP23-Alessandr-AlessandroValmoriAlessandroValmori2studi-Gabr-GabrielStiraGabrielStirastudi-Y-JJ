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
import it.unibo.jetpackjoyride.core.entities.powerup.impl.MrCuddles;

/**
 * The {@link EntityModelGenerator} interface defines the methods used for generating all {@link Entity} 
 * in the game. Currently, {@link Barry}, {@link Obstacle}, {@link PowerUp} and {@link PickUp} are 
 * {@link Entity} and therefore can be generated easily by these methods in a factory which implements this
 * interface.
 *
 * @author gabriel.stira@studio.unibo.it
 */
public interface EntityModelGenerator {
    /**
     * Generates an {@link Obstacle} with the specified type and movement behavior.
     *
     * @param obstacleType    The {@link ObstacleType} of the {@link Obstacle} to generate.
     * @param obstacleMovement The {@link Movement} behavior of the {@link Obstacle}. 
     * Since it does not make sense to have obstacle's movement random or fixated, 
     * a {@link Movement} has to be provided.
     * @return An {@link Obstacle} which has the type and the movement specified.
     */
    Obstacle generateObstacle(ObstacleType obstacleType, Movement obstacleMovement); 

    /**
     * Generates a list of {@link PowerUp} of the specified type.
     * This decision of generating not one but more powerups has been made 
     * to allow the generation of a powerup which can be considered as many
     * (see {@link MrCuddles} powerup)
     *
     * @param powerUpType The {@link PowerUpType} of power-up to generate.
     * @return A list of {@link PowerUp} which have the type specified.
     */
    List<PowerUp> generatePowerUp(PowerUpType powerUpType);

     /**
     * Generates a {@link PickUp} of the specified {@link PickUpType}.
     *
     * @param pickUpType The type of pickup to generate.
     * @return The generated {@link PickUp}.
     */
    PickUp generatePickUp(PickUpType pickUpType);

    /**
     * Generates {@link Barry}.
     *
     * @return The generated {@link Barry} entity.
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
