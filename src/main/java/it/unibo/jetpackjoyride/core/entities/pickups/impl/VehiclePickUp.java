package it.unibo.jetpackjoyride.core.entities.pickups.impl;

import it.unibo.jetpackjoyride.core.entities.pickups.api.AbstractPickUp;
import it.unibo.jetpackjoyride.core.hitbox.api.Hitbox;
import it.unibo.jetpackjoyride.core.movement.Movement;
import it.unibo.jetpackjoyride.utilities.GameInfo;

public class VehiclePickUp extends AbstractPickUp {
	private static final Integer ANIMATIONDURATION = 80;
    private int animationTimer;

    public VehiclePickUp(Movement movement, Hitbox hitbox) {
        super(PickUpType.VEHICLE, movement, hitbox);
        this.entityStatus = EntityStatus.ACTIVE;
		this.animationTimer = 0;
    }

	@Override
	protected void updateStatus(boolean isSpaceBarPressed) {
		final Double outOfBoundsX = GameInfo.getInstance().getScreenWidth();
        if (this.movement.getCurrentPosition().get1() < -outOfBoundsX / 8) {
            this.entityStatus = EntityStatus.INACTIVE;
        }

		if(this.entityStatus.equals(EntityStatus.DEACTIVATED)) {
			this.animationTimer++;
		}
		if(this.animationTimer == ANIMATIONDURATION) {
			this.entityStatus = EntityStatus.INACTIVE;
		}
	}

}
