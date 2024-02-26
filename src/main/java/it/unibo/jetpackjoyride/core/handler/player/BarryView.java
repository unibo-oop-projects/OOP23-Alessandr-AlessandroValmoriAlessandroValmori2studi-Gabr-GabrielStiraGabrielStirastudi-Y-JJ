package it.unibo.jetpackjoyride.core.handler.player;

import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.jetpackjoyride.core.entities.barry.api.Barry;
import it.unibo.jetpackjoyride.core.entities.barry.api.Barry.PerformingAction;
import it.unibo.jetpackjoyride.core.entities.entity.api.Entity;
import it.unibo.jetpackjoyride.core.handler.entity.EntityView;
import it.unibo.jetpackjoyride.utilities.CircularIterator;
import it.unibo.jetpackjoyride.utilities.GameInfo;

/**
 * The BarryView class represents the view of the Barry entity.
 * It is responsible for updating the visual representation of the Barry entity
 * based on its state.
 * 
 * @author alessandro.valmori2@studio.unibo.it
 */
public final class BarryView implements EntityView {
    /**
     * The imageview of Barry.
     */
    private final ImageView imageView;
    /** The imageview of the shield .*/
    private final ImageView shieldImageView;
    /**
     * The list of the current images which corresponing to the current
     * Barry {@link PerformingAction}.
     */
    private List<Image> images;

    /**
     * An instance of {@link GameInfo}, a singleton which contains information
     * about the current screen size.
     */
    private final GameInfo infoResolution;
    /**
     * A field that stores the most recent {@link PerformingAction} of Barry.
     */
    private PerformingAction oldAction;

    /** The width of the Barry sprite .*/
    private static final double BARRY_WIDTH = 75.0;
    /** The height of the Barry sprite. */
    private static final double BARRY_HEIGHT = 100.0;
    /**
     * A custom circular iterator that repositions itself at
     * the beginning of the list, used here to loop through
     * the image list, which is the player sprite sequence.
     */
    private CircularIterator<Image> iterator;

    /**
     * A map which associates each of Barry's {@link PerformingAction}
     * to the corresponing list of images, initialized once.
     */
    private final Map<PerformingAction, List<Image>> statusMap = new HashMap<>();
    /**
     * The number of copies of each image in the list of images, used
     * the regulate the number of frames before Barry's sprite changes.
     */
    private static final int NUM_COPIES = 7;

    /**
     * Constructs a new {@link BarryView} instance.
     */
    public BarryView() {
        this.shieldImageView = new ImageView(new Image("sprites/entities/player/barrySHIELD.png"));
        this.imageView = new ImageView();
        this.infoResolution = GameInfo.getInstance();
        this.oldAction = PerformingAction.WALKING;
        this.buildMap();
        this.images = new ArrayList<>(this.statusMap.get(this.oldAction));
        this.iterator = new CircularIterator<>(this.images);
    }

    
    @Override
    @SuppressFBWarnings(value="BC")
    public void updateView(final Entity entity) {
        final Barry barry = (Barry) entity;

        if (barry.getPerformingAction() != this.oldAction) {
            this.oldAction = barry.getPerformingAction();
            this.images = new ArrayList<>(this.statusMap.get(this.oldAction));
            this.iterator = new CircularIterator<>(this.images);
        }

        final double scaleX = infoResolution.getScreenWidth() / infoResolution.getDefaultWidth();
        final double scaleY = infoResolution.getScreenHeight() / infoResolution.getDefaultHeight();

        final double width = BARRY_WIDTH * scaleX;
        final double height = BARRY_HEIGHT * scaleY;

        imageView.setRotate(barry.getEntityMovement().getRotation().get1());
        imageView.setX(barry.getEntityMovement().getPosition().get1() * scaleX - width / 2);
        imageView.setY(barry.getEntityMovement().getPosition().get2() * scaleY - height / 2);

        imageView.setFitWidth(width);
        imageView.setFitHeight(height);

        imageView.setImage(this.iterator.next());

        shieldImageView.setX(barry.getEntityMovement().getPosition().get1() * scaleX - width / 2);
        shieldImageView.setY(barry.getEntityMovement().getPosition().get2() * scaleY - height / 2);

        shieldImageView.setFitWidth(width);
        shieldImageView.setFitHeight(height);
    }

    /**
     * Builds the map that associates each of Barry's {@link PerformingAction}
     * to the corresponding set of sprites.
     */
    private void buildMap() {
        Map<PerformingAction, Integer> framesPerAnimation = new HashMap<>() {
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
        for (final var entry : framesPerAnimation.entrySet()) {
            final List<Image> images = new ArrayList<>();
            for (int i = 0; i < entry.getValue(); i++) {
                final String imagePath = getClass().getClassLoader()
                        .getResource("sprites/entities/player/barry" + entry.getKey().toString() + (i + 1) + ".png")
                        .toExternalForm();

                images.addAll(Collections.nCopies(NUM_COPIES, new Image(imagePath)));
            }
            this.statusMap.put(entry.getKey(), new ArrayList<>(images));
        }
    }

    @Override
    public ImageView getImageView() {
        return Collections.nCopies(1, this.imageView).get(0);
    }

    /**
     * Retrives the shields' imageView.
     * 
     * @return the shield's imageView.
     */
    public ImageView getShieldImageView() {
        return Collections.nCopies(1, this.shieldImageView).get(0);
    }

}
