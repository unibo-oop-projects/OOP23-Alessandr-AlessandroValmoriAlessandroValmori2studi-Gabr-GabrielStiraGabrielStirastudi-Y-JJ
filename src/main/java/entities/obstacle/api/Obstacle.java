package entities.obstacle.api;

import entities.entity.api.Entity;

public interface Obstacle extends Entity {
    enum ObstacleType {
        MISSILE, ZAPPER, LASER
    }

    enum ObstacleStatus {
        ACTIVE, INACTIVE, DEACTIVATED
    }

    ObstacleType getObstacleType();

    /* return true if the obstacle has been succesfully deactivated */
    boolean deactivateObstacle();
}
