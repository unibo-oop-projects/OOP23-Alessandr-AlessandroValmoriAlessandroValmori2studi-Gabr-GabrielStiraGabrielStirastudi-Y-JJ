package it.unibo.jetpackjoyride.core.entities.powerup.api;

import it.unibo.jetpackjoyride.core.entities.entity.api.AbstractEntity;
import it.unibo.jetpackjoyride.core.hitbox.api.Hitbox;
import it.unibo.jetpackjoyride.core.movement.Movement;

/**
 * The {@link AbstractPowerUp} class extends {@link AbstractEntity}, so inherits
 * the common behaviour of all entities. The class also defines the methods and 
 * behaviour all {@link PowerUp} entities.
 * 
 * @author gabriel.stira@studio.unibo.it
 */
public abstract class AbstractPowerUp extends AbstractEntity implements PowerUp {
    /**
     * Defines the type of the powerup.
     */
    protected final PowerUpType powerUpType;
    /**
     * Defines the action the powerup is performing.
     */
    protected PerformingAction performingAction;

    /**
     * Constructor used to create a new AbstractPowerUp.
     *
     * @param powerUpType  The type of the powerup.
     * @param newMovement  The movement characteristics of the powerup.
     * @param hitbox       The collision characteristics of the powerup.
     */
    public AbstractPowerUp(final PowerUpType powerUpType, final Movement newMovement, final Hitbox hitbox) {
        super(EntityType.POWERUP, newMovement, hitbox);
        this.powerUpType = powerUpType;
        this.setEntityStatus(EntityStatus.ACTIVE);
    }

    /**
     * Gets the type of the powerup.
     * 
     * @return The type of the powerup.
     */
    @Override
    public PowerUpType getPowerUpType() {
        return this.powerUpType;
    }

    /**
     * Gets the action the powerup is currently performing.
     * 
     * @return The action the powerup is currently performing.
     */
    @Override
    public PerformingAction getPerformingAction() {
        return this.performingAction;
    }
}
