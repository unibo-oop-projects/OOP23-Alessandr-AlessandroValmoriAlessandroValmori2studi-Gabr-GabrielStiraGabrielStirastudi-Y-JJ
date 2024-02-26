/**
 * BarryImpl class represents the implementation of the Barry interface,
 * which defines the behavior of the player character in the Jetpack Joyride game.
 *
 * This class manages Barry's position, movement, and performingAction, including walking,
 * falling, and propelling using a jetpack. It also handles hitbox-related operations.
 */
package it.unibo.jetpackjoyride.core.entities.barry.impl;

import it.unibo.jetpackjoyride.core.entities.barry.api.Barry;
import it.unibo.jetpackjoyride.core.entities.entity.api.AbstractEntity;
import it.unibo.jetpackjoyride.core.entities.obstacle.api.Obstacle.ObstacleType;
import it.unibo.jetpackjoyride.core.movement.Movement;
import it.unibo.jetpackjoyride.core.hitbox.api.Hitbox;
import it.unibo.jetpackjoyride.utilities.Pair;
import java.util.List;
import it.unibo.jetpackjoyride.utilities.MovementChangers;

/**
 * @author alessandro.valmori2@studio.unibo.it.
 */

/**
 * This class implements the Barry interface and provides the functionality
 * for controlling the player character, Barry.
 */
public final class BarryImpl extends AbstractEntity implements Barry {
    /* The performing action. */
    private PerformingAction performingAction;

    /** A field used to determine wether barry has a shield or not. */
    private boolean hasShield;

    /** The current life status of Barry. */
    private BarryLifeStatus lifeStatus;

    /**
     * Defines the higher bound of the map.
     */
    private static final Double HIGH_BOUND = 80.0;
    /**
     * Defines the higher bound of the map.
     */
    private static final Double LOW_BOUND = 630.0;

    private static final Double PROPELLING_ACC = -1.1;

    private static final Double FALLING_ACC = 0.6;

    /**
     * The constructor for the Barry entity, iy first constructs its 
     * superclass, then sets its standard values, such as shield not equipped,
     * its life status to alive, its entity status to active and its performing action
     * to walking.
     * @param movement the {@link Movement} of Barry
     * @param hitbox the {@link Hitbox} of Barry
     */
    public BarryImpl(final Movement movement, final Hitbox hitbox) {
        super(EntityType.BARRY, movement, hitbox);
        this.hasShield = false;
        this.lifeStatus = BarryLifeStatus.ALIVE;
        this.setEntityStatus(EntityStatus.ACTIVE);
        this.performingAction = PerformingAction.WALKING;
    }

    @Override
    public PerformingAction getPerformingAction() {
        return this.performingAction;
    }

    @Override
    public boolean hasShield() {
        return this.hasShield;
    }

    @Override
    public boolean isAlive() {
        return this.lifeStatus.equals(BarryLifeStatus.ALIVE);
    }

    /**
     * Kills Barry based on the type of obstacle.
     * Updates Barry's hitbox and performing action accordingly.
     *
     * @param type the type of obstacle that killed Barry.
     */

    private void kill(final ObstacleType type) {
        this.lifeStatus = BarryLifeStatus.DEAD;
        this.performingAction = type.equals(ObstacleType.ZAPPER) ? PerformingAction.ZAPPED
                : type.equals(ObstacleType.LASER) ? PerformingAction.LASERED
                        : type.equals(ObstacleType.MISSILE) ? PerformingAction.BURNED : PerformingAction.UNDEFINED;
    }

    @Override
    public void removeShield() {
        this.hasShield = false;
    }

    @Override
    public void setShieldOn() {
        this.hasShield = true;
    }

    @Override
    public void hit(final ObstacleType type) {
        if (this.isAlive()) {
            if (this.hasShield()) {
                this.removeShield();
            } else {
                this.kill(type);
            }
        }
    }

    @Override
    public void setLifeStatus(final BarryLifeStatus lifeStatus) {
        this.lifeStatus = lifeStatus;
    }

    @Override
    protected void updateStatus(final boolean isSpaceBarPressed) {


        switch (this.performingAction) {
            case WALKING:
                if (isSpaceBarPressed) {
                    this.performingAction = PerformingAction.PROPELLING;
                }
                break;
            case PROPELLING:
                if (!isSpaceBarPressed) {
                    this.performingAction = PerformingAction.FALLING;
                }
                if (this.getEntityMovement().getPosition().get2() <= HIGH_BOUND) {
                    this.performingAction = PerformingAction.HEAD_DRAGGING;
                }
                break;

            case FALLING:
                if (isSpaceBarPressed) {
                    this.performingAction = PerformingAction.PROPELLING;

                }
                if (this.getEntityMovement().getPosition().get2() >= LOW_BOUND) {
                    this.performingAction = PerformingAction.WALKING;
                }
                break;
            case HEAD_DRAGGING:

                if (!isSpaceBarPressed) {
                    this.performingAction = PerformingAction.FALLING;
                }
                break;

            default:
                break;

        }

        final boolean isPropelling = this.performingAction.equals(PerformingAction.PROPELLING)
                || this.performingAction.equals(PerformingAction.HEAD_DRAGGING);
        this.setEntityMovement(new Movement.Builder()
        .setPosition(this.getEntityMovement().getPosition())
        .setSpeed(this.getEntityMovement().getSpeed())
        .setAcceleration(0.0, isPropelling ? PROPELLING_ACC : FALLING_ACC)
        .setMovementChangers(List.of(MovementChangers.BOUNDS))
        .build());

    }

}
