package it.unibo.jetpackjoyride.core.map.impl;

import it.unibo.jetpackjoyride.core.map.api.MapBackground;
import it.unibo.jetpackjoyride.utilities.GameInfo;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;



public class MapBackgroundImpl extends Pane implements MapBackground {

   
    private static final int moveSpeed = 2;

    private Image backgroundImage;
    private ImageView bgImageView1, bgImageView2;
    private double bgImageX1, bgImageX2;
    private double mapWidth;
    private double mapHeight;

    private GameInfo gameInfo;

    public MapBackgroundImpl(GameInfo gameInfo){
      
        this.gameInfo = gameInfo;
        mapHeight = gameInfo.getScreenHeight();
        mapWidth = gameInfo.getScreenWidth();
        
        String backgroundImageUrl = getClass().getClassLoader().getResource("background/background1.png").toExternalForm();
        backgroundImage = new Image(backgroundImageUrl);
        bgImageView1 = new ImageView(backgroundImage);

        backgroundImageUrl = getClass().getClassLoader().getResource("background/background2.png").toExternalForm();
        backgroundImage = new Image(backgroundImageUrl);
        bgImageView2 = new ImageView(backgroundImage);

        bgImageView1.setFitWidth(mapWidth);
        bgImageView1.setFitHeight(mapHeight);
        bgImageView2.setFitWidth(mapWidth);
        bgImageView2.setFitHeight(mapHeight);

        bgImageX1 = 0;
        bgImageX2 = mapWidth;

        this.getChildren().addAll(bgImageView1, bgImageView2);
    }

    private void updateSize() {
        double newWidth = gameInfo.getScreenWidth();
        double newHeight = gameInfo.getScreenHeight();

        if (newWidth != mapWidth || newHeight != mapHeight) {
            mapWidth = newWidth;
            mapHeight = newHeight;

            bgImageView1.setFitWidth(mapWidth);
            bgImageView1.setFitHeight(mapHeight);
            bgImageView2.setFitWidth(mapWidth);
            bgImageView2.setFitHeight(mapHeight);
            
        bgImageX1 = 0;
        bgImageX2 = mapWidth;

        }

    }
       
    @Override
    public void updateBackground() {

        updateSize();
        bgImageX1 -= moveSpeed;
        bgImageX2 -= moveSpeed;
    
        if (bgImageX1 <= -mapWidth) {
            bgImageX1 = bgImageX2 + mapWidth;
        }
        if (bgImageX2 <= -mapWidth) {
            bgImageX2 = bgImageX1 + mapWidth;
        }
    
        bgImageView1.setX(bgImageX1);
        bgImageView2.setX(bgImageX2);
    }
}
