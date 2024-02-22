package it.unibo.jetpackjoyride.core.entities.obstacle.impl;

import it.unibo.jetpackjoyride.core.entities.obstacle.api.AbstractObstacle;
import it.unibo.jetpackjoyride.core.hitbox.api.Hitbox;
import it.unibo.jetpackjoyride.core.movement.Movement;

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
    private Movement bufferMovement;

    public Missile(final Movement newMovement, final Hitbox hitbox) {
        super(ObstacleType.MISSILE, newMovement, hitbox);
        this.lifetimeAfterDeactivation = DELAYBEFOREDESTRUCTION + DELAYBEFOREACTIVATING;
        this.entityStatus = EntityStatus.CHARGING;

        this.bufferMovement = new Movement.Builder()
                    .setPosition(newMovement.getRealPosition())
                    .setSpeed(newMovement.getSpeed())
                    .setAcceleration(newMovement.getAcceleration())
                    .setRotation(newMovement.getRotation())
                    .setMovementChangers(newMovement.getMovementChangers())
                    .build();

                    System.out.println(this.bufferMovement.getSpeed());

        this.movement = new Movement.Builder().setPosition(WARNINGSPAWNINGX, this.bufferMovement.getRealPosition().get2())
                .setRotation(this.bufferMovement.getRotation())
                .build();
    }

    @Override
    protected void updateStatus(final boolean isSpaceBarPressed) {
        System.out.println(this.bufferMovement.getSpeed());
        System.out.println(this.lifetimeAfterDeactivation);

        if(this.entityStatus.equals(EntityStatus.CHARGING)) {
            this.lifetimeAfterDeactivation--;
            if(this.lifetimeAfterDeactivation.equals(DELAYBEFOREDESTRUCTION)) {
                this.entityStatus = EntityStatus.ACTIVE;

                this.movement = new Movement.Builder()
                    .setPosition(OUTOFBOUNDSDX, this.bufferMovement.getRealPosition().get2())
                    .setSpeed(this.bufferMovement.getSpeed())
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
