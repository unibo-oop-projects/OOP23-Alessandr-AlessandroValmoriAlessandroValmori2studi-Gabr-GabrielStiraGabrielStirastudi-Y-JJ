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

    private static final int NUM_OF_IMAGES = 6;
    private static final int POSITION = 0;
    private static final int SIZE = 1;

    private ImageView bgImageView1, bgImageView2;
    private final Pane root;
    private Image[] bgImages;
    private int currentImage = 0;

    /**
     * Constructor of the MapBackgroundViewImpl.
     * 
     * @param data The data of background model, 
     * it gives the nesessary date for view to upadte.
     */
    public MapBackgroungViewImpl(final List<Pair<Double, Double>> data) {
        this.bgImages = new Image[NUM_OF_IMAGES];
        this.root = new Pane();
        loadBgImages();
        loadBackgroungImage(data);
    }

    @Override
    public void updateBackgroundView(final List<Pair<Double, Double>> data) {
        setImageViewSize(bgImageView1, data.get(SIZE).get1(), data.get(SIZE).get2());
        setImageViewSize(bgImageView2, data.get(SIZE).get1(), data.get(SIZE).get2());
        bgImageView1.setX(data.get(POSITION).get1());
        bgImageView2.setX(data.get(POSITION).get2());
    }

    @Override
    public void addNodeInRoot(final Pane gameRoot) {
        gameRoot.getChildren().add(this.root);
    }

    @Override
    public void changeImage(final int num, final int index) {
            if (num == 0) {
                this.bgImageView1.setImage(bgImages[index]);
            } else {
                this.bgImageView2.setImage(bgImages[index + 1]);
            }
    }

    @Override
    public boolean isChange(final int num, final int index) {
            if (num == 0) {
                return !bgImageView1.getImage().equals(bgImages[index]);
            } else {
                return !bgImageView2.getImage().equals(bgImages[index + 1]);
            }
    }

    /**
    * Loads the background images and initializes image views with proper sizes.
    * @param data The data necessary for create the ImageView
    */
    private void loadBackgroungImage(final List<Pair<Double, Double>> data) {

        bgImageView1 = new ImageView(bgImages[currentImage]);
        bgImageView2 = new ImageView(bgImages[currentImage + 1]);

        setImageViewSize(bgImageView1, data.get(SIZE).get1(), data.get(SIZE).get2());
        setImageViewSize(bgImageView2, data.get(SIZE).get1(), data.get(SIZE).get2());

        this.root.getChildren().addAll(bgImageView1, bgImageView2);
    }

    private void setImageViewSize(final ImageView bImageView, final double width, final double height) {
        bImageView.setFitWidth(width);
        bImageView.setFitHeight(height);
    }

    private void loadBgImages() {
        for (int i = 0; i < NUM_OF_IMAGES; i++) {
           String path = "background/bg" + (i + 1) + ".png";
           try {
              URL bgImageUrl = getClass().getClassLoader().getResource(path);
              if (bgImageUrl == null) {
                 throw new FileNotFoundException("Coin Image was not found: " + path);
              }
              String url = bgImageUrl.toExternalForm();
              Image bgImage = new Image(url);
              bgImages[i] = bgImage;
           } catch (FileNotFoundException e) {
              System.err.println("Error message :" + e.getMessage());
           }
        }
     }
}
