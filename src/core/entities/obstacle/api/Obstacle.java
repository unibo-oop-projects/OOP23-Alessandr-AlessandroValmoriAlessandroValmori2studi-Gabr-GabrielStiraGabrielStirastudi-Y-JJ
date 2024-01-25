package core.entities.obstacle.api;

import core.entities.entity.api.Entity;

public interface Obstacle extends Entity {
    enum ObstacleType {
        MISSILE, ZAPPER, LASER
    }

    enum ObstacleStatus {
        ACTIVE, INACTIVE, DEACTIVATED
    }

    ObstacleType getObstacleType();

    void changeObstacleStatus(ObstacleStatus newObstacleStatus);
}
