package it.unibo.jetpackjoyride.core.entities.obstacle.impl;

import it.unibo.jetpackjoyride.utilities.GameInfo;
import it.unibo.jetpackjoyride.utilities.Pair;
import it.unibo.jetpackjoyride.core.entities.obstacle.api.AbstractObstacle;
import it.unibo.jetpackjoyride.core.hitbox.api.Hitbox;
import it.unibo.jetpackjoyride.core.movement.Movement;
import it.unibo.jetpackjoyride.core.movement.Movement.MovementChangers;

import java.util.*;

public final class Missile extends AbstractObstacle {
    private static final Integer DELAYBEFOREDESTRUCTION = 50;
    private int lifetimeAfterDeactivation;

    public Missile(final Movement movement, final Hitbox hitbox) {
        super(ObstacleType.MISSILE, movement, hitbox);
        this.lifetimeAfterDeactivation = DELAYBEFOREDESTRUCTION;
    }

    @Override
    protected void updateStatus() {
        final Double outOfBoundsX = GameInfo.getInstance().getScreenWidth();
        final Double outOfBoundsY = GameInfo.getInstance().getScreenHeight();

        if ((this.movement.getCurrentPosition().get1() < -outOfBoundsX / 8) ||
                (!this.movement.getMovementChangers().contains(MovementChangers.BOUNCING) &&
                        (this.movement.getCurrentPosition().get2() > outOfBoundsY - outOfBoundsY / 8
                                || this.movement.getCurrentPosition().get2() < outOfBoundsY / 8))
                ||
                (this.lifetimeAfterDeactivation < 0)) {
            this.obstacleStatus = ObstacleStatus.INACTIVE;
        }
        if (this.obstacleStatus.equals(ObstacleStatus.DEACTIVATED)) {
            if (this.lifetimeAfterDeactivation == DELAYBEFOREDESTRUCTION) {
                this.movement.setSpeed(new Pair<>(0.0, 0.0));
                this.movement.setAcceleration(new Pair<>(0.0, 0.0));
                this.movement.setMovementChangers(List.of(MovementChangers.SLOW));
            }
            this.lifetimeAfterDeactivation--;
        }
    }
}
