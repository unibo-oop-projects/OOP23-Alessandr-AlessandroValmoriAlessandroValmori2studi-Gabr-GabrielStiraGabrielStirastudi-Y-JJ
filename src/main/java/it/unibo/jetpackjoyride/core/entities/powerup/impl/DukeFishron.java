package it.unibo.jetpackjoyride.core.entities.powerup.impl;

import it.unibo.jetpackjoyride.core.entities.entity.api.Entity;
import it.unibo.jetpackjoyride.core.entities.powerup.api.AbstractPowerUp;
import it.unibo.jetpackjoyride.core.entities.powerup.api.PowerUp;
import it.unibo.jetpackjoyride.core.hitbox.api.Hitbox;
import it.unibo.jetpackjoyride.core.movement.Movement;

/**
 * The {@link DukeFishron} class defines one of the powerups implemented
 * in the game. Since it extends {@link AbstractPowerUp}, it inherits all
 * methods and behaviours of {@link Entity} and {@link PowerUp}.
 * This powerup has a really fast movement but every few seconds it enters
 * a rage mode which make it even faster and difficult to control.
 *
 * @author gabriel.stira@studio.unibo.it
 */
public class DukeFishron extends AbstractPowerUp {
    /**
     * Defines how long it takes for the powerup to enter the rage mode.
     */
    private static final Integer DEFAULT_RAGE_TIMER = 100;
    /**
     * Defines how long the rage mode lasts.
     */
    private static final Integer DEFAULT_RAGE_DURATION = 100;
    /**
     * Defines how much the speed is changed during the rage mode as a multiplier.
     */
    private static final Double RAGE_SPEED_MODIFIER = 2.0;

    /**
     * Defines a variable used to avoid a continuous spacebar pressing which would
     * cause the
     * movement to be inverted at each frame.
     */
    private Boolean intervalBewteenJumps;

    /**
     * Defines a counter used to keep trace of the rage status.
     */
    private Integer timerForRage;

    /**
     * Constructor used to create an instance of the class DukeFishron.
     * 
     * @param movement The movement characteristics of the dukefishron powerup.
     * @param hitbox   The collision characteristics of the dukefishron powerup.
     */
    public DukeFishron(final Movement movement, final Hitbox hitbox) {
        super(PowerUpType.DUKEFISHRON, movement, hitbox);
        this.intervalBewteenJumps = true;
        this.timerForRage = 0;
        this.setPerformingAction(PerformingAction.ASCENDING);
    }

    /**
     * Updates status and movement of the dukefishron entity based on its position
     * and if the spacebar is pressed.
     * 
     * @param isSpaceBarPressed Is used to invert the Y speed of the movement.
     */
    @Override
    public void updateStatus(final boolean isSpaceBarPressed) {
        switch (this.getPerformingAction()) {
            case ASCENDING:
            case DESCENDING:
                if (isSpaceBarPressed && this.intervalBewteenJumps) {
                    this.setEntityMovement(new Movement.Builder()
                            .addNewAcceleration(this.getEntityMovement().getAcceleration())
                            .addNewSpeed(this.getEntityMovement().getSpeed().get1(),
                                    -this.getEntityMovement().getSpeed().get2())
                            .addNewPosition(this.getEntityMovement().getPosition())
                            .addNewRotation(
                                    (this.getEntityMovement().getSpeed().get2() > 0 ? -1.0 : 1.0)
                                            * (Math.abs(this.getEntityMovement().getRotation().get1())),
                                    this.getEntityMovement().getRotation().get2())
                            .addNewMovementChangers(this.getEntityMovement().getMovementChangers()).build());
                    this.intervalBewteenJumps = false;
                }
                break;
            default:
                break;
        }

        if (!this.intervalBewteenJumps && !isSpaceBarPressed) {
            this.intervalBewteenJumps = true;
        }

        this.timerForRage++;

        if (this.timerForRage.equals(DEFAULT_RAGE_TIMER)
                || this.timerForRage.equals(DEFAULT_RAGE_TIMER + DEFAULT_RAGE_DURATION)) {
            this.setPerformingAction(this.timerForRage.equals(DEFAULT_RAGE_TIMER) ? PerformingAction.DESCENDING
                    : PerformingAction.ASCENDING);
            if (this.timerForRage.equals(DEFAULT_RAGE_TIMER + DEFAULT_RAGE_DURATION)) {
                this.timerForRage = 0;
            }
            this.setEntityMovement(new Movement.Builder()
                    .addNewAcceleration(this.getEntityMovement().getAcceleration())
                    .addNewSpeed(this.getEntityMovement().getSpeed().get1(),
                            this.getEntityMovement().getSpeed().get2()
                                    * (this.timerForRage.equals(DEFAULT_RAGE_TIMER) ? RAGE_SPEED_MODIFIER
                                            : 1 / RAGE_SPEED_MODIFIER))
                    .addNewPosition(this.getEntityMovement().getPosition())
                    .addNewRotation(this.getEntityMovement().getRotation())
                    .addNewMovementChangers(this.getEntityMovement().getMovementChangers()).build());
        }
    }
}
