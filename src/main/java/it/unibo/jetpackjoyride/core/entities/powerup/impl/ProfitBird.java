package it.unibo.jetpackjoyride.core.entities.powerup.impl;

import it.unibo.jetpackjoyride.core.entities.entity.api.Entity;
import it.unibo.jetpackjoyride.core.entities.powerup.api.AbstractPowerUp;
import it.unibo.jetpackjoyride.core.entities.powerup.api.PowerUp;
import it.unibo.jetpackjoyride.core.hitbox.api.Hitbox;
import it.unibo.jetpackjoyride.core.movement.Movement;

/**
 * The {@link ProfitBird} class defines one of the powerups implemented
 * in the game. Since it extends {@link AbstractPowerUp}, it inherits all
 * methods and behaviours of {@link Entity} and {@link PowerUp}.
 * This powerup is really small and so can easily avoid most obstacles, but
 * lacks a bit in mobility and so struggles a bit with relocating fast in other 
 * points of the map.
 *
 * @author gabriel.stira@studio.unibo.it
 */
public class ProfitBird extends AbstractPowerUp{
    /**
     * Defines the base speed of each jump.
     */
    private final static Double BASE_JUMP_SPEED = 8.0;
    /**
     *  Defines the Y coordinate the landing status will begin.
     */
    private static final Double LANDING_HEIGHT = 630.0;
     /**
     * Defines a variable used to avoid a continuous spacebar pressing which would cause the
     * powerup to jump indefinitely.
     */
    private Boolean intervalBewteenJumps;

    /**
     * Constructor used to create an instance of the class ProfitBird.
     * 
     * @param movement The movement characteristics of the profitbird powerup.
     * @param hitbox The collision characteristics of the profitbird powerup.
     */
    public ProfitBird(final Movement movement, final Hitbox hitbox) {
        super(PowerUpType.PROFITBIRD, movement, hitbox);
        this.intervalBewteenJumps = true;
        this.performingAction = PerformingAction.DESCENDING;
    }

    /**
     * Updates status and movement of the profitbird entity based on its position and if the spacebar is pressed.
     * @param isSpaceBarPressed Is used to increase instantly the Y speed, with the intention of simulating a jump
     */
    @Override
    public void updateStatus(final boolean isSpaceBarPressed) {
        switch (this.performingAction) {
            case WALKING:
                if(isSpaceBarPressed && this.intervalBewteenJumps) {
                    this.performingAction = PerformingAction.JUMPING;
                    this.intervalBewteenJumps = false;
                }
                break;
            case JUMPING:
                this.performingAction = PerformingAction.ASCENDING;
                break;
            case ASCENDING:
                if (this.movement.getSpeed().get2() >= 0) {
                    this.performingAction = PerformingAction.DESCENDING;
                }
                if(isSpaceBarPressed && this.intervalBewteenJumps) {
                    this.performingAction = PerformingAction.JUMPING;
                    this.intervalBewteenJumps = false;
                }
                break;
            case DESCENDING:
                if(isSpaceBarPressed && this.intervalBewteenJumps) {
                    this.performingAction = PerformingAction.JUMPING;
                    this.intervalBewteenJumps = false;
                }
                if(this.movement.getPosition().get2() >= LANDING_HEIGHT) {
                    this.performingAction = PerformingAction.WALKING;
                }
                break;
            default:
                break;
        }

        if(!this.intervalBewteenJumps && !isSpaceBarPressed) {
            this.intervalBewteenJumps = true;
        }

        this.movement = new Movement.Builder()
			.setAcceleration(this.movement.getAcceleration())
			.setSpeed(this.movement.getSpeed().get1(), this.performingAction.equals(PerformingAction.JUMPING) ? -BASE_JUMP_SPEED : this.movement.getSpeed().get2())
			.setPosition(this.movement.getPosition())
			.setRotation(this.movement.getSpeed().get2(),0.0)
			.setMovementChangers(this.movement.getMovementChangers()).build();
    }
}
