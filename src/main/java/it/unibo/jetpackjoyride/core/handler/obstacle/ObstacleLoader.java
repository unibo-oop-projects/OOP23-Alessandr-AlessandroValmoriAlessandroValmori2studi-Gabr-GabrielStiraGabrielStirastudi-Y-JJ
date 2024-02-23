package it.unibo.jetpackjoyride.core.handler.obstacle;

import java.io.BufferedReader;
import java.io.InputStream;
import java.util.*;

import it.unibo.jetpackjoyride.core.entities.entity.api.EntityModelGenerator;
import it.unibo.jetpackjoyride.core.entities.entity.impl.EntityControllerGeneratorImpl;
import it.unibo.jetpackjoyride.core.entities.entity.impl.EntityModelGeneratorImpl;
import it.unibo.jetpackjoyride.core.entities.obstacle.api.Obstacle;
import it.unibo.jetpackjoyride.core.entities.obstacle.api.Obstacle.ObstacleType;
import it.unibo.jetpackjoyride.core.handler.generic.GenericController;
import it.unibo.jetpackjoyride.core.movement.Movement;
import it.unibo.jetpackjoyride.utilities.GameInfo;
import it.unibo.jetpackjoyride.utilities.Pair;
import it.unibo.jetpackjoyride.utilities.exceptions.InvalidDataFormatException;
import it.unibo.jetpackjoyride.utilities.exceptions.NotImplementedObjectException;

import java.io.InputStreamReader;

import java.util.stream.*;

public class ObstacleLoader {
    private final static Integer MAX_NUMBER_OF_PATTERNS = 30;
    private final static Integer MIN_OBSTACLES_FOR_INSTANCE = 3;
    private final static Integer MAX_OBSTACLES_FOR_INSTANCE = 3;
    private final static Integer TYPES_OF_OBSTACLES = 3;
    private final static Integer TIME_OF_LAZYNESS = 3;
    private final static Double DEFAULT_SPEED_OF_UNKOWN_OBSTACLE = 5.0;
    private final static Integer MAX_TICK_FOR_OBSTACLE_SPAWN = 20;
    private final static Double Y_MAP_DIMENSION = 720.0;

    private InputStream in;
    private final Map<String, Integer> attributes;
    private final EntityModelGenerator entityGenerator;
    private final Map<Integer,List<Pair<Obstacle,Integer>>> allObstacles;
    private Integer interval;
    private Integer duration;
    private Integer difficulty;
    private final EntityControllerGeneratorImpl entityControllersGenerator;

    public ObstacleLoader() {
        this.attributes = new HashMap<>();
        this.entityGenerator = new EntityModelGeneratorImpl();
        this.allObstacles = new HashMap<>();
        this.entityControllersGenerator = new EntityControllerGeneratorImpl();
        this.duration = 0;
        this.interval = 0;
        this.difficulty = 1;

        try {
            in = Objects.requireNonNull(ClassLoader.getSystemResourceAsStream("files/chunkdata.txt"));
            
            this.attributes.put("PATTERN_NUMBER", 0);
            this.attributes.put("OBSTACLE_TYPE", 1);
            this.attributes.put("OBSTACLE_POSITIONX", 2);
            this.attributes.put("OBSTACLE_POSITIONY", 3);
            this.attributes.put("OBSTACLE_SPEEDX", 4);
            this.attributes.put("OBSTACLE_SPEEDY", 5);
            this.attributes.put("OBSTACLE_ACCELERATIONX", 6);
            this.attributes.put("OBSTACLE_ACCELERATIONY", 7);
            this.attributes.put("OBSTACLE_ROTATIONX", 8);
            this.attributes.put("OBSTACLE_ROTATIONY", 9);
            this.attributes.put("OBSTACLE_TICKTIME", 10);
            
            this.readFromFile();
        }
        catch(Exception e) {
            this.allObstacles.clear();
            this.randomBasedObstacleGeneration();
        }

    }

    private void randomBasedObstacleGeneration() {
        this.duration = 30;
        Random random = new Random();

        for(int i=1; i<=MAX_NUMBER_OF_PATTERNS; i++) {
            //Pattern number i has a random number of obstacles ranging 3 to 8
            final List<Pair<Obstacle,Integer>> obstaclesOfInstance = new ArrayList<>();
            final int numberOfObstacles = MIN_OBSTACLES_FOR_INSTANCE + random.nextInt(MAX_OBSTACLES_FOR_INSTANCE);
            for(int j=0; j<numberOfObstacles; j++) {
                final int typeOfObstacle = random.nextInt(TYPES_OF_OBSTACLES);
                final Obstacle obstacle;
                ObstacleType obstacleType;
                Movement movement;
                try {
                    switch (typeOfObstacle) {
                        case 0:
                            movement = new Movement.Builder().setPosition(0.0, random.nextDouble(Y_MAP_DIMENSION)).setSpeed(-(5.0+i/5), 0.0).build();
                            obstacleType = ObstacleType.MISSILE;
                            break;
                        case 1:
                            movement = new Movement.Builder().setPosition(0.0, random.nextDouble(Y_MAP_DIMENSION)).setSpeed(-(5.0+i/5), 0.0).build();
                            obstacleType = ObstacleType.ZAPPER;
                            break;
                        case 2:
                            movement = new Movement.Builder().setPosition(0.0, random.nextDouble(Y_MAP_DIMENSION)).build();
                            obstacleType = ObstacleType.LASER;
                            break;
                        default:
                            throw new NotImplementedObjectException("Tried to spawn an unknown obstacle in ObstacleLoader. A missile will be generated instead.");
                    }
                } catch (NotImplementedObjectException e) {
                    movement = new Movement.Builder().setPosition(0.0, random.nextDouble(Y_MAP_DIMENSION)).setSpeed(-(DEFAULT_SPEED_OF_UNKOWN_OBSTACLE+i/DEFAULT_SPEED_OF_UNKOWN_OBSTACLE), 0.0).build();
                    obstacleType = ObstacleType.MISSILE;
                }
    
                obstacle = this.entityGenerator.generateObstacle(obstacleType, movement);

                obstaclesOfInstance.add(new Pair<>(obstacle, 1 + random.nextInt(MAX_TICK_FOR_OBSTACLE_SPAWN)));
            }
            this.allObstacles.put(i, obstaclesOfInstance);
        }    

    }

    private boolean readFromFile(){
        final List<Pair<Obstacle,Integer>> obstaclesOfInstance = new ArrayList<>();
        Integer patternNumber=0;

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if(!line.equals("") && !line.equals("END_OF_PATTERNS")) {
                    String[] parts = line.split(",");

                    if(parts.length != this.attributes.keySet().size()) {
                        throw new InvalidDataFormatException("Invalid formatting in file: " + in);
                    }

                    patternNumber = Integer.parseInt(parts[this.attributes.get("PATTERN_NUMBER")]);

                    final String obstacleType = parts[this.attributes.get("OBSTACLE_TYPE")];
                    Double xCoordinate = Double.parseDouble(parts[this.attributes.get("OBSTACLE_POSITIONX")]);
                    Double yCoordinate = Double.parseDouble(parts[this.attributes.get("OBSTACLE_POSITIONY")]);
                    final Pair<Double,Double> pos = new Pair<>(xCoordinate, yCoordinate);
                    xCoordinate = Double.parseDouble(parts[this.attributes.get("OBSTACLE_SPEEDX")]);
                    yCoordinate = Double.parseDouble(parts[this.attributes.get("OBSTACLE_SPEEDY")]);
                    final Pair<Double,Double> speed = new Pair<>(xCoordinate, yCoordinate);
                    xCoordinate = Double.parseDouble(parts[this.attributes.get("OBSTACLE_ACCELERATIONX")]);
                    yCoordinate = Double.parseDouble(parts[this.attributes.get("OBSTACLE_ACCELERATIONY")]);
                    final Pair<Double,Double> acc = new Pair<>(xCoordinate, yCoordinate);
                    xCoordinate = Double.parseDouble(parts[this.attributes.get("OBSTACLE_ROTATIONX")]);
                
                    if(parts[this.attributes.get("OBSTACLE_ROTATIONY")].startsWith("RANDOM")) {
                        String toExtract = parts[this.attributes.get("OBSTACLE_ROTATIONY")];
                        String integersString = toExtract.substring(toExtract.indexOf('(') + 1, toExtract.indexOf(')')).replaceAll(";","").trim();

                        yCoordinate = Double.parseDouble(integersString);
                    } else {
                        yCoordinate = Double.parseDouble(parts[this.attributes.get("OBSTACLE_ROTATIONY")]);
                    }
                    final Pair<Double,Double> rot = new Pair<>(xCoordinate, yCoordinate);
                    final Integer tickTimeSpawn = Integer.parseInt(parts[this.attributes.get("OBSTACLE_TICKTIME")]);

                    final Movement obstacleMovement = new Movement.Builder().setPosition(pos).setSpeed(speed).setAcceleration(acc).setRotation(rot).build();
                    obstaclesOfInstance.add(new Pair<>(this.entityGenerator.generateObstacle(ObstacleType.valueOf(obstacleType), obstacleMovement),tickTimeSpawn));
                }
                else {
                    this.allObstacles.put(patternNumber, new ArrayList<>(obstaclesOfInstance));
                    obstaclesOfInstance.clear();
                }
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public List<GenericController<Obstacle,ObstacleView>> getInstanceOfPattern() {
        if (this.isInSamePattern()) {
            this.difficulty = GameInfo.MOVE_SPEED.get() - GameInfo.getInstance().getInitialGameSpeed() + 1;
            
            if(this.interval == 0) {
                this.duration = this.allObstacles.get(this.difficulty).stream().map(p -> p.get2()).mapToInt(i->i).max().getAsInt() + TIME_OF_LAZYNESS;
            }

            final List<Obstacle> models = new ArrayList<>();
            models.addAll(this.allObstacles.get(this.difficulty).stream()
                        .filter(p -> p.get2().equals(this.interval))
                        .map(p -> p.get1())
                        .collect(Collectors.toList()));

            this.interval++;

            if(models.size()>0) {
                System.out.println(models.get(0).getEntityMovement().getPosition());
            }

            return IntStream.range(0, models.size())
            .mapToObj(i -> this.entityControllersGenerator.generateObstacleController(models.get(i)))
            .collect(Collectors.toList());
        } else {
            this.interval = 0;
        }
        return List.of();
    }

    public boolean isInSamePattern() {
        return this.interval <= this.duration;
    }

}
