package it.unibo.jetpackjoyride.core.entities.powerup.impl;

import java.util.ArrayList;
import java.util.List;

import it.unibo.jetpackjoyride.core.entities.powerup.api.PowerUp;
import it.unibo.jetpackjoyride.core.hitbox.api.Hitbox;
import it.unibo.jetpackjoyride.core.movement.Movement;
import it.unibo.jetpackjoyride.core.movement.MovementImpl;
import it.unibo.jetpackjoyride.utilities.Pair;

public class MrCuddlesGenerator {
    private final static Integer MRCUDDLESLENGHT = 12;
    private List<PowerUp> mrCuddles;


    public MrCuddlesGenerator(Movement powerUpMovement, Hitbox powerUpHitbox) {
        this.mrCuddles = new ArrayList<>();

        for(int i=MRCUDDLESLENGHT-1; i>=0; i--) {   
            Movement delayedMovement = new MovementImpl(new Pair<>(powerUpMovement.getCurrentPosition().get1()-35.0*i-(i != 0 ? 10.0 : 0.0), powerUpMovement.getCurrentPosition().get2()), powerUpMovement.getSpeed(), powerUpMovement.getAcceleration(), powerUpMovement.getRotation(), powerUpMovement.getMovementChangers());
            PowerUp mrCuddlesBody = new MrCuddles(delayedMovement, powerUpHitbox, i);
            this.mrCuddles.add(mrCuddlesBody);
        }
    }

    public List<PowerUp> generateMrCuddle() {
        return this.mrCuddles;
    }

}
