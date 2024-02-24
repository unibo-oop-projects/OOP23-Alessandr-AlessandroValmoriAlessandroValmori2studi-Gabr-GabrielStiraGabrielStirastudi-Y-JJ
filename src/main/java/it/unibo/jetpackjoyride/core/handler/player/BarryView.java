package it.unibo.jetpackjoyride.core.handler.player;

import javafx.scene.image.ImageView;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.unibo.jetpackjoyride.core.entities.barry.api.Barry;
import it.unibo.jetpackjoyride.core.entities.barry.api.Barry.PerformingAction;
import it.unibo.jetpackjoyride.core.entities.entity.api.Entity;
import it.unibo.jetpackjoyride.core.entities.entity.api.Entity.EntityStatus;
import it.unibo.jetpackjoyride.core.handler.entity.EntityView;
import it.unibo.jetpackjoyride.utilities.GameInfo;

/**
 * The BarryView class represents the view of the Barry entity.
 * It is responsible for updating the visual representation of the Barry entity
 * based on its state.
 */
public final class BarryView implements EntityView {

    private final ImageView imageView;
    private final ImageView shieldImageView;
    private List<Image> images;
    private int animationFrame;
    private final GameInfo infoResolution;
    private PerformingAction oldAction;
    private List<ImageView> imageViewCouple;

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

    /**
     * Constructs a new BarryView instance with the given list of images.
     *
     * @param images The list of images representing different animations of the
     *               Barry entity.
     */
    public BarryView() {

        this.shieldImageView = new ImageView(new Image("sprites/entities/player/barrySHIELD.png"));
        this.imageView = new ImageView();
        this.infoResolution = GameInfo.getInstance();
        this.animationFrame = 0;
        this.oldAction = PerformingAction.WALKING;

        this.buildMap();
        this.images = new ArrayList<>(this.statusMap.get(this.oldAction));
        this.imageViewCouple = new ArrayList<>();
        this.imageViewCouple.add(imageView);
      
    }

    /**
     * Updates the visual representation of the Barry entity based on its current
     * state.
     *
     * @param barry The Barry entity whose view needs to be updated.
     */

    @Override
    public void updateView(final Entity entity) {
        final Barry barry = (Barry) entity;

        if (barry.getPerformingAction() != this.oldAction) {
            this.oldAction = barry.getPerformingAction();
            this.images = new ArrayList<>(this.statusMap.get(this.oldAction));
            animationFrame = 0;
        }

        final double scaleX = infoResolution.getScreenWidth() / infoResolution.getDefaultWidth();
        final double scaleY = infoResolution.getScreenHeight() / infoResolution.getDefaultHeight();

        final double width = infoResolution.getDefaultWidth() / 17 * scaleX;
        final double height = infoResolution.getDefaultHeight() / 7 * scaleY;

        imageView.setX(barry.getEntityMovement().getPosition().get1() * scaleX - width / 2);
        imageView.setY(barry.getEntityMovement().getPosition().get2()* scaleY - height / 2);

        imageView.setFitWidth(width);
        imageView.setFitHeight(height);

        imageView.setImage(this.images.get(animationFrame));
        animationFrame = (animationFrame + 1) % images.size();

        shieldImageView.setX(barry.getEntityMovement().getPosition().get1()* scaleX - width / 2);
        shieldImageView.setY(barry.getEntityMovement().getPosition().get2()* scaleY - height / 2);

        shieldImageView.setFitWidth(width);
        shieldImageView.setFitHeight(height);
    }

    private void buildMap() {
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
        return imageView;
    }

    public ImageView getShieldImageView(){
        return this.shieldImageView;
    }

}