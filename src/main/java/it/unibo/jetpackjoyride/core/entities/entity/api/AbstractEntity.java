package it.unibo.jetpackjoyride.core.entities.entity.api;

import it.unibo.jetpackjoyride.core.hitbox.Hitbox;
import it.unibo.jetpackjoyride.core.movement.Movement;

public abstract class AbstractEntity implements Entity {
    public final EntityType entityType;
    public Movement movement;
    public final Hitbox hitbox;
    private int lifetime;

    public AbstractEntity(EntityType entityType, Movement movement, Hitbox hitbox) {
        this.entityType = entityType;
        this.movement = movement;
        this.hitbox = hitbox;
        this.lifetime = 0;
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

    public void update() {
        this.movement.update();
        this.hitbox.updateHitbox(this.movement.getCurrentPosition(), this.movement.getRotation().get2());
        this.lifetime++;
    }

    public int getLifetime() {
        return this.lifetime;
    }
}
