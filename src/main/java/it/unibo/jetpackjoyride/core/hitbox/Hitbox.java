package it.unibo.jetpackjoyride.core.hitbox;

import it.unibo.jetpackjoyride.utilities.Pair;

/* IMPORTANT for the ones which will work with entities.
 */

/* An interface for the hitbox of entities */

public interface Hitbox {
  /* The general idea is to model the hitbox of an entity with a set of rectangles. Even if it is simpler to 
    adopt the 'one rectangle' since only one rectangle has to be updated, some entities are able to rotate and
    that would make the one rectangle implementation tricky. Therefore, an idea could be to use four Pair<>(x,y)
    to track the vertex of a polygon, which can be a better solution in case of rotating shapes (rectangles can't
    rotate), so that a "rotating rectangle" can be computed and act as hitbox even if it is actually a polygon.
   */

     void setHitboxOn();

     void setHitboxOff();

     boolean isHitboxOn();

     boolean isTouching(Pair<Double, Double> pos);
     
     void updateHitbox(Pair<Double, Double> newPosition, Double angle);

     Pair<Double,Double> getHitboxPosition();
}
