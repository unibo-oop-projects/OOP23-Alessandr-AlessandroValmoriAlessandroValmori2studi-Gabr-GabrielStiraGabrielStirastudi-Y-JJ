package it.unibo.jetpackjoyride.core.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import it.unibo.jetpackjoyride.core.entities.barry.api.Barry;
import it.unibo.jetpackjoyride.core.entities.barry.api.Barry.PerformingAction;
import it.unibo.jetpackjoyride.core.entities.entity.api.Entity;
import it.unibo.jetpackjoyride.core.entities.entity.api.Entity.EntityStatus;
import it.unibo.jetpackjoyride.core.entities.obstacle.api.Obstacle;
import it.unibo.jetpackjoyride.core.entities.obstacle.impl.Missile;
import it.unibo.jetpackjoyride.core.entities.pickups.api.PickUp;
import it.unibo.jetpackjoyride.core.entities.pickups.impl.VehiclePickUp;
import it.unibo.jetpackjoyride.core.entities.powerup.api.PowerUp;
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
    private final List<Image> barryImages;

    private final Map<PerformingAction, List<Image>> statusMap = new HashMap<>();
    private static final int NUM_COPIES = 7;

    private final Map<PerformingAction, Integer> framesPerAnimation = new HashMap<>() {
        {
            put(PerformingAction.WALKING, 4);
            put(PerformingAction.BURNED, 4);
            put(PerformingAction.LASERED, 4);
            put(PerformingAction.ZAPPED, 4);
            put(PerformingAction.FALLING, 2);
            put(PerformingAction.PROPELLING, 2);
            put(PerformingAction.HEAD_DRAGGING, 2);
        }
    };

    public EntityAnimation() {
        this.obstacleImages = new ArrayList<>(); // 0-19 MISSILE | 20-39 ZAPPER | 40-55 LASER
        this.powerupImages = new ArrayList<>(); // 0-23 LILSTOMPER | 24-29 MRCUDDLE | 30-41 PROFITBIRD | 42-53
                                                // DUKEFISHRON
        this.pickupImages = new ArrayList<>(); // 0-20 VEHICLEPICKUP
        this.barryImages = new ArrayList<>(); // 0-21 BARRY

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

        pickupImages.addAll(imageLoader(SHIELDPICKUPSPRITES, "sprites/entities/player/barry_"));
    }

    private void buildMap() {
        for (final var entry : framesPerAnimation.entrySet()) {
            final List<Image> images = new ArrayList<>();
            for (int i = 0; i < entry.getValue(); i++) {
                final String imagePath = getClass().getClassLoader()
                        .getResource("sprites/entities/player/barry" + entry.getKey().toString() + (i + 1) + ".png")
                        .toExternalForm();

                images.add(new Image(imagePath));
            }
            this.statusMap.put(entry.getKey(), new ArrayList<>(images));
        }
    }

    public List<Image> loadImages(final Entity entity) {
        switch (entity.getEntityType()) {
            case BARRY:
                this.buildMap();
                this.statusMap.values().stream().map(l -> this.barryImages.addAll(l));
                return barryImages;
            case OBSTACLE:
                return obstacleImages;
            case POWERUP:
                return powerupImages;
            case PICKUP:
                return pickupImages;
            default:
                return List.of();
        }
    }

    public AnimationInfo loadAnimationInfosFor(final Entity entity) {
        switch (entity.getEntityType()) {
            case BARRY:
                Barry barry = (Barry) entity;
                switch (barry.getPerformingAction()) {
                    case WALKING:
                        return new AnimationInfo(0, 3, NUM_COPIES, true);
                    case BURNED:
                        return new AnimationInfo(4, 7, NUM_COPIES, true);
                    case LASERED:
                        return new AnimationInfo(8, 11, NUM_COPIES, true);
                    case ZAPPED:
                        return new AnimationInfo(12, 15, NUM_COPIES, true);
                    case FALLING:
                        return new AnimationInfo(16, 17, NUM_COPIES, true);
                    case PROPELLING:
                        return new AnimationInfo(18, 19, NUM_COPIES, true);
                    case HEAD_DRAGGING:
                        return new AnimationInfo(20, 21, NUM_COPIES, true);
                    default:
                        return new AnimationInfo(0, 0, 0, false);
                }
            case OBSTACLE:
                Obstacle obstacle = (Obstacle) entity;
                switch (obstacle.getObstacleType()) {
                    case MISSILE:
                        Missile missile = (Missile) obstacle;
                        switch (obstacle.getEntityStatus()) {
                            case CHARGING:
                                if (missile.getLifetime() > 100) {
                                    return new AnimationInfo(3, 4, 4, true);
                                } else {
                                    return new AnimationInfo(0, 2, 4, true);
                                }
                            case ACTIVE:
                                return new AnimationInfo(5, 11, 4, true);
                            case DEACTIVATED:
                                return new AnimationInfo(12, 19, 4, true);
                            default:
                                return new AnimationInfo(0, 0, 0, false);
                        }
                    case ZAPPER:
                        switch (obstacle.getEntityStatus()) {
                            case ACTIVE:
                                return new AnimationInfo(0, 4, 7, true);
                            case DEACTIVATED:
                                return new AnimationInfo(5, 19, 4, false);
                            default:
                                return new AnimationInfo(0, 0, 0, false);
                        }
                    case LASER:
                        switch (obstacle.getEntityStatus()) {
                            case CHARGING:
                                return new AnimationInfo(11, 0, 8, true);
                            case ACTIVE:
                                return new AnimationInfo(12, 15, 8, true);
                            case DEACTIVATED:
                                return new AnimationInfo(0, 11, 8, true);
                            default:
                                return new AnimationInfo(0, 0, 0, false);
                        }
                    default:
                        return new AnimationInfo(0, 0, 0, false);
                }
            case POWERUP:
                PowerUp powerUp = (PowerUp) entity;
                switch (powerUp.getPowerUpType()) {
                    case LILSTOMPER:
                        switch (powerUp.getPerformingAction()) {
                            case WALKING:
                                return new AnimationInfo(0, 0, 7, true);
                            case ASCENDING:
                                return new AnimationInfo(6, 12, 4, false);
                            case DESCENDING:
                                return new AnimationInfo(17, 18, 6, false);
                            case GLIDING:
                                return new AnimationInfo(13, 16, 6, true);
                            case LANDING:
                                return new AnimationInfo(18, 23, 4, false);
                            default:
                                return new AnimationInfo(0, 0, 0, false);
                        }
                    case MRCUDDLES:
                        if (powerUp.getEntityStatus().equals(EntityStatus.ACTIVE)) {
                            switch (powerUp.getPerformingAction()) {
                                case ASCENDING:
                                    return new AnimationInfo(0, 0, 5, true);
                                case DESCENDING:
                                    return new AnimationInfo(1, 4, 5, false);
                                default:
                                    return new AnimationInfo(0, 0, 0, false);
                            }
                        } else {
                            return new AnimationInfo(5, 5, 1, false);
                        }
                    case PROFITBIRD:

                        switch (powerUp.getPerformingAction()) {
                            case WALKING:
                                return new AnimationInfo(0, 2, 7, true);
                            case JUMPING:
                                return new AnimationInfo(3, 3, 1, false);
                            case ASCENDING:
                                return new AnimationInfo(4, 11, 1, false);
                            case DESCENDING:
                                return new AnimationInfo(3, 3, 1, false);
                            default:
                                return new AnimationInfo(0, 0, 0, false);
                        }
                    case DUKEFISHRON:
                        switch (powerUp.getPerformingAction()) {
                            case ASCENDING:
                                return new AnimationInfo(0, 5, 6, true);
                            case DESCENDING:
                                return new AnimationInfo(6, 11, 6, true);
                            default:
                                return new AnimationInfo(0, 0, 0, false);
                        }
                    default:
                        return new AnimationInfo(0, 0, 0, false);
                }
            case PICKUP:
                final PickUp pickUp = (PickUp) entity;
                switch (pickUp.getPickUpType()) {
                    case VEHICLE:
                        final VehiclePickUp vehiclePickUp = (VehiclePickUp) pickUp;
                        switch (pickUp.getEntityStatus()) {
                            case ACTIVE:
                                return new AnimationInfo(0, 7, 4, true);
                            case DEACTIVATED:
                                switch (vehiclePickUp.getVehicleSpawn()) {
                                    case LILSTOMPER:
                                        return new AnimationInfo(8, 10, 4, true);
                                    case MRCUDDLES:
                                        return new AnimationInfo(11, 13, 4, true);
                                    case PROFITBIRD:
                                        return new AnimationInfo(14, 16, 4, true);
                                    case DUKEFISHRON:
                                        return new AnimationInfo(17, 19, 4, true);
                                    default:
                                        return new AnimationInfo(0, 0, 0, false);
                                }
                            default:
                                return new AnimationInfo(0, 0, 0, false);
                        }
                    case SHIELD:
                        switch (pickUp.getEntityStatus()) {
                            case ACTIVE:
                                return new AnimationInfo(0, 0, 1, false);
                            case DEACTIVATED:
                                return new AnimationInfo(0, 0, 0, false);
                            default:
                                return new AnimationInfo(0, 0, 0, false);
                        }
                    default:
                        return new AnimationInfo(0, 0, 0, false);
                }
            default:
                return new AnimationInfo(0, 0, 0, false);
        }
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
