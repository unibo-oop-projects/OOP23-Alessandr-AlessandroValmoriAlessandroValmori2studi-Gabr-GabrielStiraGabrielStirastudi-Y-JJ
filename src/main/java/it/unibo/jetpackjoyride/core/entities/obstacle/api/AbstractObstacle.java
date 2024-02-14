package it.unibo.jetpackjoyride.core.entities.obstacle.api;

import static it.unibo.jetpackjoyride.core.entities.entity.api.Entity.EntityType.*;
import static it.unibo.jetpackjoyride.core.entities.obstacle.api.Obstacle.ObstacleStatus.*;

import it.unibo.jetpackjoyride.core.entities.entity.api.AbstractEntity;
import it.unibo.jetpackjoyride.core.hitbox.api.Hitbox;
import it.unibo.jetpackjoyride.core.movement.Movement;

public abstract class AbstractObstacle extends AbstractEntity implements Obstacle {

    public final ObstacleType obstacleType;
    public ObstacleStatus obstacleStatus;

    public AbstractObstacle(final ObstacleType obstacleType, final Movement movement, final Hitbox hitbox) {
        super(OBSTACLE, movement, hitbox);
        this.obstacleType = obstacleType;
        this.obstacleStatus = INACTIVE;
    }

    @Override
    public ObstacleType getObstacleType() {
        return this.obstacleType;
    }

    @Override
    public ObstacleStatus getObstacleStatus() {
        return this.obstacleStatus;
    }

    @Override
    public void changeObstacleStatus(final ObstacleStatus newObstacleStatus) {
        this.obstacleStatus = newObstacleStatus;
    }

    @Override
    public void update() {
        this.lifetime++;
        this.movement.update();
        if (this.obstacleStatus.equals(ACTIVE)) {
            this.hitbox.updateHitbox(this.movement.getCurrentPosition(), this.movement.getRotation().get2());
        }
        this.updateStatus();
    }

    protected abstract void updateStatus();
}
