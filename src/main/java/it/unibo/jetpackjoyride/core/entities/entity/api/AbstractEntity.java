package it.unibo.jetpackjoyride.core.entities.entity.api;

import it.unibo.jetpackjoyride.core.hitbox.api.Hitbox;
import it.unibo.jetpackjoyride.core.movement.Movement;

/**
 * The {@link AbstractEntity} class implements all the methods of {@link Entity}
 * and defines the
 * common properties and behaviours of all entities.
 * 
 * @author gabriel.stira@studio.unibo.it
 */

public abstract class AbstractEntity implements Entity {
    /**
     * Type of the entity.
     */
    private final EntityType entityType;

    /**
     * Current status of the entity.
     */
    private EntityStatus entityStatus;

    /**
     * Movement characteristics of the entity.
     */
    private Movement movement;

    /**
     * Collision of the entity.
     */
    private final Hitbox hitbox;

    /**
     * The age (in terms of update method calls) of the entity.
     */
    private Integer lifetime;

    /**
     * Constructs an AbstractEntity with the specified parameters.
     * By default, the entity status is set to INACTIVE and its age is 0.
     *
     * @param entityType The type of the entity.
     * @param newMovement   The movement behavior of the entity.
     * @param hitbox     The hitbox of the entity.
     */
    public AbstractEntity(final EntityType entityType, final Movement newMovement, final Hitbox hitbox) {
        this.entityType = entityType;
        this.movement = newMovement;
        this.hitbox = hitbox;
        this.lifetime = 0;
        this.entityStatus = EntityStatus.INACTIVE; // Initially, by default, entity's status is set to INACTIVE
    }

    @Override
    public final Movement getEntityMovement() {
        return this.movement;
    }

    @Override
    public final EntityType getEntityType() {
        return this.entityType;
    }

    @Override
    public final void setEntityStatus(final EntityStatus entityStatus) {
        this.entityStatus = entityStatus;
        if (this.entityStatus.equals(EntityStatus.ACTIVE)) {
            this.hitbox.updateHitbox(this.movement.getPosition(), this.movement.getRotation().get1());
        }
    }

    @Override
    public final void setEntityMovement(final Movement newMovement) {
        this.movement = new Movement.Builder()
                .addNewPosition(newMovement.getPosition())
                .addNewSpeed(newMovement.getSpeed())
                .addNewAcceleration(newMovement.getAcceleration())
                .addNewRotation(newMovement.getRotation())
                .addNewMovementChangers(newMovement.getMovementChangers())
                .build();
    }

    @Override
    public final EntityStatus getEntityStatus() {
        return this.entityStatus;
    }

    @Override
    public final Hitbox getHitbox() {
        return this.hitbox;
    }

    @Override
    public final Integer getLifetime() {
        return this.lifetime;
    }

    @Override
    public final void setLifetime(final Integer lifetime) {
        this.lifetime = lifetime;
    }

    @Override
    public final void update(final boolean isSpaceBarPressed) {
        this.lifetime++; // Increases the lifetime of the entity, which is, the number of time the entity
                         // has been updated

        this.updateStatus(isSpaceBarPressed); // Updates the status of the entity and the modifiers to its movement,
                                              // view, etc...
        this.movement = this.movement.update(); // Updates the movement of the entity (position, speed, rotation,
                                                // etc...)
        if (this.entityStatus.equals(EntityStatus.ACTIVE)) { // Updates the hitbox of the entity if the entity is ACTIVE
            this.hitbox.updateHitbox(this.movement.getPosition(), this.movement.getRotation().get1());
        } // (Could have done it even without the if statement, but updating the hitbox
          // when the entity is not active is useless)
    }

    /**
     * Updates the status of the entity. It is implemented differently depending
     * on the type of entity.
     * 
     * @param isSpaceBarPressed Is used by entities which change their behaviour if
     *                          the space bar is pressed.
     */
    protected abstract void updateStatus(boolean isSpaceBarPressed);
}
