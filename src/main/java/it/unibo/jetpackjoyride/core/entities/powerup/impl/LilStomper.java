package it.unibo.jetpackjoyride.core.entities.powerup.impl;

import it.unibo.jetpackjoyride.core.entities.entity.api.Entity;
import it.unibo.jetpackjoyride.core.entities.powerup.api.AbstractPowerUp;
import it.unibo.jetpackjoyride.core.entities.powerup.api.PowerUp;
import it.unibo.jetpackjoyride.core.hitbox.api.Hitbox;
import it.unibo.jetpackjoyride.core.movement.Movement;

/**
 * The {@link LilStomper} class defines one of the powerups implemented
 * in the game. Since it extends {@link AbstractPowerUp}, it inherits all
 * methods and behaviours of {@link Entity} and {@link PowerUp}.
 * This powerup allows the player to jump and glide, differently time to time
 * based on how long the space bar has been pressed.
 *
 * @author gabriel.stira@studio.unibo.it
 */
public final class LilStomper extends AbstractPowerUp {
    /**
     * Defines how fast the smallest jump is.
     */
    private static final Double BASE_JUMP_HEIGHT_SPEED = 10.0;
    /**
     * Defines how much speed every tick adds to the total spee dof the jump.
     */
    private static final Double TICK_JUMP_HEIGHT_SPEED = 0.5;
    /**
     * Defines the max number of ticks a jump can have.
     */
    private static final Integer MAX_TICKS_FOR_JUMP = 15;
    /**
     * Defines the number of ticks the landing lasts.
     */
    private static final Integer RECOVER_TICKS_AFTER_LANDING = 20;
    /**
     * Defines how fast the gliding is.
     */
    private static final Double DESCENDING_BASE_SPEED = 4.0;
    /**
     * Defines the Y coordinate the landing status will begin.
     */
    private static final Double LANDING_HEIGHT = 600.0;

    /**
     * Defines a counter used to calculate the height of the jump.
     */
    private Integer loadJump;

    /**
     * Constructor used to create an instance of the class LilStomper.
     * 
     * @param movement The movement characteristics of the lilstomper powerup.
     * @param hitbox   The collision characteristics of the lilstomper powerup.
     */
    public LilStomper(final Movement movement, final Hitbox hitbox) {
        super(PowerUpType.LILSTOMPER, movement, hitbox);
        this.loadJump = 0;
        this.setPerformingAction(PerformingAction.DESCENDING);
    }

    /**
     * Updates status and movement of the lilstomper entity based on its position
     * and if the spacebar is pressed.
     * 
     * @param isSpaceBarPressed Is used to calculate the height of the jump and if
     *                          the player wants to glide.
     */
    @Override
    public void updateStatus(final boolean isSpaceBarPressed) {
        switch (this.getPerformingAction()) {
            case WALKING:
                if (isSpaceBarPressed) {
                    this.setPerformingAction(PerformingAction.JUMPING);
                }
                break;
            case JUMPING:
                if (isSpaceBarPressed) {
                    this.loadJump++;
                }
                if (this.loadJump == MAX_TICKS_FOR_JUMP || !isSpaceBarPressed) {

                    this.setEntityMovement(new Movement.Builder()
                            .setAcceleration(this.getEntityMovement().getAcceleration())
                            .setSpeed(this.getEntityMovement().getSpeed().get1(),
                                    (-this.loadJump * TICK_JUMP_HEIGHT_SPEED - BASE_JUMP_HEIGHT_SPEED))
                            .setPosition(this.getEntityMovement().getPosition())
                            .setRotation(this.getEntityMovement().getRotation())
                            .setMovementChangers(this.getEntityMovement().getMovementChangers()).build());

                    this.loadJump = 0;
                    this.setPerformingAction(PerformingAction.ASCENDING);
                }
                break;
            case ASCENDING:

                if (this.getEntityMovement().getSpeed().get2() >= 0) {
                    this.setPerformingAction(PerformingAction.DESCENDING);
                }
                break;
            case DESCENDING:
                if (isSpaceBarPressed && this.getEntityMovement().getSpeed().get2() > DESCENDING_BASE_SPEED) {
                    this.setPerformingAction(PerformingAction.GLIDING);
                }

                if (this.getEntityMovement().getPosition().get2() > LANDING_HEIGHT) {
                    this.setPerformingAction(PerformingAction.LANDING);
                    this.loadJump = -RECOVER_TICKS_AFTER_LANDING;
                }
                break;
            case GLIDING:
                if (isSpaceBarPressed) {
                    this.setEntityMovement(new Movement.Builder()
                            .setAcceleration(this.getEntityMovement().getAcceleration())
                            .setSpeed(this.getEntityMovement().getSpeed().get1(), DESCENDING_BASE_SPEED)
                            .setPosition(this.getEntityMovement().getPosition())
                            .setRotation(this.getEntityMovement().getRotation())
                            .setMovementChangers(this.getEntityMovement().getMovementChangers()).build());
                } else {
                    this.setPerformingAction(PerformingAction.DESCENDING);
                }

                if (this.getEntityMovement().getPosition().get2() > LANDING_HEIGHT) {
                    this.setPerformingAction(PerformingAction.LANDING);
                    this.loadJump = -RECOVER_TICKS_AFTER_LANDING;
                }
                break;
            case LANDING:
                this.loadJump++;
                if (this.loadJump == 0) {
                    this.setPerformingAction(PerformingAction.WALKING);
                }
                break;
            default:
                break;
        }
    }
}
