package it.unibo.jetpackjoyride.core.entities.pickups.impl;

import java.util.List;

import it.unibo.jetpackjoyride.core.entities.pickups.api.AbstractPickUp;
import it.unibo.jetpackjoyride.core.hitbox.api.Hitbox;
import it.unibo.jetpackjoyride.core.movement.Movement;
import it.unibo.jetpackjoyride.core.movement.MovementImpl;
import it.unibo.jetpackjoyride.utilities.GameInfo;
import it.unibo.jetpackjoyride.utilities.Pair;

public class VehiclePickUp extends AbstractPickUp {

	public enum VehicleSpawn {
		MRCUDDLE, LILSTOMPER, PROFITBIRD, DUKEFISHRON
	}

	private static final Integer ANIMATIONDURATION = 150;
    private Integer animationTimer;
	private Integer switchWave;
	private final VehicleSpawn vehicleSpawn;

    public VehiclePickUp(VehicleSpawn vehicleSpawn, Movement movement, Hitbox hitbox) {
        super(PickUpType.VEHICLE, movement, hitbox);
        this.entityStatus = EntityStatus.ACTIVE;
		this.animationTimer = 0;
		this.switchWave = 0;
		this.vehicleSpawn = vehicleSpawn;
    }

	@Override
	protected void updateStatus(boolean isSpaceBarPressed) {
		final Double screenX = GameInfo.getInstance().getScreenWidth();
		final Double screenY = GameInfo.getInstance().getScreenHeight();
        if (this.movement.getCurrentPosition().get1() < -screenX / 8) {
            this.entityStatus = EntityStatus.INACTIVE;
        }

		if(this.entityStatus.equals(EntityStatus.DEACTIVATED)) {
			this.animationTimer++;
			Movement newMovement = new MovementImpl(new Pair<>(screenX/2, screenY/2), new Pair<>(0.0,0.0), new Pair<>(0.0,0.0) , new Pair<>(0.0,0.0), List.of());
			this.movement = newMovement;
		}
		if(this.animationTimer.equals(ANIMATIONDURATION)) {
			this.entityStatus = EntityStatus.INACTIVE;
		}

		this.switchWave++;
		if(this.switchWave == 40 && this.entityStatus.equals(EntityStatus.ACTIVE)) {
			this.movement.setAcceleration(new Pair<>(this.movement.getAcceleration().get1(), -this.movement.getAcceleration().get2()));
			this.switchWave = -40;
		}
	}

	public VehicleSpawn getVehicleSpawn() {
		return this.vehicleSpawn;
	}

}
