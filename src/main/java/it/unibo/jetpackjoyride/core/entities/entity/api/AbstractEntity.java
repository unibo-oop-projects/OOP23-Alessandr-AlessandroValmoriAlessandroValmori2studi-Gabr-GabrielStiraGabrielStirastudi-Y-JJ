package it.unibo.jetpackjoyride.core.entities.entity.api;

import it.unibo.jetpackjoyride.core.hitbox.api.Hitbox;
import it.unibo.jetpackjoyride.core.movement.Movement;

public abstract class AbstractEntity implements Entity {
    public final EntityType entityType;
    public Movement movement;
    public Hitbox hitbox;
    public int lifetime;

    public AbstractEntity(EntityType entityType, Movement movement, Hitbox hitbox) {
        this.entityType = entityType;
        this.movement = movement;
        this.hitbox = hitbox;
        this.lifetime = 0;
    }

    @Override
    public Movement getEntityMovement() {
        return this.movement;
    }

    @Override
    public EntityType getEntityType() {
        return this.entityType;
    }

    @Override
    public Hitbox getHitbox() {
        return this.hitbox;
    }

    @Override
    public int getLifetime() {
        return this.lifetime;
    }
}
