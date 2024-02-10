package it.unibo.jetpackjoyride.core.entities.barry.impl;

import javafx.scene.image.ImageView;
import javafx.scene.image.Image;

import java.util.List;

import it.unibo.jetpackjoyride.core.entities.barry.api.Barry;
import it.unibo.jetpackjoyride.core.entities.barry.api.Barry.BarryStatus;
import it.unibo.jetpackjoyride.utilities.GameInfo;

public class BarryView {

    private ImageView imageView;
    private List<Image> images;
    private int animationFrame;
    private GameInfo infoResolution;
    private BarryStatus oldStatus;

    public BarryView(List<Image> images) {

        this.images = images;
        this.imageView = new ImageView();
        this.infoResolution = GameInfo.getInstance();
        this.animationFrame = 0;
        this.oldStatus = BarryStatus.WALKING;

    }

    

    public void update(Barry barry) {
        double width = infoResolution.getScreenWidth()/8;
        double height = infoResolution.getScreenHeight()/10;

        imageView.setX(barry.getPosition().get1() - width/2);
        imageView.setY(barry.getPosition().get2() - height/2);

        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
  
        imageView.setImage(this.images.get(animationFrame));
        animationFrame = (animationFrame + 1) % images.size();

    }

    public void setCurrentImages(List<Image>images, BarryStatus status){
        if(status != this.oldStatus){
        this.oldStatus=status;
        this.images = images;
        animationFrame=0;
        }
    }

    public ImageView getImageView() {
        return imageView;
    }
}