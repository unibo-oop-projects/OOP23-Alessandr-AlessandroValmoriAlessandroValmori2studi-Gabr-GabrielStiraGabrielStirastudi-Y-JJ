package it.unibo.jetpackjoyride.core.handler.obstacle;
import java.util.*;

import it.unibo.jetpackjoyride.core.entities.entity.api.EntityGenerator;
import it.unibo.jetpackjoyride.core.entities.entity.impl.EntityGeneratorImpl;
import it.unibo.jetpackjoyride.core.entities.obstacle.api.Obstacle;
import it.unibo.jetpackjoyride.core.entities.obstacle.api.Obstacle.ObstacleType;
import it.unibo.jetpackjoyride.core.handler.generic.GenericController;
import it.unibo.jetpackjoyride.core.movement.Movement;
import it.unibo.jetpackjoyride.core.movement.Movement.MovementChangers;
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
        this.totalPattern.addAll(this.patternSpawner(difficulty));
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



    private List<Pair<GenericController<Obstacle,ObstacleView>, Integer>> patternSpawner(final Integer difficulty) {
        this.duration +=20;

        final List<Pair<GenericController<Obstacle,ObstacleView>, Integer>> listOfMissiles = new ArrayList<>();

        this.patternSelector.get(30).pattern(listOfMissiles);

        /*
        //Integer ciao = 1;
        switch (difficulty) {
            case 1:
            listOfMissiles.addAll(this.missilePatternsStairs(true, 7));
                listOfMissiles.add(new Pair<>(this.missilePatternOne(this.mapYDivisor(95, 15),List.of()), 1));
                listOfMissiles.add(new Pair<>(this.missilePatternOne(this.mapYDivisor(95, 45),List.of()), 1));
                listOfMissiles.add(new Pair<>(this.missilePatternOne(this.mapYDivisor(95, 30),List.of()), 5));
                listOfMissiles.add(new Pair<>(this.missilePatternOne(this.mapYDivisor(95, 60),List.of()), 5));
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                break;
            case 7:
                break;
            case 8:
                break;
            case 9:
                break;
            case 10:
                listOfMissiles.addAll(this.missilePatternsStairs(true, 7));
                listOfMissiles.addAll(this.missilePatternsStairs(false, 7));
                break;

        
            default:
                break;
        }*/

        return listOfMissiles;
        
    }

    private GenericController<Obstacle,ObstacleView> missilePatternOne(final Pair<Double,Double> yPos, final List<MovementChangers> modifiers) {
        final List<GenericController<Obstacle,ObstacleView>> obstacleControllers = this.entityGenerator.generateObstacle(ObstacleType.MISSILE, new MovementImpl(yPos,new Pair<>(0.0, 0.0), new Pair<>(0.0, 0.0), new Pair<>(0.0, 0.0), modifiers));
        return obstacleControllers.get(0);
    }

    private List<Pair<GenericController<Obstacle,ObstacleView>, Integer>> missilePatternsStairs(final boolean verse, final Integer number) {

        final List<GenericController<Obstacle,ObstacleView>> obstacleControllers = new ArrayList<>();

        for(int i=0; i<number; i++) {
            obstacleControllers.add(this.missilePatternOne(this.mapYDivisor(95, verse ? 10 + i*10 : 90 - i*10), List.of()));
        }

        List<Pair<GenericController<Obstacle,ObstacleView>, Integer>> listOfMissiles = new ArrayList<>();
        for(int i=0; i<number; i++) {
            listOfMissiles.add(new Pair<>(obstacleControllers.get(i), i+1));
        }
                
        return listOfMissiles;
    }


    private void loadPatterns() {
        this.patternSelector = new HashMap<>();
        this.patternSelector.put(1, (l) -> { 
            l.add(new Pair<>(this.missilePatternOne(this.mapYDivisor(95, 15),List.of()), 1));
            l.add(new Pair<>(this.missilePatternOne(this.mapYDivisor(95, 30),List.of()), 1));
        }); 
        this.patternSelector.put(2, (l) -> { 
            l.add(new Pair<>(this.missilePatternOne(this.mapYDivisor(95, 20),List.of()), 1));
            l.add(new Pair<>(this.missilePatternOne(this.mapYDivisor(95, 70),List.of()), 5));
        }); 
        this.patternSelector.put(28, (l) -> { 
            l.addAll(this.missilePatternsStairs(true, 7));
        }); 
        this.patternSelector.put(29, (l) -> { 
            l.addAll(this.missilePatternsStairs(false, 7));
        }); 
        this.patternSelector.put(30, (l) -> { 
            l.addAll(this.missilePatternsStairs(true, 4));
            l.addAll(this.missilePatternsStairs(false, 4));
        }); 

    }

}
