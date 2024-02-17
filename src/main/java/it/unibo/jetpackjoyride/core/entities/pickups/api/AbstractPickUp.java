package it.unibo.jetpackjoyride.core.entities.pickups.api;

import static it.unibo.jetpackjoyride.core.entities.entity.api.Entity.EntityType.PICKUP;

import it.unibo.jetpackjoyride.core.entities.entity.api.AbstractEntity;
import it.unibo.jetpackjoyride.core.hitbox.api.Hitbox;
import it.unibo.jetpackjoyride.core.movement.Movement;

public abstract class AbstractPickUp extends AbstractEntity implements PickUp {

    private final PickUpType pickUpType;

    public AbstractPickUp(final PickUpType entityType, final Movement movement, final Hitbox hitbox) {
        super(PICKUP, movement, hitbox);
        this.pickUpType = entityType;
        this.entityStatus = EntityStatus.ACTIVE;
    }
    
    @Override
    public PickUpType getPickUpType() {
        return this.pickUpType;
    }
}
