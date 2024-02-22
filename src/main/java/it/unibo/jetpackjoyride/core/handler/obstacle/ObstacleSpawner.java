package it.unibo.jetpackjoyride.core.handler.obstacle;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import it.unibo.jetpackjoyride.core.entities.entity.impl.EntityControllerGeneratorImpl;
import it.unibo.jetpackjoyride.core.entities.obstacle.api.Obstacle;
import it.unibo.jetpackjoyride.core.entities.obstacle.api.Obstacle.ObstacleType;
import it.unibo.jetpackjoyride.core.handler.generic.GenericController;
import it.unibo.jetpackjoyride.core.movement.Movement;
import it.unibo.jetpackjoyride.utilities.GameInfo;


public final class ObstacleSpawner {
    private final ObstacleLoader obstacleLoader;
    private final EntityControllerGeneratorImpl entityControllersGenerator;

    public ObstacleSpawner() {
        this.obstacleLoader = new ObstacleLoader();
        this.entityControllersGenerator = new EntityControllerGeneratorImpl();
    }

    public List<GenericController<Obstacle,ObstacleView>> generateChunk() {
        if(this.obstacleLoader.hasFinished()) {
            final Integer difficulty = GameInfo.MOVE_SPEED.get() - GameInfo.getInstance().getInitialGameSpeed() + 1;
            this.obstacleLoader.generatePattern(difficulty);
        }
        final List<Obstacle> models = this.obstacleLoader.getInstanceOfPattern();
        return IntStream.range(0, models.size()).mapToObj(i -> this.entityControllersGenerator.generateObstacleController(models.get(i).getObstacleType(), models.get(i).getEntityMovement())).collect(Collectors.toList());
    }
}
