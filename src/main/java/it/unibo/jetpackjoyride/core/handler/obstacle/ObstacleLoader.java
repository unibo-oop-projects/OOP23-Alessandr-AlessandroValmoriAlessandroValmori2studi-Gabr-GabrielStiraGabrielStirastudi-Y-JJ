package it.unibo.jetpackjoyride.core.handler.obstacle;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.List;
import java.util.Random;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;
import it.unibo.jetpackjoyride.core.entities.entity.api.EntityModelGenerator;
import it.unibo.jetpackjoyride.core.entities.entity.impl.EntityModelGeneratorImpl;
import it.unibo.jetpackjoyride.core.entities.obstacle.api.Obstacle;
import it.unibo.jetpackjoyride.core.entities.obstacle.api.Obstacle.ObstacleType;
import it.unibo.jetpackjoyride.core.movement.Movement;
import it.unibo.jetpackjoyride.utilities.GameInfo;
import it.unibo.jetpackjoyride.utilities.Pair;
import it.unibo.jetpackjoyride.utilities.exceptions.InvalidDataFormatException;
import it.unibo.jetpackjoyride.utilities.exceptions.NotImplementedObjectException;
import java.nio.charset.Charset;
import java.io.InputStreamReader;

/**
 * The {@link ObstacleLoader} class is used by {@link ObstacleHandler} to
 * generate
 * the obstacle entities. Firstly, this class tries to load some patterns of
 * obstacles
 * from a file. A pattern is a series of obstacles which all have certain
 * movement
 * characteristics and a tick associated to them, which defines how long it will
 * take
 * for the obstacle to be generated after the other of the same pattern.
 * If this procedure fails, this class has a built-in method to generate random
 * patterns
 * of obstacles.
 *
 * @author gabriel.stira@studio.unibo.it
 */
public final class ObstacleLoader {
    /**
     * Defines the maximum number of patterns that can be generated by this class.
     */
    private static final Integer MAX_NUMBER_OF_PATTERNS = 30;
    /**
     * Defines the minimum number of obstacles which spawns in an instance of a
     * random pattern.
     */
    private static final Integer MIN_OBSTACLES_FOR_INSTANCE = 3;
    /**
     * Defines the maximum number of obstacles which spawns in an instance of a
     * random pattern.
     */
    private static final Integer MAX_OBSTACLES_FOR_INSTANCE = 6;
    /**
     * Defines how many types of obstacles can be generated by this class.
     */
    private static final Integer TYPES_OF_OBSTACLES = 3;
    /**
     * Defines how many ticks pass before a pattern and another.
     */
    private static final Integer TIME_OF_LAZYNESS = 10;
    /**
     * Defines a default speed the obstacles of a random generated pattern move.
     */
    private static final Double DEFAULT_SPEED_OF_OBSTACLES = 5.0;
    /**
     * Defines how many ticks the lenght of a random generated pattern can be.
     */
    private static final Integer MAX_TICK_FOR_OBSTACLE_SPAWN = 20;
    /**
     * Defines the range of the Y coordinate an obstacle of a random generate
     * pattern
     * can spawn in between.
     */
    private static final Double Y_MAP_DIMENSION = 720.0;
    /**
     * Defines a modfier used to increase the variety of patterns.
     * A bigger number means more patterns can be chosen at random
     * between the ones that are in range [difficulty - MAX_NUMBER_OF_PATTERNS].
     */
    private static final Integer PATTERN_VARIETY_MODIFIER = 3;

    /**
     * Define in order the parameters as they are found in the file.
     */
    private static final Integer PATTERN_NUMBER = 0;
    private static final Integer OBSTACLE_TYPE = 1;
    private static final Integer OBSTACLE_POSITIONX = 2;
    private static final Integer OBSTACLE_POSITIONY = 3;
    private static final Integer OBSTACLE_SPEEDX = 4;
    private static final Integer OBSTACLE_SPEEDY = 5;
    private static final Integer OBSTACLE_ACCELERATIONX = 6;
    private static final Integer OBSTACLE_ACCELERATIONY = 7;
    private static final Integer OBSTACLE_ROTATIONX = 8;
    private static final Integer OBSTACLE_ROTATIONY = 9;
    private static final Integer OBSTACLE_TICKTIME = 10;
    private static final Integer TOTAL_NUM_OF_PARAMETERS = 11;

    /**
     * Is used to read a stream of bytes.
     */
    private InputStream in;
    /**
     * Defines the factory used to generate the obstacles.
     */
    private final EntityModelGenerator entityGenerator;
    /**
     * Defines a map which associate a number to a pattern of obstacles.
     */
    private final Map<Integer, List<Pair<Obstacle, Integer>>> allObstacles;
    /**
     * Defines the current tick. Every call of getInstanceOfPattern() increases the
     * tick counter until
     * the pattern of obstacles has been iterated completely, after which returns to
     * 0;
     */
    private Integer tick;
    /*
     * Defines the duration in terms of ticks of the pattern of obstacles that is
     * generated.
     */
    private Integer duration;
    /**
     * Is used to chose the pattern of obstacles. A bigger number means the pattern
     * is more difficult.
     */
    private Integer difficulty;
    /**
     * Is used to generate random numbers.
     */
    private final Random random;

    /**
     * Constructor used to create an instance of ObstacleLoader and initialize it.
     */
    public ObstacleLoader() {
        final String filePath = "files/chunkdata.txt";
        this.entityGenerator = new EntityModelGeneratorImpl();
        this.allObstacles = new HashMap<>();
        this.random = new Random();
        this.duration = 0;
        this.tick = 0;
        this.difficulty = 1;

        /*
         * If the procedure of reading from file fails, a random based generation of
         * obstacle is used.
         */
        try {
            this.in = ClassLoader.getSystemResourceAsStream(filePath);
            if (in != null) {
                this.readFromFile();
            } else {
                throw new FileNotFoundException("Could not find the file " + filePath);
            }
        } catch (IOException | InvalidDataFormatException e) {
            this.allObstacles.clear();
            this.randomBasedObstacleGeneration();
        }
    }

    /**
     * Defines a method used to generate random patterns of obstacles.
     */
    private void randomBasedObstacleGeneration() {
        this.duration = TIME_OF_LAZYNESS;

        for (int i = 1; i <= MAX_NUMBER_OF_PATTERNS; i++) {
            // Pattern number i has a random number of obstacles ranging 3 to 8
            final List<Pair<Obstacle, Integer>> obstaclesOfInstance = new ArrayList<>();
            final int numberOfObstacles = MIN_OBSTACLES_FOR_INSTANCE
                    + random.nextInt(MAX_OBSTACLES_FOR_INSTANCE - MIN_OBSTACLES_FOR_INSTANCE);
            for (int j = 0; j < numberOfObstacles; j++) {
                final int typeOfObstacle = random.nextInt(TYPES_OF_OBSTACLES);
                ObstacleType obstacleType;
                Movement movement;
                try {
                    switch (typeOfObstacle) {
                        case 0:
                            movement = new Movement.Builder().addNewPosition(0.0, random.nextDouble(Y_MAP_DIMENSION))
                                    .addNewSpeed(-(DEFAULT_SPEED_OF_OBSTACLES + i / DEFAULT_SPEED_OF_OBSTACLES), 0.0)
                                    .build();
                            obstacleType = ObstacleType.MISSILE;
                            break;
                        case 1:
                            movement = new Movement.Builder().addNewPosition(0.0, random.nextDouble(Y_MAP_DIMENSION))
                                    .addNewSpeed(-(DEFAULT_SPEED_OF_OBSTACLES + i / DEFAULT_SPEED_OF_OBSTACLES), 0.0)
                                    .build();
                            obstacleType = ObstacleType.ZAPPER;
                            break;
                        case 2:
                            movement = new Movement.Builder().addNewPosition(0.0, random.nextDouble(Y_MAP_DIMENSION))
                                    .build();
                            obstacleType = ObstacleType.LASER;
                            break;
                        default:
                            throw new NotImplementedObjectException(
                                    "Tried to spawn an unknown obstacle in ObstacleLoader. A missile will be generated instead.");
                    }
                } catch (NotImplementedObjectException e) {
                    movement = new Movement.Builder().addNewPosition(0.0, random.nextDouble(Y_MAP_DIMENSION))
                            .addNewSpeed(-(DEFAULT_SPEED_OF_OBSTACLES + i / DEFAULT_SPEED_OF_OBSTACLES), 0.0)
                            .build();
                    obstacleType = ObstacleType.MISSILE;
                }

                final Obstacle obstacle = this.entityGenerator.generateObstacle(obstacleType, movement);

                obstaclesOfInstance.add(new Pair<>(obstacle, 1 + random.nextInt(MAX_TICK_FOR_OBSTACLE_SPAWN)));
            }
            this.allObstacles.put(i, obstaclesOfInstance);
        }

    }

    /**
     * Defines a method used to read the patterns of obstacles from a file.
     * 
     * @throws InvalidDataFormatException If the format of the file is invalid.
     */
    private void readFromFile() throws IOException, InvalidDataFormatException {
        final List<Pair<Obstacle, Integer>> obstaclesOfInstance = new ArrayList<>();
        Integer patternNumber = 0;

        final BufferedReader reader = new BufferedReader(new InputStreamReader(in, Charset.defaultCharset()));
        String line;

        for (line = reader.readLine(); line != null; line = reader.readLine()) {
            if (!"".equals(line) && !"END_OF_PATTERNS".equals(line)) {
                final String[] parts = line.split(",");
                if (parts.length != TOTAL_NUM_OF_PARAMETERS) {
                    throw new InvalidDataFormatException("Invalid formatting in file: " + in);
                }

                patternNumber = Integer.parseInt(parts[PATTERN_NUMBER]);

                final String obstacleType = parts[OBSTACLE_TYPE];

                Double xCoordinate = Double.parseDouble(parts[OBSTACLE_POSITIONX]);
                Double yCoordinate = Double.parseDouble(parts[OBSTACLE_POSITIONY]);
                final Pair<Double, Double> pos = new Pair<>(xCoordinate, yCoordinate);

                xCoordinate = Double.parseDouble(parts[OBSTACLE_SPEEDX]);
                yCoordinate = Double.parseDouble(parts[OBSTACLE_SPEEDY]);
                final Pair<Double, Double> speed = new Pair<>(xCoordinate, yCoordinate);

                xCoordinate = Double.parseDouble(parts[OBSTACLE_ACCELERATIONX]);
                yCoordinate = Double.parseDouble(parts[OBSTACLE_ACCELERATIONY]);
                final Pair<Double, Double> acc = new Pair<>(xCoordinate, yCoordinate);

                xCoordinate = Double.parseDouble(parts[OBSTACLE_ROTATIONX]);

                if (parts[OBSTACLE_ROTATIONY].startsWith("RANDOM")) {
                    final String toExtract = parts[OBSTACLE_ROTATIONY];
                    final String integersString = toExtract
                            .substring(toExtract.indexOf('(') + 1, toExtract.indexOf(')'))
                            .replaceAll(";", "").trim();

                    yCoordinate = Double.parseDouble(integersString);
                } else {
                    yCoordinate = Double.parseDouble(parts[OBSTACLE_ROTATIONY]);
                }
                final Pair<Double, Double> rot = new Pair<>(xCoordinate, yCoordinate);
                final Integer tickTimeSpawn = Integer.parseInt(parts[OBSTACLE_TICKTIME]);

                obstaclesOfInstance
                        .add(new Pair<>(
                                this.entityGenerator.generateObstacle(
                                    ObstacleType.valueOf(obstacleType),
                                    new Movement.Builder().addNewPosition(pos).addNewSpeed(speed)
                                        .addNewAcceleration(acc).addNewRotation(rot).build()),
                                tickTimeSpawn));
            } else {
                this.allObstacles.put(patternNumber, new ArrayList<>(obstaclesOfInstance));
                obstaclesOfInstance.clear();
            }
        }

    }

    /**
     * Defines a method used to generate the obstacles associated to the current
     * tick of the pattern.
     * There can be one, more than one, or no obstacle at all corresponding to a
     * certain tick.
     * Every time the method is called the tick counter is increased until it
     * reaches the total duration
     * of the pattern, after which becomes 0.
     * 
     * @return The set of all obstacles associated to a certain tick.
     */
    public Set<Obstacle> getInstanceOfPattern() {
        if (this.tick <= this.duration) {
            this.difficulty = GameInfo.MOVE_SPEED.get() - GameInfo.getInstance().getInitialGameSpeed() + 1;

            this.difficulty = (this.difficulty * PATTERN_VARIETY_MODIFIER - random.nextInt(PATTERN_VARIETY_MODIFIER))
                    % (MAX_NUMBER_OF_PATTERNS + 1);
            if (this.tick == 0) {
                this.duration = this.allObstacles.get(this.difficulty).stream().map(p -> p.get2()).mapToInt(i -> i)
                        .max().getAsInt() + TIME_OF_LAZYNESS;
            }

            this.tick++;

            return this.allObstacles.get(this.difficulty).stream()
                    .filter(p -> p.get2().equals(this.tick))
                    .map(p -> p.get1())
                    .map(p -> this.entityGenerator.generateObstacle(p.getObstacleType(), p.getEntityMovement()))
                    .collect(Collectors.toSet());

        } else {
            this.tick = 0;
        }
        return Set.of();
    }
}
