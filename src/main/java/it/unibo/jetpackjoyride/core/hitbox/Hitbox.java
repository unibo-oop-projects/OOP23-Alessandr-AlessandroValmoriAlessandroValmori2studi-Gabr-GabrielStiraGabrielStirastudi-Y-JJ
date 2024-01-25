package it.unibo.jetpackjoyride.core.hitbox;

import it.unibo.jetpackjoyride.utilities.Pair;

/* IMPORTANT for the ones which will work with entities.
 */

/* An interface for the hitbox of entities */

public interface Hitbox {
    /* The general idea is to model the hitbox of an entity with a rectangle. More precision in hitbox collision detection 
    could be achieved by using more rectangles instead of only one, but problems may arise when having to deal woth
    rotating objects so I'll just stick to the one-rectangle hitbox solution.
     */

       void setHitboxOn();

       void setHitboxOff();

       boolean isHitboxOn();
       
       void updateHitbox(Pair<Double, Double> newPosition);

       Pair<Double,Double> getHitboxPosition();
}
