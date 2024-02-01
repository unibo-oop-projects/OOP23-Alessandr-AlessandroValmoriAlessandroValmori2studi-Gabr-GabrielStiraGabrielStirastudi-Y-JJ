package it.unibo.jetpackjoyride.core.hitbox;


import java.awt.geom.AffineTransform;

import it.unibo.jetpackjoyride.utilities.Pair;
import java.awt.Polygon;
import java.awt.geom.*;

import java.util.*;




public abstract class AbstractHitbox implements Hitbox {
    private Set<Pair<Double,Double>> hitbox;
    private boolean hitboxStatus = false;

    public AbstractHitbox(Pair<Double,Double> hitboxStartingPos, Pair<Double,Double> hitboxDimensions, Double startingAngle) {
        createHitbox(hitboxStartingPos, hitboxDimensions, startingAngle);
    }

    public void createHitbox(Pair<Double,Double> hitboxStartingPos, Pair<Double,Double> hitboxDimensions, Double startingAngle) {
        final Double width = hitboxDimensions.get1();
        final Double height = hitboxDimensions.get2();
        final Double initialX = hitboxStartingPos.get1() - width/2;
        final Double initialY = hitboxStartingPos.get2() - height/2;

        this.hitbox = new HashSet<>();

        this.hitbox.add(new Pair<>(initialX, initialY));
        this.hitbox.add(new Pair<>(initialX+width, initialY));
        this.hitbox.add(new Pair<>(initialX, initialY+height));
        this.hitbox.add(new Pair<>(initialX+width, initialY+height));

        updateHitbox(hitboxStartingPos, startingAngle);
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

    private Pair<Double,Double> computeCenter() {
        Double maxX = this.hitbox.stream().mapToDouble(p -> p.get1()).max().getAsDouble();
        Double minX = this.hitbox.stream().mapToDouble(p -> p.get1()).min().getAsDouble();
        Double maxY = this.hitbox.stream().mapToDouble(p -> p.get2()).max().getAsDouble();
        Double minY = this.hitbox.stream().mapToDouble(p -> p.get2()).min().getAsDouble();

        return new Pair<>(maxX-(maxX-minX)/2,maxY-(maxY-minY)/2);
    }

    private Pair<Double,Double> computeNewPoint(Pair<Double,Double> toCompute, Pair<Double,Double> anchor, Double angle) {
        AffineTransform rotationTransform = new AffineTransform();
        rotationTransform.rotate(Math.toRadians(angle), anchor.get1(), anchor.get2());

        Point2D oldPoint = new Point2D.Double();
        Point2D newPoint= new Point2D.Double();

        oldPoint.setLocation(Double.valueOf(toCompute.get1()), Double.valueOf(toCompute.get2()));

        rotationTransform.transform(oldPoint, newPoint);
        return new Pair<>(newPoint.getX(), newPoint.getY());
    }
       
    public void updateHitbox(Pair<Double, Double> newPosition, Double angle) {
        final Double newX = newPosition.get1();
        final Double newY = newPosition.get2();

        Set<Pair<Double,Double>> newHitbox = new HashSet<>();

        for(var elem : this.hitbox) {
            Pair<Double,Double> center = computeCenter();
            Pair<Double,Double> newPoint = computeNewPoint(elem, center, angle);
            newHitbox.add(new Pair<>(newPoint.get1() + (newX - center.get1()), newPoint.get2() + (newY - center.get2())));
        };

        this.hitbox = newHitbox;
    }

    public boolean isTouching(Pair<Double,Double> pos) {
        Polygon allPoints = new Polygon();

        for(var vertex : this.hitbox) {
            allPoints.addPoint(vertex.get1().intValue(), vertex.get2().intValue());
        }

        return allPoints.contains(pos.get1(), pos.get2());
    }

    public Pair<Double,Double> getHitboxPosition() {
        return computeCenter();
    }
}
