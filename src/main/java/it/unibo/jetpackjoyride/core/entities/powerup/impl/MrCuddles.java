package it.unibo.jetpackjoyride.core.entities.powerup.impl;

import java.util.ArrayList;
import java.util.List;

import it.unibo.jetpackjoyride.core.entities.entity.api.Entity;
import it.unibo.jetpackjoyride.core.entities.powerup.api.AbstractPowerUp;
import it.unibo.jetpackjoyride.core.entities.powerup.api.PowerUp;
import it.unibo.jetpackjoyride.core.hitbox.api.Hitbox;
import it.unibo.jetpackjoyride.core.movement.Movement;
import it.unibo.jetpackjoyride.utilities.MovementChangers;

/**
 * The {@link MrCuddles} class defines one of the powerups implemented
 * in the game. Since it extends {@link AbstractPowerUp}, it inherits all
 * methods and behaviours of {@link Entity} and {@link PowerUp}.
 * This powerup is really slim so it can pass trought where normally other
 * powerup can't, but its inverse gravity based movement may cause confusion.
 *
 * @author gabriel.stira@studio.unibo.it
 */
public final class MrCuddles extends AbstractPowerUp {
    /*
     * Defines a list of the last n recordings of space bar pressed or not pressed
     * with n being the number of pieces this powerup is made of. Every piece has a
     * delayed movement which is more noticeable the further it gets from the head,
     * which is the actual powerup (only the head has an active hitbox and not
     * delayed
     * movement).
     */
    private final List<Boolean> lastFrames;

    /**
     * Constructor used to create an instance of the class MrCuddles.
     * The head is the main powerup, the body are 'passive powerup',
     * since have no collision and their movement reflects the head's
     * one (but delaying it more and more the further away from it).
     * 
     * @param movement    The movement characteristics of the mrcuddle powerup.
     * @param hitbox      The collision characteristics of the mrcuddle powerup.
     * @param indexOfBody The index representing the distance (in terms of pieces)
     *                    of the body.
     */
    public MrCuddles(final Movement movement, final Hitbox hitbox, final Integer indexOfBody) {
        super(PowerUpType.MRCUDDLES, movement, hitbox);
        this.setPerformingAction(PerformingAction.ASCENDING);
        this.lastFrames = new ArrayList<>((indexOfBody + 1) * 2);

        for (int i = 0; i < (indexOfBody + 1) * 2; i++) {
            this.lastFrames.add(false);
        }

        this.setEntityStatus(indexOfBody == 0 ? EntityStatus.ACTIVE : EntityStatus.DEACTIVATED);
    }

    /**
     * Updates status and movement of the mrcuddle entity based on its position and
     * if the spacebar is pressed.
     * 
     * @param isSpaceBarPressed Is used to decrease the Y coordinate of the powerup.
     */
    @Override
    public void updateStatus(final boolean isSpaceBarPressed) {
        this.lastFrames.remove(0);
        this.lastFrames.add(isSpaceBarPressed);

        this.setPerformingAction(this.lastFrames.get(0) ? PerformingAction.DESCENDING : PerformingAction.ASCENDING);

        this.setEntityMovement(new Movement.Builder()
                .addNewPosition(this.getEntityMovement().getPosition())
                .addNewSpeed(this.getEntityMovement().getSpeed())
                .addNewAcceleration(this.getEntityMovement().getAcceleration())
                .addNewRotation(this.getEntityMovement().getSpeed().get2() * 2, 0.0)
                .addNewMovementChangers(List.of(MovementChangers.BOUNDS,
                        this.getPerformingAction().equals(PerformingAction.ASCENDING) ? MovementChangers.INVERSEGRAVITY
                                : MovementChangers.GRAVITY))
                .build());
    }
}
