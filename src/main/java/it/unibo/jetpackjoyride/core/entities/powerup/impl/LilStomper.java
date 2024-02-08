package it.unibo.jetpackjoyride.core.entities.powerup.impl;

import it.unibo.jetpackjoyride.Game;
import it.unibo.jetpackjoyride.core.entities.powerup.api.AbstractPowerUp;
import it.unibo.jetpackjoyride.core.hitbox.api.Hitbox;
import it.unibo.jetpackjoyride.core.movement.Movement;
import it.unibo.jetpackjoyride.utilities.GameInfo;
import it.unibo.jetpackjoyride.utilities.Pair;

public class LilStomper extends AbstractPowerUp{
    private final static Double BASEJUMPHEIGHTSPEED = 80.0;
    private final static Double TICKJUMPHEITGHSPEED = 5.5;
    private final static Integer MAXTICKSFORJUMP = 15;
    private final static Integer RECOVERTICKSAFTERLANDING = 20;
    private final static Double DESCENDINGBASESPEED = 30.0;

    private Integer loadJump;

    public LilStomper(Movement movement, Hitbox hitbox) {
        super(PowerUpType.LILSTOMPER, movement, hitbox);
        this.loadJump = 0;
        this.performingAction = PerformingAction.WALKING;
    }

    @Override
    public void update(boolean isSpaceBarPressed) {
        GameInfo infoResolution = GameInfo.getInstance();
        Double screenSizeY = infoResolution.getScreenHeight();

        switch (this.performingAction) {
            case WALKING:
                if(isSpaceBarPressed) {
                    this.performingAction = PerformingAction.JUMPING;
                }
                break;
            case JUMPING:
                if(isSpaceBarPressed) {
                    this.loadJump++;
                }
                if(this.loadJump == MAXTICKSFORJUMP || !isSpaceBarPressed) {
                    this.movement.setSpeed(new Pair<>(this.movement.getSpeed().get1(), (-this.loadJump*TICKJUMPHEITGHSPEED - BASEJUMPHEIGHTSPEED)*screenSizeY/infoResolution.getDefaultHeight()));
                    this.loadJump = 0;
                    this.performingAction = PerformingAction.ASCENDING;
                }
                break;
            case ASCENDING:
           
                if(this.movement.getSpeed().get2()>=0) {
                    this.performingAction = PerformingAction.DESCENDING;
                }
                break;
            case DESCENDING:
                if(isSpaceBarPressed && (this.movement.getSpeed().get2() > (DESCENDINGBASESPEED)*screenSizeY/infoResolution.getDefaultHeight())) {
                    this.performingAction = PerformingAction.GLIDING;
                }

                if(this.movement.getCurrentPosition().get2()>screenSizeY-screenSizeY/8) {
                    this.performingAction = PerformingAction.LANDING;
                    this.loadJump = -RECOVERTICKSAFTERLANDING;
                }
                break;
            case GLIDING:
                if(isSpaceBarPressed) {
                    this.movement.setSpeed(new Pair<>(this.movement.getSpeed().get1(),(DESCENDINGBASESPEED)*screenSizeY/infoResolution.getDefaultHeight())); 
                } else {
                    this.performingAction = PerformingAction.DESCENDING;
                }

                if(this.movement.getCurrentPosition().get2()>screenSizeY-screenSizeY/8) {
                    this.performingAction = PerformingAction.LANDING;
                    this.loadJump = -RECOVERTICKSAFTERLANDING;
                }
                break;
            case LANDING:
                this.loadJump++;
                if(this.loadJump == 0) {
                    this.performingAction = PerformingAction.WALKING;
                }
                break;
            default:
                break;
        }

        this.movement.update();
    }
}
