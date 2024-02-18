package it.unibo.jetpackjoyride.core.handler.obstacle;
import java.util.*;

import it.unibo.jetpackjoyride.core.entities.entity.api.EntityGenerator;
import it.unibo.jetpackjoyride.core.entities.entity.impl.EntityGeneratorImpl;
import it.unibo.jetpackjoyride.core.entities.obstacle.api.Obstacle;
import it.unibo.jetpackjoyride.core.entities.obstacle.api.Obstacle.ObstacleType;
import it.unibo.jetpackjoyride.core.handler.generic.GenericController;
import it.unibo.jetpackjoyride.core.movement.Movement;
import it.unibo.jetpackjoyride.core.movement.MovementImpl;
import it.unibo.jetpackjoyride.utilities.GameInfo;
import it.unibo.jetpackjoyride.utilities.Pair;
import java.util.stream.*;

public class ObstacleLoader {
    private final EntityGenerator entityGenerator;
    private List<Pair<GenericController<Obstacle,ObstacleView>, Integer>> totalPattern;
    private Integer interval;
    private Integer duration;

    public ObstacleLoader() {
        this.entityGenerator = new EntityGeneratorImpl();
        this.totalPattern = new ArrayList<>();
        this.duration = 0;
        this.interval = 0;
    }

    public void generatePattern(final Integer difficulty) {
        this.totalPattern.clear();
        this.duration = 0;
        this.interval = 0;
        this.totalPattern.addAll(this.missilePatterns());
    }

    public List<GenericController<Obstacle,ObstacleView>> getInstanceOfPattern() {
        this.interval++;
        if(!this.hasFinished()) {
            final List<GenericController<Obstacle,ObstacleView>> instanceOfPattern = new ArrayList<>();
            instanceOfPattern.addAll(this.totalPattern.stream().filter(p -> p.get2().equals(this.interval)).map(p -> p.get1()).collect(Collectors.toList()));
            
            return instanceOfPattern;
        }
        return List.of();
    }


    public boolean hasFinished() {
        return this.duration.equals(this.interval);
    }



    private List<Pair<GenericController<Obstacle,ObstacleView>, Integer>> missilePatterns() {
        this.duration +=20;
        final Double screenSizeX = GameInfo.getInstance().getScreenWidth();
        final Double screenSizeY = GameInfo.getInstance().getScreenHeight();

        final List<GenericController<Obstacle,ObstacleView>> obstacleControllers = new ArrayList<>();

        for(int i=0; i<5; i++) {
            obstacleControllers.addAll(this.entityGenerator.generateObstacle(ObstacleType.MISSILE, new MovementImpl(new Pair<>(screenSizeX - screenSizeX/20, screenSizeY/6 + screenSizeY/6*i),new Pair<>(0.0, 0.0), new Pair<>(0.0, 0.0), new Pair<>(0.0, 0.0), List.of())));
        }

        List<Pair<GenericController<Obstacle,ObstacleView>, Integer>> listOfMissiles = new ArrayList<>();
        for(int i=0; i<5; i++) {
            listOfMissiles.add(new Pair<>(obstacleControllers.get(i), i+1));
        }
                
        return listOfMissiles;
    }

    private List<Pair<GenericController<Obstacle,ObstacleView>, Integer>> zapperPatterns() {
        return totalPattern;

    }

    private List<Pair<GenericController<Obstacle,ObstacleView>, Integer>> laserPatterns() {
        return totalPattern;

    }
}
