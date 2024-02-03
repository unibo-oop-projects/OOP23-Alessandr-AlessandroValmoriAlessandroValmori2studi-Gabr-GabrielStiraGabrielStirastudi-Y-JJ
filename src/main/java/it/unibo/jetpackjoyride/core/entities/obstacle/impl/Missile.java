package it.unibo.jetpackjoyride.core.entities.obstacle.impl;

import it.unibo.jetpackjoyride.core.movement.MovementGenerator.MovementChangers;
import it.unibo.jetpackjoyride.utilities.Pair;
import it.unibo.jetpackjoyride.core.entities.obstacle.api.AbstractObstacle;
import it.unibo.jetpackjoyride.core.hitbox.Hitbox;
import it.unibo.jetpackjoyride.core.movement.Movement;
import it.unibo.jetpackjoyride.core.movement.MovementGenerator;

import java.util.*;

public class Missile extends AbstractObstacle {
    private int lifetimeAfterDeactivation;

    public Missile(Movement movement, Hitbox hitbox) {
        super(ObstacleType.MISSILE, movement, hitbox);
        lifetimeAfterDeactivation=50;
    }

    @Override
    public void update() {
        this.movement.update();
        this.hitbox.updateHitbox(this.movement.getCurrentPosition(), this.movement.getRotation().get2());
        this.lifetime++;

        if((this.movement.getCurrentPosition().get1() < -200) ||
           (!this.movement.getMovementChangers().contains(MovementChangers.BOUNCING) && 
                (this.movement.getCurrentPosition().get2() < 0 || this.movement.getCurrentPosition().get2() > 800)) ||
           (this.lifetimeAfterDeactivation < 0)
            ) {
                    this.obstacleStatus = ObstacleStatus.INACTIVE;
        }
        if(this.obstacleStatus.equals(ObstacleStatus.DEACTIVATED)) {
            if(lifetimeAfterDeactivation==50) {
                this.changeObstacleMovement(new MovementGenerator(this.movement.getCurrentPosition(),new Pair<>(0.0,0.0),new Pair<>(0.0,0.0),new Pair<>(0.0,0.0)).setMovementChangers(List.of(MovementChangers.SLOW)));
            }
            lifetimeAfterDeactivation--;
        }
    }
}
