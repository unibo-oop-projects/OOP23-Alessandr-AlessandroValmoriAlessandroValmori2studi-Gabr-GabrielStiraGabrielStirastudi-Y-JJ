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
            final Integer difficulty = GameInfo.moveSpeed.get() - GameInfo.getInstance().getInitialGameSpeed() + 1;
            this.obstacleLoader.generatePattern(difficulty);
        }
        return this.obstacleLoader.getInstanceOfPattern();
        //return randomChunk();
    }
/* 
    private List<GenericController<Obstacle,ObstacleView>> randomChunk() {
        final Double screenSizeX = GameInfo.getInstance().getScreenWidth();
        final Double screenSizeY = GameInfo.getInstance().getScreenHeight();
        final Double gameMovingSpeed = Double.valueOf(GameInfo.moveSpeed.get());

        List<GenericController<Obstacle,ObstacleView>> obstacleControllers = new ArrayList<>();
        Random random = new Random();
        int numberOfObstacles = 10;

        for (int i = 0; i < numberOfObstacles; i++) {
            int typeOfObstacle = 0;
            int typeOfMovement = random.nextInt(4);
            Movement movement;
            ObstacleType obstacleType;

            switch (typeOfObstacle) {
                case 0:
                    // MISSILE
                    movement = new MovementImpl(new Pair<>(screenSizeX - screenSizeX/20, screenSizeY/6 + random.nextDouble(screenSizeY-screenSizeY/6)),
                            new Pair<>(0.0, 0.0), new Pair<>(0.0, 0.0), new Pair<>(0.0, 0.0), List.of(
                                    typeOfMovement == 0 ? MovementChangers.GRAVITY
                                            : typeOfMovement == 1 ? MovementChangers.SLOW
                                                    : typeOfMovement == 2 ?MovementChangers.SPEEDY :
                                    MovementChangers.BOUNCING, MovementChangers.SPEEDY));
                    obstacleType = ObstacleType.MISSILE;
                    break;
                case 1:
                    // ZAPPER
                    movement = new MovementImpl(new Pair<>(screenSizeX, random.nextDouble() * screenSizeY),
                            new Pair<>(-gameMovingSpeed, 0.0), new Pair<>(0.0, 0.0),
                            new Pair<>(random.nextDouble() * 180,
                                    random.nextInt(2) == 0 ? 0.0 : random.nextDouble() * 5),
                            List.of());
                    obstacleType = ObstacleType.ZAPPER;
                    break;
                case 2:
                    // LASER
                    movement = new MovementImpl(new Pair<>(screenSizeX / 2, random.nextDouble() * screenSizeY),
                            new Pair<>(0.0, 0.0), new Pair<>(0.0, 0.0), new Pair<>(0.0, 0.0),
                            List.of(MovementChangers.STATIC));
                    obstacleType = ObstacleType.LASER;
                    break;
                default:
                    throw new IllegalStateException();
            }
            obstacleControllers.add(this.entityGenerator.generateObstacle(obstacleType, movement));
        }
        return obstacleControllers;
    }

    */
}
