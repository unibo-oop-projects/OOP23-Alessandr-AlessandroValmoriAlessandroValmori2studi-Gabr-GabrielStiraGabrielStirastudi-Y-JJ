package it.unibo.jetpackjoyride.utilities;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.jetpackjoyride.core.entities.entity.api.Entity;
import it.unibo.jetpackjoyride.core.entities.entity.api.Entity.EntityType;
import it.unibo.jetpackjoyride.core.entities.obstacle.api.Obstacle;
import it.unibo.jetpackjoyride.core.entities.obstacle.impl.Laser;
import it.unibo.jetpackjoyride.core.entities.obstacle.impl.Missile;
import it.unibo.jetpackjoyride.core.entities.obstacle.impl.Zapper;
import it.unibo.jetpackjoyride.core.entities.pickups.api.PickUp;
import it.unibo.jetpackjoyride.core.entities.pickups.impl.ShieldPickUp;
import it.unibo.jetpackjoyride.core.entities.pickups.impl.VehiclePickUp;
import it.unibo.jetpackjoyride.core.entities.powerup.api.PowerUp;
import it.unibo.jetpackjoyride.core.entities.powerup.impl.DukeFishron;
import it.unibo.jetpackjoyride.core.entities.powerup.impl.LilStomper;
import it.unibo.jetpackjoyride.core.entities.powerup.impl.MrCuddles;
import it.unibo.jetpackjoyride.core.entities.powerup.impl.ProfitBird;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.net.URL;
import java.awt.Color;
import java.util.Collections;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

/**
 * The {@link EntityImageLoader} is used by the {@link EntityController} class
 * to load the images which are initially associated to the view of an entity.
 * Initially, all the images of the entities are loaded and stored in this
 * class.
 * When an entity is ready to be shown, it is passed to the loadImages method
 * which returns a list of all the images which are associated to the entity.
 * Loading the images is a one-time only process for each entity, after which
 * there is no need to deal with other images loading.
 * 
 * @author gabriel.stira@studio.unibo.it
 */
public class EntityImageLoader {
    /**
     * Total number of sprite images for {@link Missile}.
     */
    private static final Integer MISSILESPRITES = 20;

    /**
     * Total number of sprite images for {@link Zapper}.
     */
    private static final Integer ZAPPERSPRITES = 20;

    /**
     * Total number of sprite images for {@link Laser}.
     */
    private static final Integer LASERSPRITES = 16;

    /**
     * Total number of sprite images for {@link LilStomper} power-up.
     */
    private static final Integer LILSTOMPERSPRITES = 25;

    /**
     * Total number of sprite images for {@link MrCuddles} power-up.
     */
    private static final Integer MRCUDDLESPRITES = 6;

    /**
     * Total number of sprite images for {@link ProfitBird} power-up.
     */
    private static final Integer PROFITBIRDSPRITES = 12;

    /**
     * Total number of sprite images for {@link DukeFishron} power-up.
     */
    private static final Integer DUKEFISHRONSPRITES = 12;

    /**
     * Total number of sprite images for {@link VehiclePickUp}.
     */
    private static final Integer VEHICLEPICKUPSPRITES = 21;

    /**
     * Total number of sprite images for {@link ShieldPickUp} pickups.
     */
    private static final Integer SHIELDPICKUPSPRITES = 1;

    /**
     * The lists of the images that correspond to each {@link EntityType}.
     */
    private final List<Image> obstacleImages;
    private final List<Image> powerupImages;
    private final List<Image> pickupImages;

    /**
     * Constructs an {@link EntityImageLoader} and loads images for obstacles,
     * power-ups,
     * and pickups.
     */
    public EntityImageLoader() {
        this.obstacleImages = new ArrayList<>();
        this.powerupImages = new ArrayList<>();
        this.pickupImages = new ArrayList<>();

        // Load obstacle images

        obstacleImages.addAll(imageLoader(MISSILESPRITES, "sprites/entities/obstacles/missile/missile_"));
        obstacleImages.addAll(imageLoader(ZAPPERSPRITES, "sprites/entities/obstacles/zapper/zapper_"));
        obstacleImages.addAll(imageLoader(LASERSPRITES, "sprites/entities/obstacles/laser/laser_"));

        // Load power-up images

        powerupImages.addAll(imageLoader(LILSTOMPERSPRITES, "sprites/entities/powerups/lilstomper/lilstomper_"));
        powerupImages.addAll(imageLoader(MRCUDDLESPRITES, "sprites/entities/powerups/mrcuddles/mrcuddles_"));
        powerupImages.addAll(imageLoader(PROFITBIRDSPRITES, "sprites/entities/powerups/profitbird/profitbird_"));
        powerupImages.addAll(imageLoader(DUKEFISHRONSPRITES, "sprites/entities/powerups/dukefishron/dukefishron_"));

        // Load pickup images

        pickupImages
                .addAll(imageLoader(VEHICLEPICKUPSPRITES, "sprites/entities/pickups/vehiclepickup/vehiclepickup_"));
        pickupImages
                .addAll(imageLoader(SHIELDPICKUPSPRITES, "sprites/entities/pickups/shieldpickup/shieldpickup_"));

    }

    /**
     * Loads images for the given entity.
     *
     * @param entity The entity for which to load images.
     * @return The list of images for the entity.
     */
    @SuppressFBWarnings("BC")
    public List<Image> loadImages(final Entity entity) {
        switch (entity.getEntityType()) {
            case OBSTACLE:
                final Obstacle obstacle = (Obstacle) entity;
                switch (obstacle.getObstacleType()) {
                    case MISSILE:
                        return takeImages(this.obstacleImages, 0, MISSILESPRITES - 1);
                    case ZAPPER:
                        return takeImages(this.obstacleImages, MISSILESPRITES, MISSILESPRITES + ZAPPERSPRITES - 1);
                    case LASER:
                        return takeImages(this.obstacleImages, MISSILESPRITES + ZAPPERSPRITES,
                                MISSILESPRITES + ZAPPERSPRITES + LASERSPRITES - 1);
                    default:
                        break;
                }
            case POWERUP:
                final PowerUp powerUp = (PowerUp) entity;
                switch (powerUp.getPowerUpType()) {
                    case LILSTOMPER:
                        return takeImages(this.powerupImages, 0, LILSTOMPERSPRITES - 1);
                    case MRCUDDLES:
                        return takeImages(this.powerupImages, LILSTOMPERSPRITES,
                                LILSTOMPERSPRITES + MRCUDDLESPRITES - 1);
                    case PROFITBIRD:
                        return takeImages(this.powerupImages, LILSTOMPERSPRITES + MRCUDDLESPRITES,
                                LILSTOMPERSPRITES + MRCUDDLESPRITES + PROFITBIRDSPRITES - 1);
                    case DUKEFISHRON:
                        return takeImages(this.powerupImages, LILSTOMPERSPRITES + MRCUDDLESPRITES + PROFITBIRDSPRITES,
                                LILSTOMPERSPRITES + MRCUDDLESPRITES + PROFITBIRDSPRITES + DUKEFISHRONSPRITES - 1);
                    default:
                        break;
                }
            case PICKUP:
                final PickUp pickUp = (PickUp) entity;
                switch (pickUp.getPickUpType()) {
                    case VEHICLE:
                        return takeImages(this.pickupImages, 0, VEHICLEPICKUPSPRITES - 1);
                    case SHIELD:
                        return takeImages(this.pickupImages, VEHICLEPICKUPSPRITES,
                                VEHICLEPICKUPSPRITES + SHIELDPICKUPSPRITES - 1);
                    default:
                        break;
                }
                return Collections.unmodifiableList(pickupImages);
            case BARRY:
            default:
                return List.of();
        }
    }

    /**
     * Takes images from the specified range of indices in the given list.
     *
     * @param images    The list of images.
     * @param fromIndex The starting index.
     * @param toIndex   The ending index.
     * @return The sublist of images.
     */
    private List<Image> takeImages(final List<Image> images, final Integer fromIndex, final Integer toIndex) {
        if (images.isEmpty()) {
            return List.of();
        }
        return IntStream.rangeClosed(fromIndex, toIndex).mapToObj(i -> images.get(i)).toList();
    }

    /**
     * Loads images from resources based on the number of images and the path name.
     *
     * @param numberOfImages The number of images to load.
     * @param pathName       The path to the image resources.
     * @return The list of loaded images.
     */
    private List<Image> imageLoader(final Integer numberOfImages, final String pathName) {
        final List<Image> images = new ArrayList<>();

        for (int i = 0; i < numberOfImages; i++) {
            try {
                final URL imagePath = getClass().getClassLoader().getResource(pathName + (i + 1) + ".png");
                if (imagePath != null) {
                    images.add(new Image(imagePath.toExternalForm()));
                } else {
                    throw new FileNotFoundException("File " + pathName + " not found");
                }
            } catch (FileNotFoundException e) {
                images.add(createSubstituteImage());
            }
        }
        return images;
    }

    /**
     * If an image is not found, a red one is loaded instead so that the game is still playable.
     * 
     * @return A completely red image.
     */
    private Image createSubstituteImage() {
        final BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
        final Graphics g = image.getGraphics();
        g.setColor(Color.RED);
        g.fillRect(0, 0, 1, 1);
        g.dispose();
        return SwingFXUtils.toFXImage(image, null);
    }
}
