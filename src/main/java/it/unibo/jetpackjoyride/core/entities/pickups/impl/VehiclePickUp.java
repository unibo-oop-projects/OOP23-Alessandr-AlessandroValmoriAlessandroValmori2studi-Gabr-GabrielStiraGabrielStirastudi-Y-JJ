package it.unibo.jetpackjoyride.core.entities.pickups.impl;

import java.util.List;

import it.unibo.jetpackjoyride.core.entities.pickups.api.AbstractPickUp;
import it.unibo.jetpackjoyride.core.entities.powerup.api.PowerUp.PowerUpType;
import it.unibo.jetpackjoyride.core.hitbox.api.Hitbox;
import it.unibo.jetpackjoyride.core.movement.Movement;
import it.unibo.jetpackjoyride.utilities.MovementChangers;
import it.unibo.jetpackjoyride.utilities.Pair;

public class VehiclePickUp extends AbstractPickUp {
	private static final Double OUTOFBOUNDSSX = -100.0;
	private static final Integer ANIMATIONDURATION = 100;
	private static final Pair<Double,Double> BANNERSPAWNINGCOORDINATES = new Pair<>(640.0, 360.0);
	private static final Integer SWITCHDIRECTIONDURATION = 20;


    private Integer animationTimer;
	private Integer switchWave;
	private PowerUpType vehicleSpawn;

    public VehiclePickUp(final Movement movement, final Hitbox hitbox) {
        super(PickUpType.VEHICLE, movement, hitbox);
        this.entityStatus = EntityStatus.ACTIVE;
		this.animationTimer = 0;
		this.switchWave = 0;
		this.vehicleSpawn = PowerUpType.DUKEFISHRON;
    }

	@Override
	protected void updateStatus(final boolean isSpaceBarPressed) {
        if (this.movement.getPosition().get1() < OUTOFBOUNDSSX) {
            this.entityStatus = EntityStatus.INACTIVE;
        }

		if(this.entityStatus.equals(EntityStatus.DEACTIVATED)) {
			this.animationTimer++;
			this.movement = new Movement.Builder().setPosition(BANNERSPAWNINGCOORDINATES).build();
		}
		if(this.animationTimer.equals(ANIMATIONDURATION)) {
			this.entityStatus = EntityStatus.INACTIVE;
		}

		this.switchWave++;
		if(this.switchWave == SWITCHDIRECTIONDURATION && this.entityStatus.equals(EntityStatus.ACTIVE)) {
			this.movement = new Movement.Builder()
					.setAcceleration(this.movement.getAcceleration())
					.setSpeed(this.movement.getSpeed())
					.setPosition(this.movement.getPosition())
					.setRotation(this.movement.getRotation())
					.setMovementChangers(this.movement.getMovementChangers().contains(MovementChangers.GRAVITY) ? List.of(MovementChangers.INVERSEGRAVITY) : List.of(MovementChangers.GRAVITY)).build();

			this.switchWave = -SWITCHDIRECTIONDURATION;
		}
	}

	public void setVehicleSpawn(final PowerUpType newVehicleType) {
		this.vehicleSpawn = newVehicleType;
	}

	public PowerUpType getVehicleSpawn() {
		return this.vehicleSpawn;
	}
}
