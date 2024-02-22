package it.unibo.jetpackjoyride.core.entities.obstacle.impl;

import it.unibo.jetpackjoyride.utilities.GameInfo;
import it.unibo.jetpackjoyride.core.entities.obstacle.api.AbstractObstacle;
import it.unibo.jetpackjoyride.core.hitbox.api.Hitbox;
import it.unibo.jetpackjoyride.core.movement.Movement;

public final class Missile extends AbstractObstacle {
    private static final Double OUTOFBOUNDSSX = -100.0;
    private static final Integer DELAYBEFOREDESTRUCTION = 50;
    private static final Integer DELAYBEFOREACTIVATING = 150;
    private static final Double WARNINGSPAWNINGX = 1220.0;
    private Integer lifetimeAfterDeactivation;
    private Movement bufferMovement;

    public Missile(final Movement movement, final Hitbox hitbox) {
        super(ObstacleType.MISSILE, movement, hitbox);
        this.lifetimeAfterDeactivation = DELAYBEFOREDESTRUCTION + DELAYBEFOREACTIVATING;
        this.entityStatus = EntityStatus.CHARGING;

        bufferMovement = new Movement.Builder().setPosition(this.movement.getRealPosition())
            .setSpeed(this.movement.getSpeed())
            .setAcceleration(this.movement.getAcceleration())
            .setRotation(this.movement.getSpeed().get2()> 0 ? -45.0 : this.movement.getSpeed().get2()<0 ? 45.0 : 0.0,0.0)
            .setMovementChangers(this.movement.getMovementChangers()).build();

        this.movement = new Movement.Builder().setPosition(WARNINGSPAWNINGX, this.movement.getRealPosition().get2())
                .setRotation(this.bufferMovement.getRotation())
                .build();
    }

    @Override
    protected void updateStatus(final boolean isSpaceBarPressed) {
        if(this.entityStatus.equals(EntityStatus.CHARGING)) {
            this.lifetimeAfterDeactivation--;
            if(this.lifetimeAfterDeactivation.equals(DELAYBEFOREDESTRUCTION)) {
                this.entityStatus = EntityStatus.ACTIVE;

                this.movement = new Movement.Builder()
                    .setPosition(this.bufferMovement.getRealPosition().get1()-OUTOFBOUNDSSX, this.bufferMovement.getRealPosition().get2())
                    .setSpeed(this.bufferMovement.getSpeed().get1(), this.bufferMovement.getSpeed().get2())
                    .setAcceleration(this.bufferMovement.getAcceleration())
                    .setRotation(this.bufferMovement.getRotation())
                    .setMovementChangers(this.bufferMovement.getMovementChangers())
                    .build();
            }
        }

        if(this.entityStatus.equals(EntityStatus.DEACTIVATED) && this.lifetimeAfterDeactivation > DELAYBEFOREDESTRUCTION || this.movement.getRealPosition().get1() < OUTOFBOUNDSSX || this.lifetimeAfterDeactivation < 0) {
            this.entityStatus = EntityStatus.INACTIVE;
        }

        if (this.entityStatus.equals(EntityStatus.DEACTIVATED)) {
            if (this.lifetimeAfterDeactivation.equals(DELAYBEFOREDESTRUCTION)) {
                this.movement = new Movement.Builder()
                .setPosition(this.movement.getRealPosition())
                .setSpeed(this.movement.getSpeed().get1()/5, 0.0)
                .build();
            }
            this.lifetimeAfterDeactivation--;
        }
    }
}
