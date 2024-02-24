package it.unibo.jetpackjoyride.core.entities.obstacle.impl;

import it.unibo.jetpackjoyride.core.entities.obstacle.api.AbstractObstacle;
import it.unibo.jetpackjoyride.core.hitbox.api.Hitbox;
import it.unibo.jetpackjoyride.core.movement.Movement;
import it.unibo.jetpackjoyride.utilities.MovementChangers;
import java.util.List;

public final class Missile extends AbstractObstacle {
    private static final Double OUTOFBOUNDSSX = -100.0;
    private static final Integer DELAYBEFOREDESTRUCTION = 50;
    private static final Integer DELAYBEFOREACTIVATING = 150;
    private static final Double WARNINGSPAWNINGX = 1220.0;
    private static final Double OUTOFBOUNDSDX = 1350.0;

    private Integer lifetimeAfterDeactivation;

    /**
     * Movement characteristics of the entity when spawned (needed for entities which need to know what
     * their movement was initially).
     */
    private Movement movementBuffer;

    public Missile(final Movement newMovement, final Hitbox hitbox) {
        super(ObstacleType.MISSILE, newMovement, hitbox);
        this.lifetimeAfterDeactivation = DELAYBEFOREDESTRUCTION + DELAYBEFOREACTIVATING;
        this.entityStatus = EntityStatus.CHARGING;

        this.movementBuffer = new Movement.Builder()
        .setPosition(this.movement.getPosition())
        .setSpeed(this.movement.getSpeed())
        .setAcceleration(this.movement.getAcceleration())
        .setRotation(this.movement.getRotation()).setMovementChangers(this.movement.getSpeed().get2() != 0 ? List.of(MovementChangers.BOUNCING) : List.of()).build();
    }

    @Override
    protected void updateStatus(final boolean isSpaceBarPressed) {

        if(this.entityStatus.equals(EntityStatus.CHARGING)) {
            /*Since at the beginning the missile has to be shown as a warning, 
            a buffer for the correct movement is used and its movement is initially set to a static one */
            if(this.lifetime.equals(1)) { 
                this.movement = new Movement.Builder().setPosition(WARNINGSPAWNINGX, this.movement.getPosition().get2())
                .setRotation(this.movement.getRotation())
                .build();
            }

            this.lifetimeAfterDeactivation--;
            if(this.lifetimeAfterDeactivation.equals(DELAYBEFOREDESTRUCTION)) {
                this.entityStatus = EntityStatus.ACTIVE;

                this.movement = new Movement.Builder()
                    .setPosition(OUTOFBOUNDSDX, this.movementBuffer.getPosition().get2())
                    .setSpeed(this.movementBuffer.getSpeed())
                    .setAcceleration(this.movementBuffer.getAcceleration())
                    .setRotation(this.movementBuffer.getRotation())
                    .setMovementChangers(this.movementBuffer.getMovementChangers())
                    .build();
            }
        }

        if(this.entityStatus.equals(EntityStatus.DEACTIVATED) && this.lifetimeAfterDeactivation > DELAYBEFOREDESTRUCTION || this.movement.getPosition().get1() < OUTOFBOUNDSSX || this.lifetimeAfterDeactivation < 0) {
            this.entityStatus = EntityStatus.INACTIVE;
        }

        if (this.entityStatus.equals(EntityStatus.DEACTIVATED)) {
            if (this.lifetimeAfterDeactivation.equals(DELAYBEFOREDESTRUCTION)) {
                this.movement = new Movement.Builder()
                .setPosition(this.movement.getPosition())
                .setSpeed(this.movement.getSpeed().get1()/5, 0.0)
                .build();
            }
            this.lifetimeAfterDeactivation--;
        }
    }
}
