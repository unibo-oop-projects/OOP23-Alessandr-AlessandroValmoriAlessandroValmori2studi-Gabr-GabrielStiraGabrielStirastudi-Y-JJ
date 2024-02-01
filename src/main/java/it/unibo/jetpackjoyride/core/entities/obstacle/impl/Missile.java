package it.unibo.jetpackjoyride.core.entities.obstacle.impl;

import it.unibo.jetpackjoyride.core.movement.MovementGenerator.MovementChangers;

import it.unibo.jetpackjoyride.core.entities.obstacle.api.AbstractObstacle;
import it.unibo.jetpackjoyride.core.hitbox.Hitbox;
import it.unibo.jetpackjoyride.core.movement.Movement;

public class Missile extends AbstractObstacle {

    public Missile(Movement movement, Hitbox hitbox) {
        super(ObstacleType.MISSILE, movement, hitbox);
    }

    @Override
    public void update() {
        this.movement.update();
        this.hitbox.updateHitbox(this.movement.getCurrentPosition(), this.movement.getRotation().get2());
        this.lifetime++;

        if(this.movement.getCurrentPosition().get1() < -200 ||
           !this.movement.getMovementChangers().contains(MovementChangers.BOUNCING) && 
                (this.movement.getCurrentPosition().get2() < 0 || this.movement.getCurrentPosition().get1() > 800)) {
            this.obstacleStatus = ObstacleStatus.INACTIVE;
        }
        if(this.movement.getCurrentPosition().get1() == 400) {
            this.obstacleStatus = ObstacleStatus.DEACTIVATED;
        }
    }
}
