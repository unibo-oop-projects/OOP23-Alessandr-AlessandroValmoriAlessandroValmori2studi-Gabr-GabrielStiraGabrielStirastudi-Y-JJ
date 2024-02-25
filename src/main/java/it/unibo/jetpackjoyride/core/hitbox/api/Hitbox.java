package it.unibo.jetpackjoyride.core.hitbox.api;

import java.util.Set;
import it.unibo.jetpackjoyride.utilities.Pair;

/* An interface for the hitbox of entities */
/**
 * The {@link Hitbox} interface defines the methods which will be implemented by
 * the instances of hitbox classes used by the entities.
 * 
 * @author gabriel.stira@studio.unibo.it
 */
public interface Hitbox {
  /*
   * The general idea is to model the hitbox of an entity with a set of
   * rectangles. Even if it is simpler to
   * adopt the 'one rectangle' since only one rectangle has to be updated, some
   * entities are able to rotate and
   * that would make the one rectangle implementation tricky. Therefore, an idea
   * could be to use four Pair<>(x,y)
   * to track the vertex of a polygon, which can be a better solution in case of
   * rotating shapes (rectangles can't
   * rotate), so that a "rotating rectangle" can be computed and act as hitbox
   * even if it is actually a polygon.
   */

  /**
   * A collision detection is made between this hitbox class and the hitbox
   * provided.
   * If one of the vertex or the center of one of the two hitbox is inside the
   * polygon
   * formed by the four vertices which make the other hitbox
   * 
   * @param otherHitbox The hitbox to make the collision detection with.
   * @return True if the two hitbox collide, false otherwise.
   */
  boolean isTouching(Hitbox otherHitbox);

  /**
   * Updates the hitbox position and rotation.
   *
   * @param newPosition The new position (x, y) of the hitbox.
   * @param angle       The new angle of rotation for the hitbox.
   */
  void updateHitbox(Pair<Double, Double> newPosition, Double angle);

  /**
   * Gets the position (x, y) of the hitbox.
   *
   * @return The position (x, y) of the hitbox.
   */
  Pair<Double, Double> getHitboxPosition();

  /**
   * Gets the dimensions (width, height) of the hitbox.
   *
   * @return The dimensions (width, height) of the hitbox.
   */
  Pair<Double, Double> getHitboxDimensions();

  /**
   * Gets the rotation angle of the hitbox.
   *
   * @return The rotation angle of the hitbox.
   */
  Double getHitboxRotation();

  /**
   * Gets the vertices of the hitbox.
   *
   * @return A set of four vertices (x, y) representing the hitbox.
   */
  Set<Pair<Double, Double>> getHitboxVertex();
}
