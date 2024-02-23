package it.unibo.jetpackjoyride.core.handler.generic;

import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import java.util.List;
import it.unibo.jetpackjoyride.utilities.Pair;

public class EntityView {
    private final ImageView imageView;
    private final List<Image> images;
    private int animationFrame;
    private int animationLenght;
    private int animationCounter;

    private Pair<Double,Double> position;
    private Pair<Double,Double> dimensions;
    private Double angle;

    public EntityView(final List<Image> images) {
        this.imageView = new ImageView();
        this.images = images;
        this.animationFrame = 0;
        this.animationCounter = 0;
    }


    public void updateView() {
        this.animationFrame = (this.animationCounter) / this.animationLenght % this.images.size();
                        
        imageView.setX(this.position.get1() - this.dimensions.get1() / 2);
        imageView.setY(this.position.get2() - this.dimensions.get2() / 2);
        imageView.setRotate(this.angle);

        imageView.setFitWidth(this.dimensions.get1());
        imageView.setFitHeight(this.dimensions.get2());

        imageView.setImage(images.get(this.animationFrame));

        this.animationFrame++;
    }


    public ImageView getImageView() {
        return imageView;
    }
}