package it.unibo.jetpackjoyride.core.entities.powerup.impl;

import it.unibo.jetpackjoyride.core.entities.powerup.api.AbstractPowerUp;
import it.unibo.jetpackjoyride.core.hitbox.api.Hitbox;
import it.unibo.jetpackjoyride.core.movement.Movement;

public final class LilStomper extends AbstractPowerUp {
    private static final Double BASEJUMPHEIGHTSPEED = 10.0;
    private static final Double TICKJUMPHEITGHSPEED = 0.5;
    private static final Integer MAXTICKSFORJUMP = 15;
    private static final Integer RECOVERTICKSAFTERLANDING = 20;
    private static final Double DESCENDINGBASESPEED = 2.0;
    private static final Double LANDINGHEIGHT = 600.0;

    private Integer loadJump;

    public LilStomper(final Movement movement, final Hitbox hitbox) {
        super(PowerUpType.LILSTOMPER, movement, hitbox);
        this.loadJump = 0;
        this.performingAction = PerformingAction.DESCENDING;
    }

    @Override
    public void updateStatus(final boolean isSpaceBarPressed) {
        switch (this.performingAction) {
            case WALKING:
                if (isSpaceBarPressed) {
                    this.performingAction = PerformingAction.JUMPING;
                }
                break;
            case JUMPING:
                if (isSpaceBarPressed) {
                    this.loadJump++;
                }
                if (this.loadJump == MAXTICKSFORJUMP || !isSpaceBarPressed) {

                    this.movement = new Movement.Builder()
					    .setAcceleration(this.movement.getAcceleration())
					    .setSpeed(this.movement.getSpeed().get1(), (-this.loadJump * TICKJUMPHEITGHSPEED - BASEJUMPHEIGHTSPEED))
					    .setPosition(this.movement.getRealPosition())
					    .setRotation(this.movement.getRotation())
					    .setMovementChangers(this.movement.getMovementChangers()).build();

                    this.loadJump = 0;
                    this.performingAction = PerformingAction.ASCENDING;
                }
                break;
            case ASCENDING:

                if (this.movement.getSpeed().get2() >= 0) {
                    this.performingAction = PerformingAction.DESCENDING;
                }
                break;
            case DESCENDING:
                if (isSpaceBarPressed && this.movement.getSpeed().get2() > DESCENDINGBASESPEED) {
                    this.performingAction = PerformingAction.GLIDING;
                }

                if (this.movement.getRealPosition().get2() > LANDINGHEIGHT) {
                    this.performingAction = PerformingAction.LANDING;
                    this.loadJump = -RECOVERTICKSAFTERLANDING;
                }
                break;
            case GLIDING:
                if (isSpaceBarPressed) {
                    this.movement = new Movement.Builder()
					    .setAcceleration(this.movement.getAcceleration())
					    .setSpeed(this.movement.getSpeed().get1(), DESCENDINGBASESPEED)
					    .setPosition(this.movement.getRealPosition())
					    .setRotation(this.movement.getRotation())
					    .setMovementChangers(this.movement.getMovementChangers()).build();
                } else {
                    this.performingAction = PerformingAction.DESCENDING;
                }

                if (this.movement.getRealPosition().get2() > LANDINGHEIGHT) {
                    this.performingAction = PerformingAction.LANDING;
                    this.loadJump = -RECOVERTICKSAFTERLANDING;
                }
                break;
            case LANDING:
                this.loadJump++;
                if (this.loadJump == 0) {
                    this.performingAction = PerformingAction.WALKING;
                }
                break;
            default:
                break;
        }
    }
}
