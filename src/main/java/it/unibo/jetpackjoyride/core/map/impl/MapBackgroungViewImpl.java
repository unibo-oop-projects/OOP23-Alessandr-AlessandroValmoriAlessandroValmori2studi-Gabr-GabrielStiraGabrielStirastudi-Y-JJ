package it.unibo.jetpackjoyride.core.map.impl;

import java.io.FileNotFoundException;
import java.net.URL;

import it.unibo.jetpackjoyride.core.map.api.MapBackground;
import it.unibo.jetpackjoyride.core.map.api.MapBackgroundModel;
import it.unibo.jetpackjoyride.core.map.api.MapBackgroundView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class MapBackgroungViewImpl implements MapBackgroundView{

    private final String BACKGROUNG_IMAGE1_PATH = "background/Sector2.png";
    private final String BACKGROUNG_IMAGE2_PATH = "background/Sector3.png";

    private ImageView bgImageView1, bgImageView2;
    private final Pane root;
    private MapBackground controller;

    public MapBackgroungViewImpl(MapBackground controller){
        this.root = new Pane();
        this.controller = controller;
        loadBackgroungImage();
    }

    public void updateBackgroundView() {
        if(bgImageView1.getFitWidth() != controller.getSize().get1() 
        || bgImageView1.getFitHeight() != controller.getSize().get2()){
            setImageViewSize(bgImageView1, controller.getSize().get1(), controller.getSize().get2());
            setImageViewSize(bgImageView2, controller.getSize().get1(), controller.getSize().get2());
        }
        bgImageView1.setX(controller.getPosX().get1());
        bgImageView2.setX(controller.getPosX().get2());
    }

    public Pane getPane(){
        return this.root;
    }

    private void loadBackgroungImage() {

        bgImageView1 = creatImageView(BACKGROUNG_IMAGE1_PATH);
        bgImageView2 = creatImageView(BACKGROUNG_IMAGE2_PATH);

        setImageViewSize(bgImageView1, controller.getSize().get1(), controller.getSize().get2());
        setImageViewSize(bgImageView2,controller.getSize().get1(), controller.getSize().get2());

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
