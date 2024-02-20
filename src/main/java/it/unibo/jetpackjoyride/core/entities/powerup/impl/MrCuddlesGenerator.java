package it.unibo.jetpackjoyride.core.entities.powerup.impl;

import java.util.ArrayList;
import java.util.List;

import it.unibo.jetpackjoyride.core.entities.powerup.api.PowerUp;
import it.unibo.jetpackjoyride.core.hitbox.api.Hitbox;
import it.unibo.jetpackjoyride.core.movement.Movement;

public final class MrCuddlesGenerator {
    private static final Integer MRCUDDLESLENGHT = 12;
    private static final Integer DISTANCEBETWEENPIECES = 36;
    private static final Integer DISTANCEFROMHEAD = 10;
    private final List<PowerUp> mrCuddles;

    public MrCuddlesGenerator(final Movement powerUpMovement, final Hitbox powerUpHitbox) {
        this.mrCuddles = new ArrayList<>();

        for (int i = MRCUDDLESLENGHT - 1; i >= 0; i--) {

            final Movement delayedMovement = new Movement.Builder()
                .setPosition(powerUpMovement.getRealPosition().get1() - DISTANCEBETWEENPIECES * i - (i != 0 ? DISTANCEFROMHEAD : 0), powerUpMovement.getRealPosition().get2())
                .setSpeed(powerUpMovement.getSpeed())
                .setAcceleration(powerUpMovement.getAcceleration())
                .setRotation(powerUpMovement.getRotation())
                .setMovementChangers(powerUpMovement.getMovementChangers())
                .build();


            final PowerUp mrCuddlesBody = new MrCuddles(delayedMovement, powerUpHitbox, i);
            this.mrCuddles.add(mrCuddlesBody);
        }
    }

    public List<PowerUp> generateMrCuddle() {
        return this.mrCuddles;
    }

}
