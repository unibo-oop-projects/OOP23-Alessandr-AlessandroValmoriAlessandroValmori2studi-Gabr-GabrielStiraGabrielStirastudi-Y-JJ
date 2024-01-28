package it.unibo.jetpackjoyride.core.hitbox;

import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;

import it.unibo.jetpackjoyride.utilities.Pair;




public abstract class AbstractHitbox implements Hitbox {
    private Shape hitbox;
    private boolean hitboxStatus = false;

    public AbstractHitbox(Pair<Double,Double> hitboxStartingPos, Pair<Double,Double> hitboxDimensions) {
        createHitbox(hitboxStartingPos, hitboxDimensions);
    }

    public void createHitbox(Pair<Double,Double> hitboxStartingPos, Pair<Double,Double> hitboxDimensions) {
        final int initialX = hitboxStartingPos.get1().intValue();
        final int initialY = hitboxStartingPos.get1().intValue();
        final int width = hitboxDimensions.get1().intValue();
        final int height = hitboxDimensions.get2().intValue();

        this.hitbox = new Rectangle(initialX, initialY, width, height);
    }

    public void setHitboxOn() {
        this.hitboxStatus = true;
    }

    public void setHitboxOff() {
        this.hitboxStatus = false;
    }

    public boolean isHitboxOn() {
        return this.hitboxStatus;
    }
       
    public void updateHitbox(Pair<Double, Double> newPosition, Double rotationAngle) {
        final int newX = newPosition.get1().intValue();
        final int newY = newPosition.get2().intValue();

        AffineTransform rotationTransform = new AffineTransform();

        rotationTransform.rotate(Math.toRadians(rotationAngle), this.hitbox.getBounds().getCenterX(), this.hitbox.getBounds().getCenterY());

        Shape rotatedHitbox = rotationTransform.createTransformedShape(this.hitbox).getBounds();

        //System.out.println("X rel: " + this.hitbox.getBounds().getCenterX() + " Y rel: " + this.hitbox.getBounds().getCenterY());
        //System.out.println("X: " + rotatedHitbox.getBounds().getCenterX() + " Y: " + rotatedHitbox.getBounds().getCenterY());
        
    }

    public Pair<Double,Double> getHitboxPosition() {
        return new Pair<>(this.hitbox.getBounds().getCenterX(), this.hitbox.getBounds().getCenterY());
    }
}
