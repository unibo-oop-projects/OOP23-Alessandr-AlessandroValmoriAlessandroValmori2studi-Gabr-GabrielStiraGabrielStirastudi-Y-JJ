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
        this.hitbox.updateHitbox(this.movement.getCurrentPosition(), this.movement.getRotation().get1() + this.movement.getRotation().get2());
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
    public Integer getLifetime() {
        return this.lifetime;
    }

    @Override
    public void update(final boolean isSpaceBarPressed) {
        this.lifetime++; //Increases the lifetime of the entity, which is, the number of time the entity has been updated

        this.updateStatus(isSpaceBarPressed); //Updates the status of the entity and the modifiers to its movement, view, etc...

        this.movement.update(); //Updates the movement of the entity (position, speed, rotation, etc...)

        if (this.entityStatus.equals(EntityStatus.ACTIVE)) { //Updates the hitbox of the entity if the entity is ACTIVE
            this.hitbox.updateHitbox(this.movement.getCurrentPosition(), this.movement.getRotation().get1() + this.movement.getRotation().get2());
        }//(Could have done it even without the if statement, but updating the hitbox when the entity is not active is useless)
    }

    protected abstract void updateStatus(boolean isSpaceBarPressed);
}
