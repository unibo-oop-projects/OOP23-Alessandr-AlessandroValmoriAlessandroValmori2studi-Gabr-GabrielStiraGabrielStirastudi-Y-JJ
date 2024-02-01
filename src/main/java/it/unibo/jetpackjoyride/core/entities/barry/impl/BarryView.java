package it.unibo.jetpackjoyride.core.entities.barry.impl;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.Node;
import javafx.scene.image.Image;
import it.unibo.jetpackjoyride.core.entities.barry.api.Barry;
import it.unibo.jetpackjoyride.utilities.GameInfo;

public class BarryView {
    private ImageView imageView;
    private Image image;
    private int animationFrame;
    private GameInfo infoResolution;

    public BarryView(Barry barry) {
       
        this.imageView = new ImageView();
        this.infoResolution = new GameInfo();
        this.animationFrame = 0;

        String imagePath = getClass().getClassLoader()
       .getResource("sprites/entities/obstacles/missile/missile_1.png").toExternalForm();

        image = new Image(imagePath);

        this.update(barry);
       

    }

    public void update(Barry barry) {

        imageView.setX(barry.getPosition().get1());
        imageView.setY(barry.getPosition().get2());

        double width = 30.0;
        double height = 50.0;

        imageView.setFitWidth(width);
        imageView.setFitHeight(height);

        imageView.setImage(image);

      
    

    }

    public ImageView getImageView() {
        return imageView;
    }
}