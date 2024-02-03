/*package it.unibo.jetpackjoyride.core.entities.powerup.impl;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import it.unibo.jetpackjoyride.core.entities.powerup.api.AbstractPowerUp;
import it.unibo.jetpackjoyride.core.hitbox.api.Hitbox;
import it.unibo.jetpackjoyride.core.movement.Movement;
import it.unibo.jetpackjoyride.utilities.Pair;

public class MrCuddles extends AbstractPowerUp{
    private List<Boolean> lastFiveUpdates;

    public MrCuddles(Movement movement, Hitbox hitbox) {
        super(PowerUpType.MRCUDDLES, movement, hitbox);
        lastFiveUpdates = new ArrayList<>(5);

        for()
    }

    @Override
    public void update(boolean isSpaceBarPressed) {
        if(lastFiveUpdates.size() == 5) {
            lastFiveUpdates.remove(0);
        } 
        lastFiveUpdates.add(isSpaceBarPressed);
        head update(lastFiveUpdates.get(0));

        body 1 update(lastFiveUpdates.get(1));

    }   
}*/
