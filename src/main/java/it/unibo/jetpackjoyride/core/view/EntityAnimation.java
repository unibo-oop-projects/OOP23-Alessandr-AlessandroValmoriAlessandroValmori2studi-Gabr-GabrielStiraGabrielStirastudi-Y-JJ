package it.unibo.jetpackjoyride.core.view;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import it.unibo.jetpackjoyride.core.entities.entity.api.Entity;
import it.unibo.jetpackjoyride.core.entities.entity.api.Entity.EntityStatus;
import it.unibo.jetpackjoyride.core.entities.entity.api.Entity.EntityType;
import it.unibo.jetpackjoyride.core.entities.obstacle.api.Obstacle;
import it.unibo.jetpackjoyride.core.entities.obstacle.api.Obstacle.ObstacleType;
import it.unibo.jetpackjoyride.core.movement.Movement;
import it.unibo.jetpackjoyride.utilities.Pair;
import it.unibo.jetpackjoyride.utilities.exceptions.InvalidDataFormatException;
import javafx.scene.image.Image;

public class EntityAnimation {
    private final static Integer MISSILESPRITES = 20;
    private final static Integer ZAPPERSPRITES = 20;
    private final static Integer LASERSPRITES = 16;
    private final static Integer LILSTOMPERSPRITES = 24;
    private final static Integer MRCUDDLESPRITES = 6;
    private final static Integer PROFITBIRDSPRITES = 12;
    private final static Integer DUKEFISHRONSPRITES = 12;
    private final static Integer VEHICLEPICKUPSPRITES = 21;
    private final static Integer SHIELDPICKUPSPRITES = 2;

    private final List<Image> obstacleImages;
    private final List<Image> powerupImages;
    private final List<Image> pickupImages;

    record InfoAnimation(Double dimensions, Double duration) {}

    private final Map<Pair<EntityStatus,PerformingAction>, InfoAnimation> barryAnimationMapper;

    public EntityAnimation() {
        this.obstacleImages = new ArrayList<>(); // 0-19 MISSILE | 20-39 ZAPPER | 40-55 LASER
        this.powerupImages = new ArrayList<>(); // 0-23 LILSTOMPER | 24-29 MRCUDDLE | 30-41 PROFITBIRD | 42-53 DUKEFISHRON
        this.pickupImages = new ArrayList<>(); // 0-20 VEHICLEPICKUP

        // MISSILE 20 total
        obstacleImages.addAll(imageLoader(MISSILESPRITES, "sprites/entities/obstacles/missile/missile_"));
        // ZAPPER 20 total
        obstacleImages.addAll(imageLoader(ZAPPERSPRITES, "sprites/entities/obstacles/zapper/zapper_"));
        // LASER 16 total
        obstacleImages.addAll(imageLoader(LASERSPRITES, "sprites/entities/obstacles/laser/laser_"));
        
        // LILSTOMPER 24 total
        powerupImages.addAll(imageLoader(LILSTOMPERSPRITES, "sprites/entities/powerups/lilstomper/lilstomper_"));
        // MRCUDDLE 6 total
        powerupImages.addAll(imageLoader(MRCUDDLESPRITES, "sprites/entities/powerups/mrcuddles/mrcuddles_"));
        // PROFITBIRD 12 total
        powerupImages.addAll(imageLoader(PROFITBIRDSPRITES, "sprites/entities/powerups/profitbird/profitbird_"));
        // DUKEFISHRON 12 total
        powerupImages.addAll(imageLoader(DUKEFISHRONSPRITES, "sprites/entities/powerups/dukefishron/dukefishron_"));

        // VEHICLEPICKUP
        pickupImages.addAll(imageLoader(VEHICLEPICKUPSPRITES, "sprites/entities/pickups/vehiclepickup/vehiclepickup_"));
        // VEHICLEPICKUP
        pickupImages.addAll(imageLoader(SHIELDPICKUPSPRITES, "sprites/entities/pickups/shieldpickup/shieldpickup_"));
    }

    public List<Image> loadAnimationFor(final Entity entity) {
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

    private List<Image> imageLoader(final Integer numberOfImages, final String pathName) {
        try {
            return IntStream.range(0, numberOfImages).mapToObj(i -> new Image(getClass().getClassLoader().getResource(pathName + (i + 1) + ".png").toExternalForm())).toList();
        } catch (Exception e) {
            return List.of();
        }
    } 

    private List<Image> takeImages(final List<Image> images, final Integer fromIndex, final Integer toIndex) {
        return IntStream.rangeClosed(fromIndex, toIndex).mapToObj(i -> images.get(i)).toList();
    }
}
