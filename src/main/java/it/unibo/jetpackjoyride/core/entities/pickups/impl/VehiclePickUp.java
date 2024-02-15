package it.unibo.jetpackjoyride.core.entities.pickups.impl;

import it.unibo.jetpackjoyride.core.entities.pickups.api.AbstractPickUp;
import it.unibo.jetpackjoyride.core.hitbox.api.Hitbox;
import it.unibo.jetpackjoyride.core.movement.Movement;

public class VehiclePickUp extends AbstractPickUp {

    public VehiclePickUp(Movement movement, Hitbox hitbox) {
        super(PickUpType.VEHICLE, movement, hitbox);
        this.entityStatus = EntityStatus.ACTIVE;
    }

	@Override
	protected void updateStatus() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'updateStatus'");
	}
    
}
