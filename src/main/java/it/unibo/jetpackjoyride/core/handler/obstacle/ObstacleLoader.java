package it.unibo.jetpackjoyride.core.handler.obstacle;

import java.util.*;

import it.unibo.jetpackjoyride.core.entities.entity.impl.EntityControllerGeneratorImpl;
import it.unibo.jetpackjoyride.core.entities.obstacle.api.Obstacle;
import it.unibo.jetpackjoyride.core.entities.obstacle.api.Obstacle.ObstacleType;
import it.unibo.jetpackjoyride.core.handler.generic.GenericController;
import it.unibo.jetpackjoyride.utilities.MovementChangers;
import it.unibo.jetpackjoyride.core.movement.Movement;
import it.unibo.jetpackjoyride.utilities.GameInfo;
import it.unibo.jetpackjoyride.utilities.Pair;
import java.util.stream.*;

@FunctionalInterface
interface PatternSelector {

    void pattern(List<Pair<GenericController<Obstacle, ObstacleView>, Integer>> list);
}

public class ObstacleLoader {
    private final EntityControllerGeneratorImpl entityGenerator;
    private final List<Pair<GenericController<Obstacle, ObstacleView>, Integer>> totalPattern;
    private Integer interval;
    private Integer duration;
    private Double screenSizeX;
    private Double screenSizeY;
    private Map<Integer, PatternSelector> patternSelector;
    private Integer difficulty;

    public ObstacleLoader() {
        this.entityGenerator = new EntityControllerGeneratorImpl();
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

    public List<GenericController<Obstacle, ObstacleView>> getInstanceOfPattern() {
        this.interval++;
        if (!this.hasFinished()) {
            final List<GenericController<Obstacle, ObstacleView>> instanceOfPattern = new ArrayList<>();
            instanceOfPattern.addAll(this.totalPattern.stream().filter(p -> p.get2().equals(this.interval))
                    .map(p -> p.get1()).collect(Collectors.toList()));

            return instanceOfPattern;
        }
        return List.of();
    }

    public boolean hasFinished() {
        return this.duration.equals(this.interval);
    }

    private Pair<Double, Double> mapYDivisor(final Integer xDivisor, final Integer yDivisor) {
        return new Pair<Double, Double>(xDivisor*1.0,  yDivisor*1.0);
    }

    private List<Pair<GenericController<Obstacle, ObstacleView>, Integer>> patternSpawner() {
        this.duration = 25;
        final List<Pair<GenericController<Obstacle, ObstacleView>, Integer>> listOfObstacles = new ArrayList<>();

        final Integer random = new Random().nextInt(3);
        var rand = this.difficulty * 3 - random;
        this.patternSelector.get(50).pattern(listOfObstacles);
       
        return listOfObstacles;
    }

    private GenericController<Obstacle, ObstacleView> singleMissile(final Pair<Double, Double> pos,
            final Pair<Double, Double> speed, final List<MovementChangers> modifiers) {
        return this.entityGenerator.generateObstacleController(ObstacleType.MISSILE,
        new Movement.Builder().setPosition(pos).setSpeed(speed).setMovementChangers(modifiers).build());
    }

    private GenericController<Obstacle, ObstacleView> singleZapper(final Pair<Double, Double> pos,
            final Pair<Double, Double> rotation) {

        return this.entityGenerator.generateObstacleController(ObstacleType.ZAPPER, 
                    new Movement.Builder().setPosition(pos).setSpeed(Double.valueOf(-this.difficulty*2), 0.0).setRotation(rotation).build());
    
    }

    private GenericController<Obstacle, ObstacleView> singleLaser(final Pair<Double, Double> pos,
            final Pair<Double, Double> speed) {
        return this.entityGenerator.generateObstacleController(ObstacleType.LASER,
                    new Movement.Builder().setPosition(pos).setSpeed(speed).build());

    }

    private List<Pair<GenericController<Obstacle, ObstacleView>, Integer>> missileStairs(final boolean dir,
            final Integer number, final Integer whenStarting) {
        return IntStream.range(0, number)
                .mapToObj(i -> new Pair<>(
                        this.singleMissile(this.mapYDivisor(95, dir ? 10 + i * 10 : 90 - i * 10), new Pair<>(0.0,0.0), List.of()),
                        whenStarting + i))
                .toList();
    }

    private void loadPatterns() {
        this.patternSelector = new HashMap<>();
        this.patternSelector.put(1, (l) -> {
            l.add(new Pair<>(this.singleMissile(this.mapYDivisor(95, 15), new Pair<>(0.0,0.0),List.of()), 1));
            l.add(new Pair<>(this.singleMissile(this.mapYDivisor(95, 30), new Pair<>(0.0,0.0),List.of()), 1));
        });
        this.patternSelector.put(2, (l) -> {
            l.add(new Pair<>(this.singleMissile(this.mapYDivisor(95, 85), new Pair<>(0.0,0.0), List.of()), 1));
            l.add(new Pair<>(this.singleMissile(this.mapYDivisor(95, 15), new Pair<>(0.0,0.0), List.of()), 1));
            l.add(new Pair<>(this.singleZapper(this.mapYDivisor(105, 50), new Pair<>(0.0, 0.0)), 3));

        });
        this.patternSelector.put(3, (l) -> {
            l.add(new Pair<>(this.singleLaser(this.mapYDivisor(50, 20), new Pair<>(0.0, 0.0)), 1));
            l.add(new Pair<>(this.singleMissile(this.mapYDivisor(95, 45), new Pair<>(0.0,0.0), List.of()), 1));
            l.add(new Pair<>(this.singleMissile(this.mapYDivisor(95, 65), new Pair<>(0.0,0.0), List.of()), 1));
        });
        this.patternSelector.put(4, (l) -> {
            l.add(new Pair<>(this.singleMissile(this.mapYDivisor(95, 50), new Pair<>(0.0,0.0), List.of()), 3));
            l.add(new Pair<>(this.singleZapper(this.mapYDivisor(105, 20), new Pair<>(90.0, 0.0)), 1));
            l.add(new Pair<>(this.singleZapper(this.mapYDivisor(105, 80), new Pair<>(90.0, 0.0)), 1));
        });
        this.patternSelector.put(5, (l) -> {
            l.add(new Pair<>(this.singleMissile(this.mapYDivisor(95, 75), new Pair<>(0.0, Double.valueOf(this.difficulty*2)), List.of(MovementChangers.BOUNCING)), 1));
            l.add(new Pair<>(this.singleMissile(this.mapYDivisor(95, 15), new Pair<>(0.0,0.0), List.of()), 3));
            l.add(new Pair<>(this.singleMissile(this.mapYDivisor(95, 25), new Pair<>(0.0,0.0), List.of()), 5));
        });
        this.patternSelector.put(6, (l) -> {
            l.add(new Pair<>(this.singleLaser(this.mapYDivisor(50, 70), new Pair<>(0.0, 0.0)), 3));
            l.add(new Pair<>(this.singleLaser(this.mapYDivisor(50, 60), new Pair<>(0.0, 0.0)), 3));
            l.add(new Pair<>(this.singleMissile(this.mapYDivisor(95, 40), new Pair<>(0.0,0.0), List.of()), 1));
            l.add(new Pair<>(this.singleMissile(this.mapYDivisor(95, 30), new Pair<>(0.0, Double.valueOf(this.difficulty*2)), List.of(MovementChangers.BOUNCING)), 1));
        });
        this.patternSelector.put(7, (l) -> {
            l.add(new Pair<>(this.singleMissile(this.mapYDivisor(95, 15), new Pair<>(0.0,0.0), List.of()), 1));
            l.add(new Pair<>(this.singleMissile(this.mapYDivisor(95, 85), new Pair<>(0.0,0.0), List.of()), 3));
            l.addAll(this.missileStairs(true, 5, 5));
        });
        this.patternSelector.put(8, (l) -> {
            l.add(new Pair<>(this.singleMissile(this.mapYDivisor(95, 10),new Pair<>(0.0, Double.valueOf(this.difficulty*2)), List.of(MovementChangers.BOUNCING)), 1));
            l.add(new Pair<>(this.singleMissile(this.mapYDivisor(95, 20),new Pair<>(0.0, Double.valueOf(this.difficulty*2)), List.of(MovementChangers.BOUNCING)), 1));
            l.add(new Pair<>(this.singleMissile(this.mapYDivisor(95, 80),new Pair<>(0.0, -Double.valueOf(this.difficulty*2)), List.of(MovementChangers.BOUNCING)), 1));
            l.add(new Pair<>(this.singleMissile(this.mapYDivisor(95, 90),new Pair<>(0.0, -Double.valueOf(this.difficulty*2)), List.of(MovementChangers.BOUNCING)), 1));
        });
        this.patternSelector.put(9, (l) -> {
            l.add(new Pair<>(this.singleZapper(this.mapYDivisor(105, 10), new Pair<>(0.0, 0.0)), 2));
            l.add(new Pair<>(this.singleZapper(this.mapYDivisor(105, 20), new Pair<>(90.0, 0.0)), 1));
            l.add(new Pair<>(this.singleZapper(this.mapYDivisor(105, 80), new Pair<>(90.0, 0.0)), 1));
            l.add(new Pair<>(this.singleZapper(this.mapYDivisor(105, 90), new Pair<>(0.0, 0.0)), 2));

            l.add(new Pair<>(this.singleZapper(this.mapYDivisor(105, 10), new Pair<>(0.0, 0.0)), 6));
            l.add(new Pair<>(this.singleZapper(this.mapYDivisor(105, 20), new Pair<>(90.0, 0.0)), 7));
            l.add(new Pair<>(this.singleZapper(this.mapYDivisor(105, 80), new Pair<>(90.0, 0.0)), 7));
            l.add(new Pair<>(this.singleZapper(this.mapYDivisor(105, 90), new Pair<>(0.0, 0.0)), 6));
        });
        this.patternSelector.put(10, (l) -> {
            l.add(new Pair<>(this.singleMissile(this.mapYDivisor(95, 15), new Pair<>(0.0,0.0), List.of()), 1));
            l.add(new Pair<>(this.singleZapper(this.mapYDivisor(105, 40), new Pair<>(90.0, 2.0)), 1));
            l.add(new Pair<>(this.singleZapper(this.mapYDivisor(105, 60), new Pair<>(90.0, -2.0)), 3));
            l.add(new Pair<>(this.singleZapper(this.mapYDivisor(105, 50), new Pair<>(90.0, 2.0)), 5));
            l.addAll(this.missileStairs(true, 3, 7));
            l.addAll(this.missileStairs(false, 3, 9));
            l.addAll(this.missileStairs(true, 3, 11));
        });
        this.patternSelector.put(11, (l) -> {
            l.add(new Pair<>(this.singleMissile(this.mapYDivisor(95, 45), new Pair<>(0.0,0.0), List.of()), 1));
            l.add(new Pair<>(this.singleMissile(this.mapYDivisor(95, 55),new Pair<>(0.0, Double.valueOf(this.difficulty*2)), List.of(MovementChangers.BOUNCING)), 3));
            l.add(new Pair<>(this.singleMissile(this.mapYDivisor(95, 65), new Pair<>(0.0,0.0), List.of()), 1));
        });
        this.patternSelector.put(12, (l) -> {
            l.add(new Pair<>(this.singleMissile(this.mapYDivisor(95, 75), new Pair<>(0.0,0.0), List.of()), 1));
            l.add(new Pair<>(this.singleMissile(this.mapYDivisor(95, 90), new Pair<>(0.0,0.0), List.of()), 1));
            l.add(new Pair<>(this.singleLaser(this.mapYDivisor(50, 15), new Pair<>(0.0, 0.0)), 3));
            l.add(new Pair<>(this.singleLaser(this.mapYDivisor(50, 25), new Pair<>(0.0, 0.0)), 5));
            l.add(new Pair<>(this.singleLaser(this.mapYDivisor(50, 35), new Pair<>(0.0, 0.0)), 7));
        });
        this.patternSelector.put(13, (l) -> {
            l.addAll(this.missileStairs(true, 3, 1));
            l.add(new Pair<>(this.singleLaser(this.mapYDivisor(50, 80), new Pair<>(0.0, 0.0)), 3));
            l.add(new Pair<>(this.singleLaser(this.mapYDivisor(50, 70), new Pair<>(0.0, 0.0)), 3));
        });
        this.patternSelector.put(14, (l) -> {
            this.duration = 35;
            for (int i = 0; i < 20; i++) {
                l.add(new Pair<>(this.singleMissile(this.mapYDivisor(95, new Random().nextInt(81) + 10), new Pair<>(0.0,0.0), List.of()),
                        1 + i));
            }
        });
        this.patternSelector.put(15, (l) -> {
            this.duration = 35;
            IntStream.range(0, 6).forEach(i -> l
                    .add(new Pair<>(this.singleLaser(this.mapYDivisor(50, 10 + i * 10), new Pair<>(0.0, 0.0)), 1)));
            IntStream.range(0, 6).forEach(i -> l
                    .add(new Pair<>(this.singleLaser(this.mapYDivisor(50, 90 - i * 10), new Pair<>(0.0, 0.0)), 17)));
        });
        this.patternSelector.put(16, (l) -> {
            l.addAll(this.missileStairs(false, 5, 1));
            IntStream.range(0, 4)
                    .forEach(i -> l.add(new Pair<>(this.singleMissile(this.mapYDivisor(95, 20 + i * 10),new Pair<>(0.0, -Double.valueOf(this.difficulty*2)), List.of(MovementChangers.BOUNCING)),1 + i * 2)));
        });
        this.patternSelector.put(17, (l) -> {

            IntStream.range(0, 4).forEach(
                    i -> l.add(new Pair<>(this.singleZapper(this.mapYDivisor(105, 15 + i * 15), new Pair<>(30.0, 0.0)),
                            3 + i * 2)));
            IntStream.range(0, 4).forEach(
                    i -> l.add(new Pair<>(this.singleZapper(this.mapYDivisor(105, 45 + i * 15), new Pair<>(30.0, 0.0)),
                            1 + i * 2)));
        });
        this.patternSelector.put(18, (l) -> {
            l.add(new Pair<>(this.singleLaser(this.mapYDivisor(50, 50), new Pair<>(0.0, 0.0)), 3));
            l.add(new Pair<>(this.singleLaser(this.mapYDivisor(50, 20), new Pair<>(0.0, 0.0)), 15));
            l.add(new Pair<>(this.singleLaser(this.mapYDivisor(50, 80), new Pair<>(0.0, 0.0)), 7));
            l.add(new Pair<>(this.singleMissile(this.mapYDivisor(95, 90), new Pair<>(0.0,0.0), List.of()), 1));
        });
        this.patternSelector.put(19, (l) -> {
            this.duration = 30;
            l.addAll(this.missileStairs(true, 5, 1));
            l.addAll(this.missileStairs(false, 5, 7));
            l.addAll(this.missileStairs(true, 5, 13));
            l.addAll(this.missileStairs(false, 5, 19));
        });
        this.patternSelector.put(20, (l) -> {
            l.add(new Pair<>(this.singleZapper(this.mapYDivisor(105, 30), new Pair<>(15.0, 0.0)), 1));
            l.add(new Pair<>(this.singleZapper(this.mapYDivisor(105, 15), new Pair<>(45.0, 0.0)), 3));
            l.add(new Pair<>(this.singleZapper(this.mapYDivisor(105, 45), new Pair<>(60.0, 0.0)), 5));

            l.add(new Pair<>(this.singleMissile(this.mapYDivisor(95, 90), new Pair<>(0.0,0.0), List.of()), 7));

            l.add(new Pair<>(this.singleZapper(this.mapYDivisor(105, 30), new Pair<>(25.0, 0.0)), 5));
            l.add(new Pair<>(this.singleZapper(this.mapYDivisor(105, 60), new Pair<>(20.0, 0.0)), 7));
            l.add(new Pair<>(this.singleZapper(this.mapYDivisor(105, 45), new Pair<>(90.0, 0.0)), 9));

            l.add(new Pair<>(this.singleMissile(this.mapYDivisor(95, 20), new Pair<>(0.0,0.0), List.of()), 7));

            l.add(new Pair<>(this.singleZapper(this.mapYDivisor(105, 30), new Pair<>(0.0, 0.0)), 9));
            l.add(new Pair<>(this.singleZapper(this.mapYDivisor(105, 55), new Pair<>(15.0, 0.0)), 12));
            l.add(new Pair<>(this.singleZapper(this.mapYDivisor(105, 45), new Pair<>(35.0, 0.0)), 15));

        });
        this.patternSelector.put(21, (l) -> {
            l.add(new Pair<>(this.singleLaser(this.mapYDivisor(50, 10), new Pair<>(0.0, 0.5)), 1));
            l.add(new Pair<>(this.singleLaser(this.mapYDivisor(50, 90), new Pair<>(0.0, -0.5)), 1));
            l.add(new Pair<>(this.singleLaser(this.mapYDivisor(50, 50), new Pair<>(0.0, 0.0)), 15));
            l.add(new Pair<>(this.singleMissile(this.mapYDivisor(95, 90), new Pair<>(0.0,0.0), List.of()), 1));
            l.add(new Pair<>(this.singleMissile(this.mapYDivisor(95, 10), new Pair<>(0.0,0.0), List.of()), 1));
        });
        this.patternSelector.put(22, (l) -> {
            l.add(new Pair<>(this.singleMissile(this.mapYDivisor(95, 90), new Pair<>(0.0,0.0), List.of()), 1));
        });
        this.patternSelector.put(23, (l) -> {
            this.duration = 40;
            IntStream.range(0, 12).forEach(
                    i -> l.add(new Pair<>(this.singleZapper(this.mapYDivisor(105, 15 + new Random().nextInt(71)),
                            new Pair<>(0.0, new Random().nextDouble(-3.0, 3.0))), 1 + i * 3)));
        });
        this.patternSelector.put(24, (l) -> {
            l.add(new Pair<>(this.singleMissile(this.mapYDivisor(95, 90), new Pair<>(0.0,0.0), List.of()), 1));
            l.add(new Pair<>(this.singleLaser(this.mapYDivisor(50, 70), new Pair<>(0.0, 0.0)), 5));
            l.add(new Pair<>(this.singleLaser(this.mapYDivisor(50, 45), new Pair<>(0.0, 0.0)), 5));

            l.addAll(this.missileStairs(true, 3, 15));
        });
        this.patternSelector.put(25, (l) -> {
            this.duration = 30;
            IntStream.range(0, 5)
                    .forEach(i -> l.add(new Pair<>(this.singleMissile(this.mapYDivisor(95, 10 + i * 10), new Pair<>(0.0, Double.valueOf(this.difficulty*2)), List.of(MovementChangers.BOUNCING)),1 + i)));
            IntStream.range(0, 5)
                    .forEach(i -> l.add(new Pair<>(this.singleMissile(this.mapYDivisor(95, 90 - i * 10), new Pair<>(0.0, -Double.valueOf(this.difficulty*2)), List.of(MovementChangers.BOUNCING)), 10 + i)));
            l.addAll(this.missileStairs(false, 6, 20));
        });
        this.patternSelector.put(26, (l) -> {
            this.duration = 60;
            l.add(new Pair<>(this.singleLaser(this.mapYDivisor(50, 15), new Pair<>(0.0, 0.0)), 1));
            l.add(new Pair<>(this.singleLaser(this.mapYDivisor(50, 15), new Pair<>(0.0, 0.0)), 12));
            l.add(new Pair<>(this.singleLaser(this.mapYDivisor(50, 15), new Pair<>(0.0, 0.0)), 24));

            l.add(new Pair<>(this.singleLaser(this.mapYDivisor(50, 15), new Pair<>(0.0, 0.60)), 1));
            l.add(new Pair<>(this.singleLaser(this.mapYDivisor(50, 35), new Pair<>(0.0, 0.60)), 17));
            l.add(new Pair<>(this.singleLaser(this.mapYDivisor(50, 54), new Pair<>(0.0, 0.60)), 32));

            l.add(new Pair<>(this.singleLaser(this.mapYDivisor(50, 85), new Pair<>(0.0, 0.0)), 24));
            l.add(new Pair<>(this.singleLaser(this.mapYDivisor(50, 85), new Pair<>(0.0, 0.0)), 12));
            l.add(new Pair<>(this.singleLaser(this.mapYDivisor(50, 85), new Pair<>(0.0, 0.0)), 1));
        });
        this.patternSelector.put(27, (l) -> {
            l.add(new Pair<>(this.singleZapper(this.mapYDivisor(105, 10), new Pair<>(0.0, 0.0)), 2));
            l.add(new Pair<>(this.singleZapper(this.mapYDivisor(105, 20), new Pair<>(90.0, 0.0)), 1));
            l.add(new Pair<>(this.singleZapper(this.mapYDivisor(105, 80), new Pair<>(90.0, 0.0)), 1));
            l.add(new Pair<>(this.singleZapper(this.mapYDivisor(105, 90), new Pair<>(0.0, 0.0)), 2));

            l.add(new Pair<>(this.singleZapper(this.mapYDivisor(105, 50), new Pair<>(0.0, 10.0)), 4));

            l.add(new Pair<>(this.singleZapper(this.mapYDivisor(105, 10), new Pair<>(0.0, 0.0)), 6));
            l.add(new Pair<>(this.singleZapper(this.mapYDivisor(105, 20), new Pair<>(90.0, 0.0)), 7));
            l.add(new Pair<>(this.singleZapper(this.mapYDivisor(105, 80), new Pair<>(90.0, 0.0)), 7));
            l.add(new Pair<>(this.singleZapper(this.mapYDivisor(105, 90), new Pair<>(0.0, 0.0)), 6));
            l.add(new Pair<>(this.singleMissile(this.mapYDivisor(95, 40), new Pair<>(0.0,0.0), List.of()), 1));
            l.add(new Pair<>(this.singleMissile(this.mapYDivisor(95, 50), new Pair<>(0.0,0.0), List.of()), 1));
            l.add(new Pair<>(this.singleMissile(this.mapYDivisor(95, 60), new Pair<>(0.0,0.0), List.of()), 1));
            l.addAll(this.missileStairs(false, 3, 3));
            l.addAll(this.missileStairs(true, 3, 3));

        });
        this.patternSelector.put(28, (l) -> {
            l.add(new Pair<>(this.singleZapper(this.mapYDivisor(105, 10), new Pair<>(90.0, 0.0)), 1));
            l.add(new Pair<>(this.singleZapper(this.mapYDivisor(105, 30), new Pair<>(90.0, 0.0)), 1));
            l.add(new Pair<>(this.singleZapper(this.mapYDivisor(105, 50), new Pair<>(90.0, 0.0)), 1));

            l.add(new Pair<>(this.singleZapper(this.mapYDivisor(105, 90), new Pair<>(90.0, 0.0)), 5));
            l.add(new Pair<>(this.singleZapper(this.mapYDivisor(105, 70), new Pair<>(90.0, 0.0)), 5));
            l.add(new Pair<>(this.singleZapper(this.mapYDivisor(105, 50), new Pair<>(90.0, 0.0)), 5));

            l.add(new Pair<>(this.singleZapper(this.mapYDivisor(105, 10), new Pair<>(90.0, 0.0)), 9));
            l.add(new Pair<>(this.singleZapper(this.mapYDivisor(105, 30), new Pair<>(90.0, 0.0)), 9));
            l.add(new Pair<>(this.singleZapper(this.mapYDivisor(105, 50), new Pair<>(90.0, 0.0)), 9));
        });
        this.patternSelector.put(29, (l) -> {
            this.duration = 30;
            l.addAll(this.missileStairs(false, 3, 1));
            l.addAll(this.missileStairs(true, 7, 7));
            l.add(new Pair<>(this.singleLaser(this.mapYDivisor(50, 10), new Pair<>(0.0, 0.0)), 1));
            l.add(new Pair<>(this.singleLaser(this.mapYDivisor(50, 20), new Pair<>(0.0, 0.0)), 5));
            l.add(new Pair<>(this.singleLaser(this.mapYDivisor(50, 30), new Pair<>(0.0, 0.0)), 9));
            l.add(new Pair<>(this.singleLaser(this.mapYDivisor(50, 40), new Pair<>(0.0, 0.0)), 13));
            l.add(new Pair<>(this.singleLaser(this.mapYDivisor(50, 50), new Pair<>(0.0, 0.0)), 17));

            l.add(new Pair<>(this.singleZapper(this.mapYDivisor(105, 80), new Pair<>(90.0, 0.0)), 25));
            l.add(new Pair<>(this.singleZapper(this.mapYDivisor(105, 60), new Pair<>(90.0, 0.0)), 25));
            l.add(new Pair<>(this.singleZapper(this.mapYDivisor(105, 40), new Pair<>(90.0, 0.0)), 25));
        });
        this.patternSelector.put(30, (l) -> {
            this.duration = 30;
            l.addAll(this.missileStairs(true, 4, 1));
            l.addAll(this.missileStairs(false, 4, 10));
            l.add(new Pair<>(this.singleZapper(this.mapYDivisor(105, 10), new Pair<>(90.0, 0.0)), 12));
            l.add(new Pair<>(this.singleZapper(this.mapYDivisor(105, 30), new Pair<>(90.0, 0.0)), 12));
            l.add(new Pair<>(this.singleZapper(this.mapYDivisor(105, 50), new Pair<>(90.0, 0.0)), 12));

            l.add(new Pair<>(this.singleLaser(this.mapYDivisor(50, 70), new Pair<>(0.0, 0.0)), 1));
            l.add(new Pair<>(this.singleLaser(this.mapYDivisor(50, 80), new Pair<>(0.0, 0.0)), 1));
            l.add(new Pair<>(this.singleLaser(this.mapYDivisor(50, 90), new Pair<>(0.0, 0.0)), 1));
        });
        this.patternSelector.put(50, (l) -> {
            this.duration = 30;
            
            l.add(new Pair<>(this.singleMissile(this.mapYDivisor(1300, 360),new Pair<>(0.0, Double.valueOf((this.difficulty+5)*2)), List.of(MovementChangers.BOUNCING)), 3));
        });

    }

}
