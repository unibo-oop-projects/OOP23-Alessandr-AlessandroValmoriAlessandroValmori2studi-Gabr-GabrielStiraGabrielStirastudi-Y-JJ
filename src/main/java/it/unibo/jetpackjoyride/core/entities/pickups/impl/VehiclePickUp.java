package it.unibo.jetpackjoyride.core.entities.pickups.impl;

import java.util.List;

import it.unibo.jetpackjoyride.core.entities.entity.api.Entity;
import it.unibo.jetpackjoyride.core.entities.pickups.api.AbstractPickUp;
import it.unibo.jetpackjoyride.core.entities.pickups.api.PickUp;
import it.unibo.jetpackjoyride.core.entities.powerup.api.PowerUp.PowerUpType;
import it.unibo.jetpackjoyride.core.hitbox.api.Hitbox;
import it.unibo.jetpackjoyride.core.movement.Movement;
import it.unibo.jetpackjoyride.utilities.MovementChangers;
import it.unibo.jetpackjoyride.utilities.Pair;

/**
 * The {@link VehiclePickUp} class defines one of the pickups implemented
 * in the game. Since it extends {@link AbstractPickUp}, it inherits all
 * methods and behaviours of {@link Entity} and {@link PickUp}.
 * The vehicle pickup when collected generates a random powerup from those
 * which have been unlocked in the shop.
 * 
 * @author gabriel.stira@studio.unibo.it
 */
public class VehiclePickUp extends AbstractPickUp {
	/**
	 * Defines the X coordinate at which the vehicle pickup status will be set to
	 * INACTIVE.
	 */
	private static final Double OUT_OF_BOUNDS_SX = -100.0;
	/**
	 * Defines the lenght of the banner animation which start when the pickup is
	 * collected.
	 */
	private static final Integer ANIMATION_DURATION = 100;
	/**
	 * Defines the coordinates at which the banner will be generated.
	 */
	private static final Pair<Double, Double> BANNER_SPAWNING_COORDINATES = new Pair<>(640.0, 360.0);
	/*
	 * Defines the Y range of the ondulatory movement of the pickup.
	 */
	private static final Integer SWITCH_DIRECTION_DURATION = 20;

	/**
	 * Defines a counter used for the banner animation of the pickup.
	 */
	private Integer animationTimer;
	/**
	 * Defines a counter used for the ondulatory movement of the pickup.
	 */
	private Integer switchWave;
	/**
	 * Defines what powerup is generated if this pickup is collected.
	 */
	private PowerUpType vehicleSpawn;

	/**
	 * Constructor used to create an instance of the class vehicle pickuo.
	 * 
	 * @param movement The movement characteristics of the vehicle pickup.
	 * @param hitbox   The collision characteristics of the vehicle pickup.
	 */
	public VehiclePickUp(final Movement movement, final Hitbox hitbox) {
		super(PickUpType.VEHICLE, movement, hitbox);
		this.setEntityStatus(EntityStatus.ACTIVE);
		this.animationTimer = 0;
		this.switchWave = 0;
		this.vehicleSpawn = PowerUpType.DUKEFISHRON;
	}

	/**
	 * Updates the status of the vehiclepickup entity based on its position.
	 * 
	 * @param isSpaceBarPressed Is ignored by this entity.
	 */
	@Override
	protected void updateStatus(final boolean isSpaceBarPressed) {
		if (this.getEntityMovement().getPosition().get1() < OUT_OF_BOUNDS_SX) {
			this.setEntityStatus(EntityStatus.INACTIVE);
		}

		if (this.getEntityStatus().equals(EntityStatus.DEACTIVATED)) {
			if (this.animationTimer.equals(0)) {
				this.setEntityMovement(new Movement.Builder().setPosition(BANNER_SPAWNING_COORDINATES).build());
			}
			this.animationTimer++;
		}
		if (this.animationTimer.equals(ANIMATION_DURATION)) {
			this.setEntityStatus(EntityStatus.INACTIVE);
		}

		this.switchWave++;
		if (this.switchWave.equals(SWITCH_DIRECTION_DURATION) && this.getEntityStatus().equals(EntityStatus.ACTIVE)) {
			this.setEntityMovement(new Movement.Builder()
					.setAcceleration(this.getEntityMovement().getAcceleration())
					.setSpeed(this.getEntityMovement().getSpeed())
					.setPosition(this.getEntityMovement().getPosition())
					.setRotation(this.getEntityMovement().getRotation())
					.setMovementChangers(
							this.getEntityMovement().getMovementChangers().contains(MovementChangers.GRAVITY)
									? List.of(MovementChangers.INVERSEGRAVITY)
									: List.of(MovementChangers.GRAVITY))
					.build());

			this.switchWave = -SWITCH_DIRECTION_DURATION;
		}
	}

	/**
	 * Sets the {@link PowerUpType} this vehicle pickup will generate if collected.
	 * 
	 * @param newVehicleType
	 */
	public void setVehicleSpawn(final PowerUpType newVehicleType) {
		this.vehicleSpawn = newVehicleType;
	}

	/**
	 * Gets the {@link PowerUpType} this vehicle pickup will generate if collected.
	 * 
	 * @return The type of powerup of the generated powerup.
	 */
	public PowerUpType getVehicleSpawn() {
		return this.vehicleSpawn;
	}
}
