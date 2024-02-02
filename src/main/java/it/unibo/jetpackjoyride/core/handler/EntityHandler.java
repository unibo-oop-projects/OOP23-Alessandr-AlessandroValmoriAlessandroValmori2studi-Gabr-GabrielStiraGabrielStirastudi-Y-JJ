package it.unibo.jetpackjoyride.core.handler;

import java.util.*;

import it.unibo.jetpackjoyride.core.hitbox.Hitbox;
import javafx.scene.Group;

public interface EntityHandler extends Runnable {
    List<ObstacleController> getControllers();
    boolean update(Group obstacleGroup, Hitbox playerHitbox); //returns true if an obstacle hit the player
    void run();
    void start();
    void over();
} 
