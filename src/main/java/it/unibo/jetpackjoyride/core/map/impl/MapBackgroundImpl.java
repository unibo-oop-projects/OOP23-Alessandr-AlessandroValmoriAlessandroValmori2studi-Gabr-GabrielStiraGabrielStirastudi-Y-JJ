package it.unibo.jetpackjoyride.core.map.impl;

import java.io.FileNotFoundException;
import java.net.URL;

import it.unibo.jetpackjoyride.core.map.api.MapBackground;
import it.unibo.jetpackjoyride.utilities.GameInfo;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class MapBackgroundImpl extends Pane implements MapBackground {

    private final String BACKGROUNG_IMAGE1_PATH = "background/Sector2.png";
    private final String BACKGROUNG_IMAGE2_PATH = "background/Sector3.png";

    private int moveSpeed;

    private ImageView bgImageView1, bgImageView2;
    private double bgImageX1, bgImageX2;
    private double mapWidth;
    private double mapHeight;

    private GameInfo gameInfo;

    public MapBackgroundImpl(GameInfo gameInfo) {

        this.gameInfo = gameInfo;
        moveSpeed = GameInfo.moveSpeed.get();
        mapHeight = gameInfo.getScreenHeight();
        mapWidth = gameInfo.getScreenWidth();
        loadBackgroungImage();

    }

    public void updateBackgroundModel() {

        bgImageX1 -= moveSpeed;
        bgImageX2 -= moveSpeed;

        if (isOutofMap(bgImageX1)) {
            bgImageX1 = bgImageX2 + mapWidth;
        }
        if (isOutofMap(bgImageX2)) {
            bgImageX2 = bgImageX1 + mapWidth;
        }
        updateSize();

    }

    public void updateBackgroundView() {

        bgImageView1.setX(bgImageX1);
        bgImageView2.setX(bgImageX2);
    }

    private void updateSize() {
        double newWidth = gameInfo.getScreenWidth();
        double newHeight = gameInfo.getScreenHeight();

        if (newWidth != mapWidth || newHeight != mapHeight) {
            double ratioX1 = bgImageX1 / mapWidth;
            double ratioX2 = bgImageX2 / mapWidth;
            mapWidth = newWidth;
            mapHeight = newHeight;

            setImageViewSize(bgImageView1, mapWidth, mapHeight);
            setImageViewSize(bgImageView2, mapWidth, mapHeight);

            bgImageX1 = mapWidth * ratioX1;
            bgImageX2 = mapWidth * ratioX2;

        }

    }

    private void loadBackgroungImage() {

        bgImageView1 = creatImageView(BACKGROUNG_IMAGE1_PATH);
        bgImageView2 = creatImageView(BACKGROUNG_IMAGE2_PATH);

        setImageViewSize(bgImageView1, mapWidth, mapHeight);
        setImageViewSize(bgImageView2, mapWidth, mapHeight);

        bgImageX1 = 0;
        bgImageX2 = mapWidth;

        this.getChildren().addAll(bgImageView1, bgImageView2);
    }

    private ImageView creatImageView(String path) {

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
        }
        return null;
    }

    private void setImageViewSize(ImageView bImageView, double width, double height) {
        bImageView.setFitWidth(width);
        bImageView.setFitHeight(height);
    }

    private boolean isOutofMap(double x) {
        return x <= -mapWidth;
    }
}