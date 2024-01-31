package it.unibo.jetpackjoyride.core.handler;

import java.util.*;

import javafx.scene.Group;

public interface ChunkMaker extends Runnable {
    List<ObstacleController> getControllers();
    void update(Group obstacleGroup);
    void run();
    void start();
    void over();
} 
