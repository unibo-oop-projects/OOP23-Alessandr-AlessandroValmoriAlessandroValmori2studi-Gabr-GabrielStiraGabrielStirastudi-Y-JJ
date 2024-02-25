package it.unibo.jetpackjoyride.utilities;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import it.unibo.jetpackjoyride.core.entities.entity.api.Entity;
import it.unibo.jetpackjoyride.core.entities.obstacle.api.Obstacle;
import it.unibo.jetpackjoyride.core.entities.pickups.api.PickUp;
import it.unibo.jetpackjoyride.core.entities.powerup.api.PowerUp;
import java.util.Collections;
import javafx.scene.image.Image;

@SuppressWarnings("unchecked")
public class EntityImageLoader {
    private final static Integer MISSILESPRITES = 20;
    private final static Integer ZAPPERSPRITES = 20;
    private final static Integer LASERSPRITES = 16;
    private final static Integer LILSTOMPERSPRITES = 25;
    private final static Integer MRCUDDLESPRITES = 6;
    private final static Integer PROFITBIRDSPRITES = 12;
    private final static Integer DUKEFISHRONSPRITES = 12;
    private final static Integer VEHICLEPICKUPSPRITES = 21;
    private final static Integer SHIELDPICKUPSPRITES = 2;

    private final List<Image> obstacleImages;
    private final List<Image> powerupImages;
    private final List<Image> pickupImages;
    
    public EntityImageLoader() {
        this.obstacleImages = new ArrayList<>(); // 0-19 MISSILE | 20-39 ZAPPER | 40-55 LASER
        this.powerupImages = new ArrayList<>(); // 0-24 LILSTOMPER | 25-30 MRCUDDLE | 31-42 PROFITBIRD | 43-54 DUKEFISHRON
        this.pickupImages = new ArrayList<>(); // 0-20 VEHICLEPICKUP

        // MISSILE 20 total
        obstacleImages.addAll(imageLoader(MISSILESPRITES, "sprites/entities/obstacles/missile/missile_"));
        // ZAPPER 20 total
        obstacleImages.addAll(imageLoader(ZAPPERSPRITES, "sprites/entities/obstacles/zapper/zapper_"));
        // LASER 16 total
        obstacleImages.addAll(imageLoader(LASERSPRITES, "sprites/entities/obstacles/laser/laser_"));

        // LILSTOMPER 25 total
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

        pickupImages.addAll(imageLoader(SHIELDPICKUPSPRITES, "sprites/entities/player/barry_"));
    }


    public List<Image> loadImages(final Entity entity) {
        switch (entity.getEntityType()) {
            case OBSTACLE:
                @SuppressWarnings("unchecked")
                Obstacle obstacle = (Obstacle)entity;
                switch (obstacle.getObstacleType()) {
                    case MISSILE:
                        return this.takeImages(this.obstacleImages,0,MISSILESPRITES-1);
                    case ZAPPER: 
                        return this.takeImages(this.obstacleImages,MISSILESPRITES, MISSILESPRITES+ZAPPERSPRITES-1);
                    case LASER: 
                        return this.takeImages(this.obstacleImages, MISSILESPRITES+ZAPPERSPRITES, MISSILESPRITES+ZAPPERSPRITES+LASERSPRITES-1);
                    default:
                        break;
                }
            case POWERUP:
                @SuppressWarnings("unchecked")
                PowerUp powerUp = (PowerUp)entity;
                switch (powerUp.getPowerUpType()) {
                    case LILSTOMPER:
                        return this.takeImages(this.powerupImages, 0,LILSTOMPERSPRITES-1);
                    case MRCUDDLES:
                        return this.takeImages(this.powerupImages, LILSTOMPERSPRITES,LILSTOMPERSPRITES+MRCUDDLESPRITES-1);
                    case PROFITBIRD:
                        return this.takeImages(this.powerupImages, LILSTOMPERSPRITES+MRCUDDLESPRITES,LILSTOMPERSPRITES+MRCUDDLESPRITES+PROFITBIRDSPRITES-1);
                    case DUKEFISHRON:
                        return this.takeImages(this.powerupImages, LILSTOMPERSPRITES+MRCUDDLESPRITES+PROFITBIRDSPRITES,LILSTOMPERSPRITES+MRCUDDLESPRITES+PROFITBIRDSPRITES+DUKEFISHRONSPRITES-1);
                    default:
                        break;
                }
            case PICKUP:
                @SuppressWarnings("unchecked")
                PickUp pickUp = (PickUp)entity;
                switch (pickUp.getPickUpType()) {
                    case VEHICLE:
                        return this.takeImages(this.pickupImages, 0,VEHICLEPICKUPSPRITES-1);
                    case SHIELD:
                        return this.takeImages(this.pickupImages, VEHICLEPICKUPSPRITES,VEHICLEPICKUPSPRITES+SHIELDPICKUPSPRITES-1);
                    default:
                        break;
                }
                return Collections.unmodifiableList(pickupImages);
            case BARRY:
            default:
                return List.of();
        }
    }

    private List<Image> takeImages(final List<Image> images, final Integer fromIndex, final Integer toIndex) {
        return IntStream.rangeClosed(fromIndex, toIndex).mapToObj(i -> images.get(i)).toList();
    }

    private List<Image> imageLoader(final Integer numberOfImages, final String pathName) {
        try {
            return IntStream.range(0, numberOfImages)
                    .mapToObj(i -> new Image(
                            getClass().getClassLoader().getResource(pathName + (i + 1) + ".png").toExternalForm()))
                    .toList();
        } catch (Exception e) {
            return List.of();
        }
    }
}
