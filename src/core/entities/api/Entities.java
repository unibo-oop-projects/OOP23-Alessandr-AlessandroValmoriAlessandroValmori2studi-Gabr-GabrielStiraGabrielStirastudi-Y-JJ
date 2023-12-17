package core.entities.api;

/* IMPORTANT for the ones which will work with instances of objects which have collisions, can be drawn, can be controlled, etc...
 * Referring in particular to Barry, obstacles, power-up, coins, mini power-up, any form of particle which has collisions, etc...
 * Choose if you want to implement a general interface Entities and then maybe some abstract classes to specify the behaviour of
 * each entity you will have to deal with, or if you want to implement different interfaces for each entity (first one recommended).
 */

/* An interface for the entities */

public interface Entities {

    void update();
    void draw();
    /* getPosition()     to decide what coordinate system we will use (we could create a utility class to define the methods we need, or 
    implement an already existing interface) */

}