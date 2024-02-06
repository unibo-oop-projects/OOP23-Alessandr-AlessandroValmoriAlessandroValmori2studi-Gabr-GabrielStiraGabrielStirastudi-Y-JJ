package it.unibo.jetpackjoyride.core.entities.obstacle.api;

import it.unibo.jetpackjoyride.core.entities.entity.api.Entity;

public interface Obstacle extends Entity {
    enum ObstacleType {
        MISSILE, ZAPPER, LASER, CUSTOMOBSTACLE
    }

    enum ObstacleStatus {
        CHARGING,//The obstacle has no collision, but will have in a moment
        ACTIVE, //The obstacle has a collision
        DEACTIVATED, //The obstacle has no more a collision but can't be removed
        INACTIVE //The obstacle has no collision and can be removed
    }

    ObstacleType getObstacleType();

    void changeObstacleStatus(ObstacleStatus newObstacleStatus);

    ObstacleStatus getObstacleStatus();

    void update();
}
