package it.unibo.jetpackjoyride.core.entities.obstacle.api;

public interface Obstacle {
    enum ObstacleType {
        MISSILE, ZAPPER, LASER
    }

    enum ObstacleStatus {
        ACTIVE, INACTIVE, DEACTIVATED
    }

    ObstacleType getObstacleType();

    void changeObstacleStatus(ObstacleStatus newObstacleStatus);
}
