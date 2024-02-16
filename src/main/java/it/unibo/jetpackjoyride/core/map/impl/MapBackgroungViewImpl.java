package it.unibo.jetpackjoyride.core.map.impl;

import java.io.FileNotFoundException;
import java.net.URL;

import it.unibo.jetpackjoyride.core.map.api.MapBackgroundView;
import it.unibo.jetpackjoyride.utilities.GameInfo;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class MapBackgroungViewImpl implements MapBackgroundView{

    private final String BACKGROUNG_IMAGE1_PATH = "background/Sector2.png";
    private final String BACKGROUNG_IMAGE2_PATH = "background/Sector3.png";

    private GameInfo gameInfo;
    private ImageView bgImageView1, bgImageView2;
    private final Pane root;

    public MapBackgroungViewImpl(){
        gameInfo = GameInfo.getInstance();
        this.root = new Pane();
        loadBackgroungImage();
    }

    public void updateBackgroundView(double x1,double x2 ,double mapWidth,double mapHeight) {
        if(bgImageView1.getFitWidth() != mapWidth || bgImageView1.getFitHeight() != mapHeight){
            setImageViewSize(bgImageView1, mapWidth, mapHeight);
            setImageViewSize(bgImageView2, mapWidth, mapHeight);
        }
        bgImageView1.setX(x1);
        bgImageView2.setX(x2);
    }

    public Pane getPane(){
        return this.root;
    }

    private void loadBackgroungImage() {

        bgImageView1 = creatImageView(BACKGROUNG_IMAGE1_PATH);
        bgImageView2 = creatImageView(BACKGROUNG_IMAGE2_PATH);

        setImageViewSize(bgImageView1, gameInfo.getScreenWidth(), gameInfo.getScreenHeight());
        setImageViewSize(bgImageView2, gameInfo.getScreenWidth(), gameInfo.getScreenHeight());

        this.root.getChildren().addAll(bgImageView1, bgImageView2);
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
            return new ImageView();
        }
    }

    private void setImageViewSize(ImageView bImageView, double width, double height) {
        bImageView.setFitWidth(width);
        bImageView.setFitHeight(height);
    }
    
}
