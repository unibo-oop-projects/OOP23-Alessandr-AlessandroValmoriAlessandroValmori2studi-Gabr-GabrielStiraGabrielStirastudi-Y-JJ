package it.unibo.jetpackjoyride.core.entities.powerup.impl;

import java.util.ArrayList;
import java.util.List;

import it.unibo.jetpackjoyride.core.entities.powerup.api.PowerUp;
import it.unibo.jetpackjoyride.core.hitbox.api.Hitbox;
import it.unibo.jetpackjoyride.core.movement.Movement;
import it.unibo.jetpackjoyride.core.movement.MovementImpl;
import it.unibo.jetpackjoyride.utilities.GameInfo;
import it.unibo.jetpackjoyride.utilities.Pair;

public final class MrCuddlesGenerator {
    private static final Integer MRCUDDLESLENGHT = 12;
    private final List<PowerUp> mrCuddles;

    public MrCuddlesGenerator(final Movement powerUpMovement, final Hitbox powerUpHitbox) {
        final GameInfo infoResolution = GameInfo.getInstance();
        final Double screenX = infoResolution.getScreenWidth();
        this.mrCuddles = new ArrayList<>();

        for (int i = MRCUDDLESLENGHT - 1; i >= 0; i--) {
            final Movement delayedMovement = new MovementImpl(
                    new Pair<>(powerUpMovement.getCurrentPosition().get1() - screenX/36 * i - (i != 0 ? screenX/128 : 0.0),
                            powerUpMovement.getCurrentPosition().get2()),
                    powerUpMovement.getSpeed(), powerUpMovement.getAcceleration(), powerUpMovement.getRotation(),
                    powerUpMovement.getMovementChangers());
            PowerUp mrCuddlesBody = new MrCuddles(delayedMovement, powerUpHitbox, i);
            this.mrCuddles.add(mrCuddlesBody);
        }
    }

    public List<PowerUp> generateMrCuddle() {
        return this.mrCuddles;
    }

}
