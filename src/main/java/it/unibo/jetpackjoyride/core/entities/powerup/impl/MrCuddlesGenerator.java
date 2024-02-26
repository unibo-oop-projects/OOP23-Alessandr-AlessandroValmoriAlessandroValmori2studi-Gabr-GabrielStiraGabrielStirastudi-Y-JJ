package it.unibo.jetpackjoyride.core.entities.powerup.impl;

import java.util.ArrayList;
import java.util.List;

import it.unibo.jetpackjoyride.core.entities.powerup.api.PowerUp;
import it.unibo.jetpackjoyride.core.hitbox.api.Hitbox;
import it.unibo.jetpackjoyride.core.movement.Movement;
import java.util.Collections;

/**
 * The {@link MrCuddlesGenerator} class is used to generate both the head and
 * the body
 * pieces which form the complete powerup MrCuddle. Uses the {@link MrCuddles}
 * class to
 * generate a list of powerup, which is what the "powerup MrCuddle" actually is.
 * 
 * @author gabriel.stira@studio.unibo.it
 */
public final class MrCuddlesGenerator {
    /**
     * Defines the number of pieces forming the body of MrCuddles.
     */
    private static final Integer MRCUDDLES_LENGHT = 12;
    /**
     * Defines the distance between the pieces of MrCuddle.
     */
    private static final Integer DISTANCE_BETWEEN_PIECES = 36;
    /*
     * Defines the distance the first body piece will additionally have form the
     * head.
     */
    private static final Integer DISTANCE_FROM_HEAD = 10;
    /**
     * Defines the powerup MrCuddle as a list of powerup (head + body pieces).
     */
    private final List<PowerUp> mrCuddles;

    /**
     * Constructor used to create a MrCuddle powerup as a list of powerup.
     * 
     * @param powerUpMovement The movement characteristics of the mrcuddle powerup.
     * @param powerUpHitbox   The collision characteristics of the mrcuddle powerup.
     */
    public MrCuddlesGenerator(final Movement powerUpMovement, final Hitbox powerUpHitbox) {
        this.mrCuddles = new ArrayList<>();

        for (int i = MRCUDDLES_LENGHT - 1; i >= 0; i--) {

            final Movement delayedMovement = new Movement.Builder()
                    .addNewPosition(powerUpMovement.getPosition().get1() - DISTANCE_BETWEEN_PIECES * i
                            - (i != 0 ? DISTANCE_FROM_HEAD : 0), powerUpMovement.getPosition().get2())
                    .addNewSpeed(powerUpMovement.getSpeed())
                    .addNewAcceleration(powerUpMovement.getAcceleration())
                    .addNewRotation(powerUpMovement.getRotation())
                    .addNewMovementChangers(powerUpMovement.getMovementChangers())
                    .build();

            final PowerUp mrCuddlesBody = new MrCuddles(delayedMovement, powerUpHitbox, i);
            this.mrCuddles.add(mrCuddlesBody);
        }
    }

    /**
     * Generates and returns the MrCuddle powerup as a list of powerup.
     * 
     * @return The MrCuddle powerup as a list of powerup.
     */
    public List<PowerUp> generateMrCuddle() {
        return Collections.unmodifiableList(this.mrCuddles);
    }

}
