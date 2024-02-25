package it.unibo.jetpackjoyride.core.entities.pickups.api;

import static it.unibo.jetpackjoyride.core.entities.entity.api.Entity.EntityType.PICKUP;

import it.unibo.jetpackjoyride.core.entities.entity.api.AbstractEntity;
import it.unibo.jetpackjoyride.core.hitbox.api.Hitbox;
import it.unibo.jetpackjoyride.core.movement.Movement;

/**
 * The {@link AbstractPickUp} class extends {@link AbstractEntity}, so inherits
 * the common behaviour of all entities. The class also defines the methods and 
 * behaviour all {@link PickUp} entities.
 * 
 * @author gabriel.stira@studio.unibo.it
 */
public abstract class AbstractPickUp extends AbstractEntity implements PickUp {
    /*
     * The type of the pickup.
     */
    private final PickUpType pickUpType;

    /**
     * Constructor used to create a new AbstractPickUp.
     *
     * @param entityType   The type of the pickup.
     * @param newMovement  The movement characteristics of the pickup.
     * @param hitbox       The collision characteristics of the pickup.
     */
    public AbstractPickUp(final PickUpType entityType, final Movement newMovement, final Hitbox hitbox) {
        super(PICKUP, newMovement, hitbox);
        this.pickUpType = entityType;
        this.setEntityStatus(EntityStatus.ACTIVE);
    }
    
    /**
     * Gets the type of pickup.
     *
     * @return The pickup type.
     */
    @Override
    public PickUpType getPickUpType() {
        return this.pickUpType;
    }
}
