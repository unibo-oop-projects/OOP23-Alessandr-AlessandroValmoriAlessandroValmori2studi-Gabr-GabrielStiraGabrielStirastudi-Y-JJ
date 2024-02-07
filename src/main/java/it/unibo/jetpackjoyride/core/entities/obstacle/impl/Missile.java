package it.unibo.jetpackjoyride.core.entities.obstacle.impl;

import it.unibo.jetpackjoyride.core.movement.MovementGenerator.MovementChangers;
import it.unibo.jetpackjoyride.utilities.GameInfo;
import it.unibo.jetpackjoyride.utilities.Pair;
import it.unibo.jetpackjoyride.core.entities.obstacle.api.AbstractObstacle;
import it.unibo.jetpackjoyride.core.hitbox.api.Hitbox;
import it.unibo.jetpackjoyride.core.movement.Movement;
import it.unibo.jetpackjoyride.core.movement.MovementGenerator;

import java.util.*;

public class Missile extends AbstractObstacle {
    private final static Integer DELAYBEFOREDESTRUCTION = 50;
    private int lifetimeAfterDeactivation;

    public Missile(Movement movement, Hitbox hitbox) {
        super(ObstacleType.MISSILE, movement, hitbox);
        lifetimeAfterDeactivation = DELAYBEFOREDESTRUCTION;
    }

    @Override
    protected void updateStatus() {
        Double outOfBoundsX = GameInfo.getInstance().getScreenWidth();
        Double outOfBoundsY = GameInfo.getInstance().getScreenHeight();
        if((this.movement.getCurrentPosition().get1() < -outOfBoundsX/8) ||
           (!this.movement.getMovementChangers().contains(MovementChangers.BOUNCING) && 
                (this.movement.getCurrentPosition().get2() > outOfBoundsY - outOfBoundsY/8 || this.movement.getCurrentPosition().get2() < outOfBoundsY/8)) ||
           (this.lifetimeAfterDeactivation < 0)
            ) {
                    this.obstacleStatus = ObstacleStatus.INACTIVE;
        }
        if(this.obstacleStatus.equals(ObstacleStatus.DEACTIVATED)) {
            if(lifetimeAfterDeactivation == DELAYBEFOREDESTRUCTION) {
                this.changeObstacleMovement(new MovementGenerator(this.movement.getCurrentPosition(),new Pair<>(0.0,0.0),new Pair<>(0.0,0.0),new Pair<>(0.0,0.0)).setMovementChangers(List.of(MovementChangers.SLOW)));
            }
            lifetimeAfterDeactivation--;
        }
    }
}
