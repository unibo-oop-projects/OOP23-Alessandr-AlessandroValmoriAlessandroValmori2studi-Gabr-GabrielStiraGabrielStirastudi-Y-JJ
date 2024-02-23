package it.unibo.jetpackjoyride.core.handler.generic;

import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import java.util.List;

import it.unibo.jetpackjoyride.core.hitbox.api.Hitbox;
import it.unibo.jetpackjoyride.core.view.AnimationInfo;
import it.unibo.jetpackjoyride.utilities.GameInfo;
import it.unibo.jetpackjoyride.utilities.Pair;

public class EntityView {
    private final ImageView imageView;
    private final List<Image> images;
    private int animationFrame;
    private int animationLenght;
    private int animationCounter;

    private Pair<Double,Double> position;
    private Pair<Double,Double> dimensions;
    private Integer animationSpeed;
    private Pair<Integer,Integer> range;
    private Double angle;
    private boolean repeatedAnimation;

    public EntityView(final List<Image> images) {
        this.imageView = new ImageView();
        this.images = images;
        this.animationFrame = 0;
        this.animationCounter = 0;
        this.repeatedAnimation = true;
    }

    public void setAnimationInfos(final Hitbox entityHitbox, final AnimationInfo animationInfo) {
        this.angle = entityHitbox.getHitboxRotation();
        this.position = entityHitbox.getHitboxPosition();
        this.dimensions = entityHitbox.getHitboxDimensions();
        this.range = new Pair<>(animationInfo.rangeLow(), animationInfo.rangeHigh());
        this.animationSpeed = animationInfo.speedOfAnimation();
        this.repeatedAnimation = animationInfo.repetedAnimation();
        this.animationLenght = Math.abs(this.range.get2() - this.range.get1() + 1);
    }

    public void updateView() {
        final GameInfo infoResolution = GameInfo.getInstance();
        final Double scaleX = infoResolution.getScreenWidth() / infoResolution.getDefaultWidth();
        final Double scaleY = infoResolution.getScreenHeight() / infoResolution.getDefaultHeight();

        if(this.repeatedAnimation) {
            this.animationFrame = (this.animationCounter) / this.animationSpeed % this.animationLenght;
        }

        this.position = new Pair<>((this.position.get1()-this.dimensions.get1()/2)*scaleX, (this.position.get2()-this.dimensions.get2()/2)*scaleY);
        this.dimensions = new Pair<>(this.dimensions.get1()*scaleX, this.dimensions.get2()*scaleY);

        imageView.setX(this.position.get1());
        imageView.setY(this.position.get2());
        imageView.setRotate(this.angle);

        imageView.setFitWidth(this.dimensions.get1());
        imageView.setFitHeight(this.dimensions.get2());

        imageView.setImage(images.get(this.animationFrame));

        this.animationCounter++;
    }


    public ImageView getImageView() {
        return imageView;
    }
}