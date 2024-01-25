package core.entities.entity.api;

import core.hitbox.Hitbox;
import core.movement.Movement;

public abstract class AbstractEntity implements Entity {
    public final EntityType entityType;
    public Movement movement;
    public final Hitbox hitbox;

    public AbstractEntity(EntityType entityType, Movement movement, Hitbox hitbox) {
        this.entityType = entityType;
        this.movement = movement;
        this.hitbox = hitbox;
    }

    public Movement getEntityMovement() {
        return this.movement;
    }

    public EntityType getEntityType() {
        return this.entityType;
    }

    public Hitbox getHitbox() {
        return this.hitbox;
    }
}
