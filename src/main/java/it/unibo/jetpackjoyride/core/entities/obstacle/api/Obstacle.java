package it.unibo.jetpackjoyride.core.entities.obstacle.api;

public interface Obstacle {
    enum ObstacleType {
        MISSILE, ZAPPER, LASER
    }

    enum ObstacleStatus {
        ACTIVE, //The obstacle has a collision
        INACTIVE, //The obstacle has no collision and can be removed
        DEACTIVATED, //The obstacle has no more a collision but can't be removed
        CHARGING //The obstacle has no collision, but will have in a moment
    }

    ObstacleType getObstacleType();

    void changeObstacleStatus(ObstacleStatus newObstacleStatus);
}
