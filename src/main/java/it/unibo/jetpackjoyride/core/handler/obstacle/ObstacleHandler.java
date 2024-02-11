package it.unibo.jetpackjoyride.core.handler.obstacle;

import java.util.List;

import it.unibo.jetpackjoyride.core.hitbox.api.Hitbox;
import javafx.scene.Group;

public interface ObstacleHandler extends Runnable {
    List<ObstacleController> getControllers();

    boolean update(Group obstacleGroup, Hitbox playerHitbox); // returns true if an obstacle hit the player

    void run();

    void start();

    void over();
}
