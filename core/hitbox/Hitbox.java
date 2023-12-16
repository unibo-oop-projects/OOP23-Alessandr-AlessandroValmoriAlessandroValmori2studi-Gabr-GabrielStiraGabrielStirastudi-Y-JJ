package core.hitbox;

/* IMPORTANT for the ones which will work with entities.
 */

/* An interface which deals with the hitbox of entities */

public interface Hitbox {
    /* The general idea is to model the hitbox of an entity with a set of rectangles. java.awt.Rectangle could be used
     * instead of manually having to deal with the position of each vertex, height, width...
     */

    /*TODO: decide if rectangles are a good choice or the use of polygons (custom) is better. 
      Personally, I think the entities we have to deal with don't require so much precision in regards to hitbox detection;
      indeed, most of these will have a shape which could be approximated with one or few rectangles.*/
      

      /*Some general methods */
      void updateHitbox();

      void draw();

      /*TODO: Also, we should decide if we want the hitboxes to check if they are colliding with other hitboxes
       * by themself or let the job to an external handler. 
       */

       void setHitboxOn();

       void setHitboxOff();

       boolean isHitboxOn();
}
