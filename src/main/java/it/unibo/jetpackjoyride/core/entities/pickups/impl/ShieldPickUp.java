package it.unibo.jetpackjoyride.core.entities.pickups.impl;

import it.unibo.jetpackjoyride.core.entities.entity.api.Entity;
import it.unibo.jetpackjoyride.core.entities.pickups.api.AbstractPickUp;
import it.unibo.jetpackjoyride.core.entities.pickups.api.PickUp;
import it.unibo.jetpackjoyride.core.hitbox.api.Hitbox;
import it.unibo.jetpackjoyride.core.movement.Movement;

/**
 * The {@link ShieldPickUp} class defines one of the pickups implemented
 * in the game. Since it extends {@link AbstractPickUp}, it inherits all
 * methods and behaviours of {@link Entity} and {@link PickUp}.
 * The shield pickup gives the player a second chance if hit by an obstacle.
 *  
 * @author gabriel.stira@studio.unibo.it
 */
public class ShieldPickUp extends AbstractPickUp {
    /**
     *  Defines the X coordinate at which the shield pickup status will be set to INACTIVE.
     */
	private static final Double OUT_OF_BOUNDS_SX = -100.0;

    /**
     * Constructor used to create an instance of the class Shield pickup.
     * @param movement The movement characteristics of the shield pickup.
     * @param hitbox The collision characteristics of the shield pickup.
     */
    public ShieldPickUp(final Movement movement, final Hitbox hitbox) {
        super(PickUpType.SHIELD, movement, hitbox);
        this.entityStatus = EntityStatus.ACTIVE;
    }

    /**
     * Updates the status of the shield entity based on its position.
     * @param isSpaceBarPressed Is ignored by this entity.
     */
	@Override
	protected void updateStatus(final boolean isSpaceBarPressed) {
        if (this.movement.getPosition().get1() < OUT_OF_BOUNDS_SX || this.entityStatus.equals(EntityStatus.DEACTIVATED)) {
            this.entityStatus = EntityStatus.INACTIVE;
        }
	}

}
