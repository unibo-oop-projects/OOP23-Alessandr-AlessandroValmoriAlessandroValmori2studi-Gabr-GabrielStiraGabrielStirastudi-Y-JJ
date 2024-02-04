package it.unibo.jetpackjoyride.core.entities.powerup.impl;

import java.util.ArrayList;
import java.util.List;

import it.unibo.jetpackjoyride.core.entities.powerup.api.AbstractPowerUp;
import it.unibo.jetpackjoyride.core.hitbox.api.Hitbox;
import it.unibo.jetpackjoyride.core.movement.Movement;
import it.unibo.jetpackjoyride.utilities.Pair;

public class MrCuddles extends AbstractPowerUp{
    private final static Integer MRCUDDLESLENGHT = 8;
    public Integer indexOfBody;
    private List<Boolean> lastFrames;

    public MrCuddles(Movement movement, Hitbox hitbox, Integer indexOfBody) {
        super(PowerUpType.MRCUDDLES, movement, hitbox);
        this.performingAction = PerformingAction.ASCENDING;
        this.indexOfBody = indexOfBody;
        this.lastFrames = new ArrayList<>();
        if(indexOfBody==0) {
            this.powerUpStatus = PowerUpStatus.ACTIVE;
        } else {
            this.powerUpStatus = PowerUpStatus.DEACTIVATED;
        }
    }

    @Override
    public void update(boolean isSpaceBarPressed) {
        if(this.lastFrames.size() == MRCUDDLESLENGHT) {
            this.lastFrames.remove(0);
        }
        this.lastFrames.add(isSpaceBarPressed);


        this.movement.update();

        if(lastFrames.size() > indexOfBody) {
            if(lastFrames.get(lastFrames.size()-1-indexOfBody)) {
                this.performingAction = PerformingAction.DESCENDING;
            } else {
                this.performingAction = PerformingAction.ASCENDING;
            }
        }
        

        if(this.movement.getCurrentPosition().get2()<150) {
            this.movement.setSpeed(new Pair<>(this.movement.getSpeed().get1(), 0.0));
            this.movement.setCurrentPosition(new Pair<>(this.movement.getCurrentPosition().get1(), 150.0));
        }
        if(this.movement.getCurrentPosition().get2()>650) {
            this.movement.setSpeed(new Pair<>(this.movement.getSpeed().get1(), 0.0));
            this.movement.setCurrentPosition(new Pair<>(this.movement.getCurrentPosition().get1(), 650.0));
        }
        
        switch (this.performingAction) {
            case ASCENDING:
                if(this.movement.getSpeed().get2()<-80) {
                    this.movement.setSpeed(new Pair<>(this.movement.getSpeed().get1(), 10.0));
                }
                break;
            case DESCENDING:
                this.movement.setSpeed(new Pair<>(this.movement.getSpeed().get1(), 70.0));
                break;
            default:
                break;
        }
    }   
}
