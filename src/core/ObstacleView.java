/*package com.fxversion;

import java.util.Random;
import javafx.scene.shape.Rectangle;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;

public class ObstacleView {
    private ImageView imageView;
    private Image[] images;
    private Image[] warnings;
    private int animationFrame;

    public ObstacleView(Image[] images, Image[] warnings) {
        this.images = images;
        this.warnings = warnings;
        imageView = new ImageView();
        this.animationFrame = 0;
    }

    public void updateView(ObstacleModel model) {
        imageView.setX(model.getX());
        imageView.setY(model.getY());
        imageView.setFitWidth(model.getWidth());
        imageView.setFitHeight(model.getHeight());

        if (model.isWarning()) {
            imageView.setFitWidth(model.getWidth()*0.8);
            imageView.setFitHeight(model.getHeight()*0.8);
            imageView.setX(model.getX()-model.getWidth()*0.8);
            imageView.setImage(warnings[model.getTimer() / 40 % 2]);
        } else {
            imageView.setImage(images[animationFrame]);
            animationFrame = (animationFrame + 1) % images.length;
        }
    }

    public ImageView getImageView() {
        return imageView;
    }
}*/