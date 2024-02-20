package it.unibo.jetpackjoyride.core.handler.obstacle;

import java.util.List;
import it.unibo.jetpackjoyride.core.entities.obstacle.api.Obstacle;
import it.unibo.jetpackjoyride.core.handler.generic.GenericController;
import it.unibo.jetpackjoyride.utilities.GameInfo;


public final class ObstacleSpawner {
    private final ObstacleLoader obstacleLoader;


    public ObstacleSpawner() {
        this.obstacleLoader = new ObstacleLoader();
    }

    public List<GenericController<Obstacle,ObstacleView>> generateChunk() {
        if(this.obstacleLoader.hasFinished()) {
            final Integer difficulty = GameInfo.MOVE_SPEED.get() - GameInfo.getInstance().getInitialGameSpeed() + 1;
            this.obstacleLoader.generatePattern(difficulty);
        }
        return this.obstacleLoader.getInstanceOfPattern();
    }
}
