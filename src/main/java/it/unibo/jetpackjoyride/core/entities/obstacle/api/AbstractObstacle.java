package it.unibo.jetpackjoyride.core.entities.obstacle.api;

import static it.unibo.jetpackjoyride.core.entities.entity.api.Entity.EntityType.*;
import static it.unibo.jetpackjoyride.core.entities.obstacle.api.Obstacle.ObstacleStatus.*;

import it.unibo.jetpackjoyride.core.entities.entity.api.AbstractEntity;
import it.unibo.jetpackjoyride.core.hitbox.api.Hitbox;
import it.unibo.jetpackjoyride.core.movement.Movement;

public abstract class AbstractObstacle extends AbstractEntity implements Obstacle {

    public final ObstacleType obstacleType;
    public ObstacleStatus obstacleStatus;

    public AbstractObstacle(ObstacleType obstacleType, Movement movement, Hitbox hitbox) {
        super(OBSTACLE, movement, hitbox);
        this.obstacleType = obstacleType;
        this.obstacleStatus = INACTIVE;
    }

    public ObstacleType getObstacleType() {
        return this.obstacleType;
    }

    public ObstacleStatus getObstacleStatus() {
        return this.obstacleStatus;
    }

    public void changeObstacleStatus(ObstacleStatus newObstacleStatus) {
        this.obstacleStatus = newObstacleStatus;
    }

    public void changeObstacleMovement(Movement newMovement) {
        this.movement = newMovement;
    }

    public void update() {
        this.lifetime++;
        this.movement.update();
        this.hitbox.updateHitbox(this.movement.getCurrentPosition(), this.movement.getRotation().get2());
        this.updateStatus();
    }

    protected abstract void updateStatus();
}
