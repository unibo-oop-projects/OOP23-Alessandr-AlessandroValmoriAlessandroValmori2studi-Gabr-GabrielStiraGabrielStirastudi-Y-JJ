package it.unibo.jetpackjoyride.core.map.impl;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.List;

import it.unibo.jetpackjoyride.core.map.api.MapBackgroundView;
import it.unibo.jetpackjoyride.utilities.Pair;
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
    private static final int POSITION = 0;
    private static final int SIZE = 1;

    private ImageView bgImageView1, bgImageView2;
    private final Pane root;

    /**
     * Constructor of the MapBackgroundViewImpl.
     * 
     * @param data The data of background model, 
     * it gives the nesessary date for view to upadte.
     */
    public MapBackgroungViewImpl(final List<Pair<Double, Double>> data) {
        this.root = new Pane();
        loadBackgroungImage(data);
    }

    @Override
    public void updateBackgroundView(final List<Pair<Double, Double>> data) {
        if (bgImageView1.getFitWidth() != data.get(SIZE).get1() 
        || bgImageView1.getFitHeight() != data.get(SIZE).get2()) {
            setImageViewSize(bgImageView1, data.get(SIZE).get1(), data.get(SIZE).get2());
            setImageViewSize(bgImageView2, data.get(SIZE).get1(), data.get(SIZE).get2());
        }
        bgImageView1.setX(data.get(POSITION).get1());
        bgImageView2.setX(data.get(POSITION).get2());
    }

    @Override
    public void addNodeInRoot(final Pane gameRoot) {
        gameRoot.getChildren().add(this.root);
    }

    /**
    * Loads the background images and initializes image views with proper sizes.
    * @param data The data necessary for create the ImageView
    */
    private void loadBackgroungImage(final List<Pair<Double, Double>> data) {

        bgImageView1 = creatImageView(BACKGROUNG_IMAGE1_PATH);
        bgImageView2 = creatImageView(BACKGROUNG_IMAGE2_PATH);

        setImageViewSize(bgImageView1, data.get(SIZE).get1(), data.get(SIZE).get2());
        setImageViewSize(bgImageView2, data.get(SIZE).get1(), data.get(SIZE).get2());

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
