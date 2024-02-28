package it.unibo.jetpackjoyride.core.entities.entity.impl;

import it.unibo.jetpackjoyride.core.entities.barry.api.Barry;
import it.unibo.jetpackjoyride.core.entities.barry.impl.BarryImpl;
import it.unibo.jetpackjoyride.core.entities.entity.api.EntityFactory;
import it.unibo.jetpackjoyride.core.entities.obstacle.api.Obstacle;
import it.unibo.jetpackjoyride.core.entities.obstacle.api.Obstacle.ObstacleType;
import it.unibo.jetpackjoyride.core.entities.pickups.api.PickUp;
import it.unibo.jetpackjoyride.core.entities.pickups.api.PickUp.PickUpType;
import it.unibo.jetpackjoyride.core.entities.powerup.api.PowerUp;
import it.unibo.jetpackjoyride.core.entities.powerup.api.PowerUp.PowerUpType;
import it.unibo.jetpackjoyride.core.hitbox.api.Hitbox;
import it.unibo.jetpackjoyride.core.hitbox.impl.HitboxImpl;
import it.unibo.jetpackjoyride.core.movement.Movement;
import it.unibo.jetpackjoyride.utilities.MovementChangers;
import it.unibo.jetpackjoyride.utilities.Pair;
import java.util.List;

/**
 * The {@link EntityFactoryImpl} factory implements the methods of {@link EntityFactory} and therefore
 * allows the generation of all {@link Entity} in the game.
 *
 * @author gabriel.stira@studio.unibo.it
 */
public final class EntityFactoryImpl implements EntityFactory {
    /**
     * Define the initial position of Barry.
     */
    private static final Pair<Double, Double> BARRY_STARTING_POS = new Pair<>(200.0, 630.0);

    /**
     * Define the dimensions of barry's hitbox.
     */
    private static final Pair<Double, Double> BARRY_HITBOX_DIMENSIONS = new Pair<>(75.0, 100.0);

    @Override
    public Obstacle generateObstacle(final ObstacleType obstacleType, final Movement obstacleMovement) {
        return new ObstacleFactory().generateObstacle(obstacleType, obstacleMovement);
    }

    @Override
    public List<PowerUp> generatePowerUp(final PowerUpType powerUpType) {
        return new PowerUpFactory().generatePowerUp(powerUpType);
    }

    @Override
    public PickUp generatePickUp(final PickUpType pickUpType) {
        return new PickUpFactory().generatePickUp(pickUpType);
    }

    @Override
    public Barry generateBarry() {
        final Movement barryMovement = new Movement.Builder().addNewPosition(BARRY_STARTING_POS)
                .addNewMovementChangers(List.of(MovementChangers.GRAVITY, MovementChangers.BOUNDS)).build();
        final Hitbox barryHitbox = new HitboxImpl(barryMovement.getPosition(),
                BARRY_HITBOX_DIMENSIONS,
                0.0);
        return new BarryImpl(barryMovement, barryHitbox);
    }
}
