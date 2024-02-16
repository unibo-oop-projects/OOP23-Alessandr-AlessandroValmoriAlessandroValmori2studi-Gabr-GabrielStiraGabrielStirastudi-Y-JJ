package it.unibo.jetpackjoyride.core.entities.powerup.impl;

import it.unibo.jetpackjoyride.core.entities.powerup.api.AbstractPowerUp;
import it.unibo.jetpackjoyride.core.hitbox.api.Hitbox;
import it.unibo.jetpackjoyride.core.movement.Movement;
import it.unibo.jetpackjoyride.utilities.GameInfo;
import it.unibo.jetpackjoyride.utilities.Pair;

public final class LilStomper extends AbstractPowerUp {
    private static final Double BASEJUMPHEIGHTSPEED = 5.0;
    private static final Double TICKJUMPHEITGHSPEED = 0.5;
    private static final Integer MAXTICKSFORJUMP = 10;
    private static final Integer RECOVERTICKSAFTERLANDING = 20;
    private static final Double DESCENDINGBASESPEED = 2.0;

    private Integer loadJump;

    public LilStomper(final Movement movement, final Hitbox hitbox) {
        super(PowerUpType.LILSTOMPER, movement, hitbox);
        this.loadJump = 0;
        this.performingAction = PerformingAction.WALKING;
    }

    @Override
    public void updateStatus(final boolean isSpaceBarPressed) {
        final GameInfo infoResolution = GameInfo.getInstance();
        final Double screenSizeY = infoResolution.getScreenHeight();

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
                    this.movement.setSpeed(new Pair<>(this.movement.getSpeed().get1(),
                            (-this.loadJump * TICKJUMPHEITGHSPEED - BASEJUMPHEIGHTSPEED) * screenSizeY
                                    / infoResolution.getDefaultHeight()));
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
                if (isSpaceBarPressed && this.movement.getSpeed().get2() > (DESCENDINGBASESPEED) * screenSizeY / infoResolution.getDefaultHeight()) {
                    this.performingAction = PerformingAction.GLIDING;
                }

                if (this.movement.getCurrentPosition().get2() > screenSizeY - screenSizeY / 8) {
                    this.performingAction = PerformingAction.LANDING;
                    this.loadJump = -RECOVERTICKSAFTERLANDING;
                }
                break;
            case GLIDING:
                if (isSpaceBarPressed) {
                    this.movement.setSpeed(new Pair<>(this.movement.getSpeed().get1(),
                            (DESCENDINGBASESPEED) * screenSizeY / infoResolution.getDefaultHeight()));
                } else {
                    this.performingAction = PerformingAction.DESCENDING;
                }

                if (this.movement.getCurrentPosition().get2() > screenSizeY - screenSizeY / 8) {
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

        this.movement.update();
    }
}
