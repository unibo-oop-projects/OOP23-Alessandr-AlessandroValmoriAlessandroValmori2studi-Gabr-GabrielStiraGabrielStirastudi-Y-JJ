package it.unibo.jetpackjoyride.core.map.impl;

import java.io.FileNotFoundException;
import java.net.URL;

import it.unibo.jetpackjoyride.core.map.api.MapBackground;
import it.unibo.jetpackjoyride.utilities.GameInfo;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;



public class MapBackgroundImpl extends Pane implements MapBackground {

   
    private int moveSpeed;

    private ImageView bgImageView1, bgImageView2;
    private double bgImageX1, bgImageX2;
    private double mapWidth;
    private double mapHeight;

    private GameInfo gameInfo;

    public MapBackgroundImpl(GameInfo gameInfo){
      
        this.gameInfo = gameInfo;
        moveSpeed = GameInfo.moveSpeed;
        mapHeight = gameInfo.getScreenHeight();
        mapWidth = gameInfo.getScreenWidth();
        loadBackgroungImage();
        
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

    private void loadBackgroungImage(){

        bgImageView1 = creatImageView("background/background1.png");
        bgImageView2 = creatImageView("background/background2.png");

        setImageViewSize(bgImageView1, mapWidth, mapHeight);
        setImageViewSize(bgImageView2, mapWidth, mapHeight);

        bgImageX1 = 0;
        bgImageX2 = mapWidth;

        this.getChildren().addAll(bgImageView1, bgImageView2);
    }

    private ImageView creatImageView(String path){

        try {
            URL backgroundImageUrl = getClass().getClassLoader().getResource(path);
            if(backgroundImageUrl == null){
                throw new FileNotFoundException();
            }
            String url = backgroundImageUrl.toExternalForm();
            Image backgroundImage = new Image(url);
            ImageView backImageView = new ImageView(backgroundImage);
            return backImageView;
        } catch (FileNotFoundException e) {
            System.out.println("The Image was not found");
        }
        return null;
    }

    private void setImageViewSize(ImageView bImageView,double width, double height){
            bImageView.setFitWidth(width);
            bImageView.setFitHeight(height);
    }
}
