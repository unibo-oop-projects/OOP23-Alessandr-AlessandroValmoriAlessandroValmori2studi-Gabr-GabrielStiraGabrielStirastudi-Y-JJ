package it.unibo.jetpackjoyride.core.entities.obstacle.impl;

import it.unibo.jetpackjoyride.utilities.GameInfo;
import it.unibo.jetpackjoyride.utilities.Pair;
import it.unibo.jetpackjoyride.core.entities.obstacle.api.AbstractObstacle;
import it.unibo.jetpackjoyride.core.hitbox.api.Hitbox;
import it.unibo.jetpackjoyride.core.movement.Movement;

import java.util.*;

public final class Missile extends AbstractObstacle {
    private static final Integer DELAYBEFOREDESTRUCTION = 50;
    private static final Integer DELAYBEFOREACTIVATING = 150;
    private Integer lifetimeAfterDeactivation;

    public Missile(final Movement movement, final Hitbox hitbox) {
        super(ObstacleType.MISSILE, movement, hitbox);
        this.lifetimeAfterDeactivation = DELAYBEFOREDESTRUCTION + DELAYBEFOREACTIVATING;
        this.entityStatus = EntityStatus.CHARGING;
    }

    @Override
    protected void updateStatus(final boolean isSpaceBarPressed) {
        final GameInfo infoResolution = GameInfo.getInstance();
        final Double screenX = infoResolution.getScreenWidth();
        final Double gameMovingSpeed = Double.valueOf(GameInfo.MOVE_SPEED.get());
        
        if(this.entityStatus.equals(EntityStatus.CHARGING)) {
            this.lifetimeAfterDeactivation--;
            if(this.lifetimeAfterDeactivation.equals(DELAYBEFOREDESTRUCTION)) {
                this.entityStatus = EntityStatus.ACTIVE;
                this.movement.setCurrentPosition(new Pair<>(this.movement.getCurrentPosition().get1() + screenX/16,this.movement.getCurrentPosition().get2()));
                this.movement.setSpeed(new Pair<>(-gameMovingSpeed*screenX/infoResolution.getDefaultWidth()*2, this.movement.getSpeed().get2()));
                this.movement.setMovementChangers(this.movement.getMovementChangers());
            }
        }

        if(this.entityStatus.equals(EntityStatus.DEACTIVATED) && this.lifetimeAfterDeactivation > DELAYBEFOREDESTRUCTION || this.movement.getCurrentPosition().get1() < -screenX / 8 || this.lifetimeAfterDeactivation < 0) {
            this.entityStatus = EntityStatus.INACTIVE;
        }

        if (this.entityStatus.equals(EntityStatus.DEACTIVATED)) {
            if (this.lifetimeAfterDeactivation.equals(DELAYBEFOREDESTRUCTION)) {
                this.movement.setAcceleration(new Pair<>(0.0, 0.0));
                this.movement.setSpeed(new Pair<>(-gameMovingSpeed, 0.0));
                this.movement.setRotation(new Pair<>(0.0,0.0));
                this.movement.setMovementChangers(List.of());
            }
            this.lifetimeAfterDeactivation--;
        }
    }
}
