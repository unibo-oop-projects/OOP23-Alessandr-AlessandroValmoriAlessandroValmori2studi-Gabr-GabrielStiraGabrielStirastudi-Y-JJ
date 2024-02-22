package it.unibo.jetpackjoyride.core.entities.pickups.impl;

import it.unibo.jetpackjoyride.core.entities.pickups.api.AbstractPickUp;
import it.unibo.jetpackjoyride.core.hitbox.api.Hitbox;
import it.unibo.jetpackjoyride.core.movement.Movement;

public class ShieldPickUp extends AbstractPickUp {
	private static final Double OUTOFBOUNDSSX = -100.0;

    public ShieldPickUp(final Movement movement, final Hitbox hitbox) {
        super(PickUpType.SHIELD, movement, hitbox);
        this.entityStatus = EntityStatus.ACTIVE;
    }

	@Override
	protected void updateStatus(final boolean isSpaceBarPressed) {
        if (this.movement.getRealPosition().get1() < OUTOFBOUNDSSX) {
            this.entityStatus = EntityStatus.INACTIVE;
        }

	}

}
