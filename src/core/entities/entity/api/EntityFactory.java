package core.entities.entity.api;

import core.entities.obstacle.impl.ObstacleImpl;
import core.hitbox.Hitbox;
import core.movement.impl.MovementImpl;
import static core.entities.obstacle.api.Obstacle.ObstacleType;

public interface EntityFactory {
    <X extends MovementImpl> ObstacleImpl generateObstacle(ObstacleType obstacleType, X movement, Hitbox hitbox);
}
