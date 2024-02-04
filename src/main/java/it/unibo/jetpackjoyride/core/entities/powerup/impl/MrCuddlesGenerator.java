package it.unibo.jetpackjoyride.core.entities.powerup.impl;

import java.util.ArrayList;
import java.util.List;

import it.unibo.jetpackjoyride.core.entities.powerup.api.PowerUp;
import it.unibo.jetpackjoyride.core.hitbox.api.Hitbox;
import it.unibo.jetpackjoyride.core.movement.Movement;
import it.unibo.jetpackjoyride.core.movement.MovementGenerator;
import it.unibo.jetpackjoyride.core.movement.MovementGenerator.MovementChangers;
import it.unibo.jetpackjoyride.utilities.Pair;

public class MrCuddlesGenerator {
    private final static Integer MRCUDDLESLENGHT = 15;
    private List<PowerUp> mrCuddles;


    public MrCuddlesGenerator(Movement powerUpMovement, Hitbox powerUpHitbox) {
        this.mrCuddles = new ArrayList<>();

        for(int i=MRCUDDLESLENGHT; i>=0; i--) {   
            Movement delayedMovement = new MovementGenerator(new Pair<>(powerUpMovement.getCurrentPosition().get1()-35.0*i-(i != 0 ? 45.0 : 0.0), powerUpMovement.getCurrentPosition().get2()), powerUpMovement.getSpeed(), powerUpMovement.getAcceleration(), powerUpMovement.getRotation()).setMovementChangers(List.of(MovementChangers.INITIALLYSTILL, MovementChangers.INVERSEGRAVITY, MovementChangers.BOUNDS));
            PowerUp mrCuddlesBody = new MrCuddles(delayedMovement, powerUpHitbox, i);
            this.mrCuddles.add(mrCuddlesBody);
        }
    }

    public List<PowerUp> generateMrCuddle() {
        return this.mrCuddles;
    }

}
