package it.unibo.jetpackjoyride.core.entities.entity.api;

import it.unibo.jetpackjoyride.core.hitbox.api.Hitbox;
import it.unibo.jetpackjoyride.core.movement.Movement;

/**
 * The {@link AbstractEntity} class implements all the methods of {@link Entity} and defines the 
 * common proprierties and behaviours of all entities.
 * 
 * @author gabriel.stira@studio.unibo.it
 */

public abstract class AbstractEntity implements Entity {
    /**
     * Type of the entity.
     */
    protected final EntityType entityType;

    /**
     * Current status of the entity.
     */
    protected EntityStatus entityStatus;

    /**
     * Movement characteristics of the entity.
     */
    protected Movement movement;

    /**
     * Collision of the entity.
     */
    protected Hitbox hitbox;

    /**
     * The age (in terms of update method calls) of the entity.
     */
    protected Integer lifetime;

     /**
     * Constructs an AbstractEntity with the specified parameters.
     * By default, the entity status is set to INACTIVE and its age is 0.
     *
     * @param entityType The type of the entity.
     * @param movement   The movement behavior of the entity.
     * @param hitbox     The hitbox of the entity.
     */
    public AbstractEntity(final EntityType entityType, final Movement newMovement, final Hitbox hitbox) {
        this.entityType = entityType;
        this.movement = newMovement;
        this.hitbox = hitbox;
        this.lifetime = 0;
        this.entityStatus = EntityStatus.INACTIVE; // Initially, by default, entity's status is set to INACTIVE
    }

    /**
     * Gets the movement characteristics of the entity.
     *
     * @return The movement characteristics of the entity.
     */
    @Override
    public Movement getEntityMovement() {
        return this.movement;
    }

    /**
     * Gets the type of the entity.
     *
     * @return The type of the entity.
     */
    @Override
    public EntityType getEntityType() {
        return this.entityType;
    }

     /**
     * Sets the status of the entity.
     * If the status is set to ACTIVE, the collision gets updated
     *
     * @param entityStatus The status of the entity.
     */
    @Override
    public void setEntityStatus(final EntityStatus entityStatus) {
        this.entityStatus = entityStatus;
    }

    /**
     * Sets the movement of the entity.
     *
     * @param newMovement The new moveemnt of the entity.
     */
    @Override
    public void setEntityMovement(final Movement newMovement) {
        this.movement = new Movement.Builder()
                .setPosition(newMovement.getPosition())
                .setSpeed(newMovement.getSpeed())
                .setAcceleration(newMovement.getAcceleration())
                .setRotation(newMovement.getRotation())
                .setMovementChangers(newMovement.getMovementChangers())
                .build();
    }

    /**
     * Gets the status of the entity.
     *
     * @return The status of the entity.
     */
    @Override
    public EntityStatus getEntityStatus() {
        return this.entityStatus;
    }

    /**
     * Gets the collision of the entity.
     *
     * @return The collision of the entity.
     */
    @Override
    public Hitbox getHitbox() {
        return this.hitbox;
    }

    /**
     * Gets the number of times the entity has been updated.
     *
     * @return The age of the entity.
     */
    @Override
    public Integer getLifetime() {
        return this.lifetime;
    }

    /**
     * Updates the entity's lifetime, status, movement and collision.
     *
     * @param isSpaceBarPressed Is used by entities which change their behaviour if the space bar is pressed.
     */
    @Override
    public void update(final boolean isSpaceBarPressed) {
        this.lifetime++; //Increases the lifetime of the entity, which is, the number of time the entity has been updated

        this.updateStatus(isSpaceBarPressed); //Updates the status of the entity and the modifiers to its movement, view, etc...

        this.movement = this.movement.update(); //Updates the movement of the entity (position, speed, rotation, etc...)
        
        this.hitbox.updateHitbox(this.movement.getPosition(), this.movement.getRotation().get1()); //Updates the hitbox of the entity 
        
    }

    /**
     * Updates the status of the entity. It is implemented differently depending 
     * on the type of entity.
     * 
     * @param isSpaceBarPressed Is used by entities which change their behaviour if the space bar is pressed.
     */
    protected abstract void updateStatus(boolean isSpaceBarPressed);
}
