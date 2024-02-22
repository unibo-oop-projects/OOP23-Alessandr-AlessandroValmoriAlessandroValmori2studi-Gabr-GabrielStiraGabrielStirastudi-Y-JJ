package it.unibo.jetpackjoyride.core.handler.obstacle;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import it.unibo.jetpackjoyride.core.entities.entity.api.EntityModelGenerator;
import it.unibo.jetpackjoyride.core.entities.entity.impl.EntityModelGeneratorImpl;
import it.unibo.jetpackjoyride.core.entities.obstacle.api.Obstacle;
import it.unibo.jetpackjoyride.core.entities.obstacle.api.Obstacle.ObstacleType;
import it.unibo.jetpackjoyride.core.movement.Movement;
import it.unibo.jetpackjoyride.utilities.GameInfo;
import it.unibo.jetpackjoyride.utilities.Pair;
import it.unibo.jetpackjoyride.utilities.exceptions.InvalidDataFormatException;

import java.util.stream.*;

public class ObstacleLoader {
    private final Map<String, Integer> attributes;
    private final EntityModelGenerator entityGenerator;
    private final Map<Integer,List<Pair<Obstacle,Integer>>> allObstacles;
    private Integer interval;
    private Integer duration;
    private Integer difficulty;

    public ObstacleLoader() {
        this.attributes = new HashMap<>();
        this.entityGenerator = new EntityModelGeneratorImpl();
        this.allObstacles = new HashMap<>();
        this.duration = 0;
        this.interval = 0;
        this.difficulty = 1;

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

        if(!readFromFile("src/main/java/it/unibo/jetpackjoyride/utilities/files/chunkdata.txt")) {
            System.out.println("Randomic generation of obstacles instead");
        }

    }

    public void generatePattern(final Integer difficulty) {
        this.duration = 0;
        this.interval = 0;
        this.difficulty = 0;
    }

    private boolean readFromFile(String filename){
        final List<Pair<Obstacle,Integer>> obstaclesOfInstance = new ArrayList<>();
        Integer patternNumber=0;

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line = reader.readLine();
            while ((line = reader.readLine()) != null) {
                if(!line.equals("")) {
                    String[] parts = line.split(",");

                    if(parts.length != this.attributes.keySet().size()) {
                        throw new InvalidDataFormatException("Something went wrong while trying to read the file "+filename);
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
                    this.allObstacles.put(patternNumber, obstaclesOfInstance);
                    this.duration = obstaclesOfInstance.get(obstaclesOfInstance.size()-1).get2() + 10;
                }
            }
        } catch (IOException e) {
            return false;
        }
        catch (InvalidDataFormatException e) {
            return false;
        }
        return true;
    }

    public List<Obstacle> getInstanceOfPattern() {
        this.interval++;
        if (!this.hasFinished()) {
            this.difficulty = GameInfo.MOVE_SPEED.get() - GameInfo.getInstance().getInitialGameSpeed() + 1;
            final Random random = new Random();
            return this.allObstacles.get(1).stream()
                        .filter(p -> p.get2().equals(this.interval))
                        .map(p -> p.get1())
                        .collect(Collectors.toList());
        }
        return List.of();
    }

    public boolean hasFinished() {
        return this.duration.equals(this.interval);
    }

}
