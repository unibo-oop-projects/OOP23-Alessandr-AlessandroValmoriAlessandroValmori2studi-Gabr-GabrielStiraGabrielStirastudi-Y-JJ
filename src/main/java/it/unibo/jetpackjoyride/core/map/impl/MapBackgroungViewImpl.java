package it.unibo.jetpackjoyride.core.map.impl;

import java.io.FileNotFoundException;
import java.net.URL;

import it.unibo.jetpackjoyride.core.map.api.MapBackground;
import it.unibo.jetpackjoyride.core.map.api.MapBackgroundView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 * Implementation of the MapBackground interface.
 * This class provides functionality to control the background view of the game.
 * @author yukai.zhou@studio.unibo.it
 */
public final class MapBackgroungViewImpl implements MapBackgroundView {

    private static final String BACKGROUNG_IMAGE1_PATH = "background/Sector2.png";
    private static final String BACKGROUNG_IMAGE2_PATH = "background/Sector3.png";

    private ImageView bgImageView1, bgImageView2;
    private final Pane root;
    private final Pane gameRoot;
    private final MapBackground controller;

    /**
     * Constructor of the MapBackgroundViewImpl.
     * 
     * @param controller The controller for the map background, 
     * it gives the nesessary date for view to upadte.
     * @param gameRoot The main root of the Game
     */
    public MapBackgroungViewImpl(final MapBackground controller, final Pane gameRoot) {
        this.gameRoot = gameRoot;
        this.root = new Pane();
        this.controller = controller;
        loadBackgroungImage();
    }

    @Override
    public void updateBackgroundView() {
        if (bgImageView1.getFitWidth() != controller.getSize().get1() 
        || bgImageView1.getFitHeight() != controller.getSize().get2()) {
            setImageViewSize(bgImageView1, controller.getSize().get1(), controller.getSize().get2());
            setImageViewSize(bgImageView2, controller.getSize().get1(), controller.getSize().get2());
        }
        bgImageView1.setX(controller.getPosX().get1());
        bgImageView2.setX(controller.getPosX().get2());
    }

    @Override
    public void addNodeInRoot() {
        gameRoot.getChildren().add(this.root);
    }

    /**
    *  Loads the background images and initializes image views with proper sizes.
    */
    private void loadBackgroungImage() {

        bgImageView1 = creatImageView(BACKGROUNG_IMAGE1_PATH);
        bgImageView2 = creatImageView(BACKGROUNG_IMAGE2_PATH);

        setImageViewSize(bgImageView1, controller.getSize().get1(), controller.getSize().get2());
        setImageViewSize(bgImageView2, controller.getSize().get1(), controller.getSize().get2());

        this.root.getChildren().addAll(bgImageView1, bgImageView2);
    }

    /**
    * Creates an image view with the given image path.
    * 
    * @param path The path to the image.
    * @return An ImageView object representing the image.
    */
    private ImageView creatImageView(final String path) {

        try {
            URL backgroundImageUrl = getClass().getClassLoader().getResource(path);
            if (backgroundImageUrl == null) {
                throw new FileNotFoundException("Backgroung Image was not found: " + path);
            }
            String url = backgroundImageUrl.toExternalForm();
            Image backgroundImage = new Image(url);
            ImageView backImageView = new ImageView(backgroundImage);
            return backImageView;
        } catch (FileNotFoundException e) {
            System.err.println("Error message :" + e.getMessage());
            return new ImageView();
        }
    }

    private void setImageViewSize(final ImageView bImageView, final double width, final double height) {
        bImageView.setFitWidth(width);
        bImageView.setFitHeight(height);
    }
}
