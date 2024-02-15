package it.unibo.jetpackjoyride.core.entities.entity.api;

import it.unibo.jetpackjoyride.core.hitbox.api.Hitbox;
import it.unibo.jetpackjoyride.core.movement.Movement;

public abstract class AbstractEntity implements Entity {
    protected final EntityType entityType;
    protected EntityStatus entityStatus;
    protected Movement movement;
    protected Hitbox hitbox;
    protected Integer lifetime;

    public AbstractEntity(final EntityType entityType, final Movement movement, final Hitbox hitbox) {
        this.entityStatus = EntityStatus.INACTIVE;
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
    public void setEntityStatus(final EntityStatus entityStatus) {
        this.entityStatus = entityStatus;
    }

    @Override
    public EntityStatus getEntityStatus() {
        return this.entityStatus;
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
