package it.unibo.jetpackjoyride.core.handler.obstacle;
import java.util.*;

import it.unibo.jetpackjoyride.core.entities.entity.api.EntityGenerator;
import it.unibo.jetpackjoyride.core.entities.entity.impl.EntityGeneratorImpl;
import it.unibo.jetpackjoyride.core.entities.obstacle.api.Obstacle;
import it.unibo.jetpackjoyride.core.entities.obstacle.api.Obstacle.ObstacleType;
import it.unibo.jetpackjoyride.core.handler.generic.GenericController;
import it.unibo.jetpackjoyride.core.movement.Movement.MovementChangers;
import it.unibo.jetpackjoyride.core.movement.Movement;
import it.unibo.jetpackjoyride.core.movement.MovementImpl;
import it.unibo.jetpackjoyride.utilities.GameInfo;
import it.unibo.jetpackjoyride.utilities.Pair;
import java.util.stream.*;

@FunctionalInterface 
interface PatternSelector { 
 
    void pattern(List<Pair<GenericController<Obstacle,ObstacleView>, Integer>> list); 
} 

public class ObstacleLoader {
    private final EntityGenerator entityGenerator;
    private List<Pair<GenericController<Obstacle,ObstacleView>, Integer>> totalPattern;
    private Integer interval;
    private Integer duration;
    private Double screenSizeX;
    private Double screenSizeY;
    private Map<Integer, PatternSelector> patternSelector;
    private Integer difficulty;

    public ObstacleLoader() {
        this.entityGenerator = new EntityGeneratorImpl();
        this.totalPattern = new ArrayList<>();
        this.duration = 0;
        this.interval = 0;
        this.loadPatterns();
    }

    public void generatePattern(final Integer difficulty) {
        this.screenSizeX = GameInfo.getInstance().getScreenWidth();
        this.screenSizeY = GameInfo.getInstance().getScreenHeight();
        this.totalPattern.clear();
        this.duration = 0;
        this.interval = 0;
        this.difficulty = difficulty;
        this.totalPattern.addAll(this.patternSpawner());
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

    private Pair<Double,Double> mapYDivisor(final Integer xDivisor, final Integer yDivisor) {
        return new Pair<Double, Double>(this.screenSizeX*xDivisor/100, this.screenSizeY*yDivisor/100);
    }



    private List<Pair<GenericController<Obstacle,ObstacleView>, Integer>> patternSpawner() {
        this.duration=20;
        final List<Pair<GenericController<Obstacle,ObstacleView>, Integer>> listOfObstacles = new ArrayList<>();

        final Integer random = new Random().nextInt(3);
        //this.patternSelector.get(this.difficulty*3-random).pattern(listOfMissiles);
        this.patternSelector.get(4).pattern(listOfObstacles);
        return listOfObstacles;
        
    }

    private GenericController<Obstacle,ObstacleView> singleMissile(final Pair<Double,Double> pos, final List<MovementChangers> modifiers) {
        final GenericController<Obstacle,ObstacleView> obstacleController = this.entityGenerator.generateObstacle(ObstacleType.MISSILE, new MovementImpl(pos,new Pair<>(0.0, 0.0), new Pair<>(0.0, 0.0), new Pair<>(0.0, 0.0), modifiers)).get(0);
        return obstacleController;
    }

    private GenericController<Obstacle,ObstacleView> singleZapper(final Pair<Double,Double> pos, final Pair<Double,Double> rotation) {
        final GenericController<Obstacle,ObstacleView> obstacleController = this.entityGenerator.generateObstacle(ObstacleType.ZAPPER, new MovementImpl(pos,new Pair<>(Double.valueOf(-this.difficulty)-5, 0.0), new Pair<>(0.0, 0.0), rotation, List.of())).get(0);
        return obstacleController;
    }

    private List<Pair<GenericController<Obstacle,ObstacleView>, Integer>> stairsPattern(final ObstacleType obstacleType, final boolean verse, final Integer number) {
        final List<GenericController<Obstacle,ObstacleView>> obstacleControllers = new ArrayList<>();
        for(int i=0; i<number; i++) {
            obstacleControllers.add(this.singleMissile(this.mapYDivisor(95, verse ? 10 + i*10 : 90 - i*10), List.of()));
        }
        List<Pair<GenericController<Obstacle,ObstacleView>, Integer>> listOfObstacles = new ArrayList<>();
        for(int i=0; i<number; i++) {
            listOfObstacles.add(new Pair<>(obstacleControllers.get(i), i+1));
        }
        return listOfObstacles;
    }


    private void loadPatterns() {
        this.patternSelector = new HashMap<>();
        this.patternSelector.put(1, (l) -> { 
            l.add(new Pair<>(this.singleMissile(this.mapYDivisor(95, 15),List.of()), 1));
            l.add(new Pair<>(this.singleMissile(this.mapYDivisor(95, 30),List.of()), 1));
        }); 
        this.patternSelector.put(2, (l) -> { 
            l.add(new Pair<>(this.singleMissile(this.mapYDivisor(95, 80),List.of()), 1));
            l.add(new Pair<>(this.singleMissile(this.mapYDivisor(95, 15),List.of()), 1));
            l.add(new Pair<>(this.singleZapper(this.mapYDivisor(105, 45),new Pair<>(0.0,0.0)), 3));

        }); 
        this.patternSelector.put(3, (l) -> { 
            l.add(new Pair<>(this.singleMissile(this.mapYDivisor(95, 45),List.of()), 1));
            l.add(new Pair<>(this.singleMissile(this.mapYDivisor(95, 65),List.of()), 1));
        }); 
        this.patternSelector.put(4, (l) -> { 
            l.add(new Pair<>(this.singleMissile(this.mapYDivisor(95, 50),List.of()), 1));
            l.add(new Pair<>(this.singleZapper(this.mapYDivisor(105, 20),new Pair<>(90.0,0.0)), 3));
            l.add(new Pair<>(this.singleZapper(this.mapYDivisor(105, 80),new Pair<>(90.0,0.0)), 3));
        }); 
        this.patternSelector.put(5, (l) -> { 
            l.add(new Pair<>(this.singleMissile(this.mapYDivisor(95, 75),List.of(MovementChangers.DIAGONALDOWN, MovementChangers.BOUNCING)), 1));
            l.add(new Pair<>(this.singleMissile(this.mapYDivisor(95, 15),List.of()), 3));
            l.add(new Pair<>(this.singleMissile(this.mapYDivisor(95, 25),List.of()), 5));
        }); 
        this.patternSelector.put(6, (l) -> { 
            l.add(new Pair<>(this.singleMissile(this.mapYDivisor(95, 40),List.of()), 1));
            l.add(new Pair<>(this.singleMissile(this.mapYDivisor(95, 30),List.of(MovementChangers.DIAGONALDOWN, MovementChangers.DIAGONALUP)), 1));
        }); 
        this.patternSelector.put(7, (l) -> { 
            l.add(new Pair<>(this.singleMissile(this.mapYDivisor(95, 15),List.of()), 1));
            l.add(new Pair<>(this.singleMissile(this.mapYDivisor(95, 85),List.of()), 3));
        }); 
        this.patternSelector.put(8, (l) -> { 
            l.add(new Pair<>(this.singleMissile(this.mapYDivisor(95, 10),List.of(MovementChangers.DIAGONALDOWN, MovementChangers.BOUNCING)), 1));
            l.add(new Pair<>(this.singleMissile(this.mapYDivisor(95, 20),List.of(MovementChangers.DIAGONALDOWN, MovementChangers.BOUNCING)), 1));
            l.add(new Pair<>(this.singleMissile(this.mapYDivisor(95, 80),List.of(MovementChangers.DIAGONALUP, MovementChangers.BOUNCING)), 1));
            l.add(new Pair<>(this.singleMissile(this.mapYDivisor(95, 90),List.of(MovementChangers.DIAGONALUP, MovementChangers.BOUNCING)), 1));
        }); 
        this.patternSelector.put(9, (l) -> { 
            l.add(new Pair<>(this.singleMissile(this.mapYDivisor(95, 90),List.of()), 1));
        }); 
        this.patternSelector.put(10, (l) -> { 
            l.add(new Pair<>(this.singleMissile(this.mapYDivisor(95, 90),List.of()), 1));
        }); 
        this.patternSelector.put(11, (l) -> { 
            l.add(new Pair<>(this.singleMissile(this.mapYDivisor(95, 45),List.of()), 1));
            l.add(new Pair<>(this.singleMissile(this.mapYDivisor(95, 55),List.of(MovementChangers.DIAGONALDOWN, MovementChangers.BOUNCING)), 3));
            l.add(new Pair<>(this.singleMissile(this.mapYDivisor(95, 65),List.of()), 1));
        }); 
        this.patternSelector.put(12, (l) -> { 
            l.add(new Pair<>(this.singleMissile(this.mapYDivisor(95, 90),List.of()), 1));
            l.add(new Pair<>(this.singleMissile(this.mapYDivisor(95, 90),List.of()), 1));
        }); 
        this.patternSelector.put(13, (l) -> { 
            l.addAll(this.stairsPattern(ObstacleType.MISSILE, true, 3));
        }); 
        this.patternSelector.put(14, (l) -> { 
            l.add(new Pair<>(this.singleMissile(this.mapYDivisor(95, 90),List.of()), 1));
        }); 
        this.patternSelector.put(15, (l) -> { 
            l.add(new Pair<>(this.singleMissile(this.mapYDivisor(95, 90),List.of()), 1));
        }); 
        this.patternSelector.put(16, (l) -> { 
            l.addAll(this.stairsPattern(ObstacleType.MISSILE, false, 5));
        }); 
        this.patternSelector.put(17, (l) -> { 
            l.add(new Pair<>(this.singleMissile(this.mapYDivisor(95, 90),List.of()), 1));
        }); 
        this.patternSelector.put(18, (l) -> { 
            l.add(new Pair<>(this.singleMissile(this.mapYDivisor(95, 90),List.of()), 1));
        }); 
        this.patternSelector.put(19, (l) -> { 
            l.addAll(this.stairsPattern(ObstacleType.MISSILE, true, 5));
        }); 
        this.patternSelector.put(20, (l) -> { 
            l.add(new Pair<>(this.singleMissile(this.mapYDivisor(95, 90),List.of()), 1));
        }); 
        this.patternSelector.put(21, (l) -> { 
            l.add(new Pair<>(this.singleMissile(this.mapYDivisor(95, 90),List.of()), 1));
        }); 
        this.patternSelector.put(22, (l) -> { 
            l.add(new Pair<>(this.singleMissile(this.mapYDivisor(95, 90),List.of()), 1));
        }); 
        this.patternSelector.put(23, (l) -> { 
            l.add(new Pair<>(this.singleMissile(this.mapYDivisor(95, 90),List.of()), 1));
        }); 
        this.patternSelector.put(24, (l) -> { 
            l.add(new Pair<>(this.singleMissile(this.mapYDivisor(95, 90),List.of()), 1));
        }); 
        this.patternSelector.put(25, (l) -> { 
            l.add(new Pair<>(this.singleMissile(this.mapYDivisor(95, 90),List.of()), 1));
        }); 
        this.patternSelector.put(26, (l) -> { 
            l.add(new Pair<>(this.singleMissile(this.mapYDivisor(95, 90),List.of()), 1));
        }); 
        this.patternSelector.put(27, (l) -> { 
            l.add(new Pair<>(this.singleMissile(this.mapYDivisor(95, 90),List.of()), 1));
        }); 
        this.patternSelector.put(28, (l) -> { 
            l.addAll(this.stairsPattern(ObstacleType.MISSILE, true, 8));
        }); 
        this.patternSelector.put(29, (l) -> { 
            l.addAll(this.stairsPattern(ObstacleType.MISSILE, false, 8));
        }); 
        this.patternSelector.put(30, (l) -> { 
            l.addAll(this.stairsPattern(ObstacleType.MISSILE, true, 4));
            l.addAll(this.stairsPattern(ObstacleType.MISSILE, false, 4));
        }); 

    }

}
