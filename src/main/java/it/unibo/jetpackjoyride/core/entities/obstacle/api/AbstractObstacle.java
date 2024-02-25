package it.unibo.jetpackjoyride.core.entities.obstacle.api;

import it.unibo.jetpackjoyride.core.entities.entity.api.AbstractEntity;
import it.unibo.jetpackjoyride.core.hitbox.api.Hitbox;
import it.unibo.jetpackjoyride.core.movement.Movement;

/**
 * The {@link AbstractObstacle} class extends {@link AbstractEntity}, so inherits
 * the common behaviour of all entities. The class also defines the methods and 
 * behaviour all {@link Obstacle} entities.
 * 
 * @author gabriel.stira@studio.unibo.it
 */
public abstract class AbstractObstacle extends AbstractEntity implements Obstacle {
    /*
     * The type of the obstacle
     */
    private final ObstacleType obstacleType;

    /**
     * Constructor used to create a new AbstractObstacle.
     *
     * @param obstacleType The type of the obstacle.
     * @param newMovement  The movement characteristics of the obstacle.
     * @param hitbox       The collision characteristics of the obstacle.
     */
    public AbstractObstacle(final ObstacleType obstacleType, final Movement newMovement, final Hitbox hitbox) {
        super(EntityType.OBSTACLE, newMovement, hitbox);
        this.obstacleType = obstacleType;
        this.entityStatus = EntityStatus.INACTIVE;
    }

    @Override
    public ObstacleType getObstacleType() {
        return this.obstacleType;
    }
}
