package entities.entity.api;

import hitbox.Hitbox;
import movement.impl.MovementImpl;
import utilities.*;

public abstract class AbstractEntity implements Entity {
    public final EntityType entityType;
    public MovementImpl movement;
    public final Hitbox hitbox;

    public AbstractEntity(final EntityType entityType, final MovementImpl movement, final Hitbox hitbox) {
        this.entityType = entityType;
        this.movement = movement;
        this.hitbox = hitbox;
    }

    public EntityType getEntityType() {
        return this.entityType;
    }

    public Pair<Double, Double> getStartingPosition() {
        return this.movement.getStartingPosition();
    }

    public Pair<Double, Double> getCurrentPosition() {
        return this.movement.getCurrentPosition();
    }

    public void setCurrentPosition(final Pair<Double, Double> newCurrentPosition) {
        this.movement.currentPosition = newCurrentPosition;
    }

    public Hitbox getHitbox() {
        return this.hitbox;
    }
}
