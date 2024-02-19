package it.unibo.jetpackjoyride.core.entities.obstacle.api;

import it.unibo.jetpackjoyride.core.entities.entity.api.Entity;

public interface Obstacle extends Entity {
    enum ObstacleType {
        MISSILE, ZAPPER, LASER
    }

    ObstacleType getObstacleType();
}
