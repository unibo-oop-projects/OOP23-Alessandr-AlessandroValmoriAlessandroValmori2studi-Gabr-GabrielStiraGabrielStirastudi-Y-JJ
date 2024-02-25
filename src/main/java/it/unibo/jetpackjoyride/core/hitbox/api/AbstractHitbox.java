package it.unibo.jetpackjoyride.core.hitbox.api;

import it.unibo.jetpackjoyride.utilities.Pair;
import java.awt.Polygon;
import java.awt.geom.*;

import java.util.*;

public abstract class AbstractHitbox implements Hitbox {
    private Set<Pair<Double, Double>> hitbox;
    private Pair<Double, Double> hitboxDimensions;
    private Pair<Double, Double> hitboxPosition;
    private Double hitboxRotation;

    public AbstractHitbox(final Pair<Double, Double> hitboxStartingPos, final Pair<Double, Double> hitboxDimensions, final Double hitboxRotation) {
        this.hitboxRotation = hitboxRotation;
        this.hitboxPosition = hitboxStartingPos;    
        this.hitboxDimensions = hitboxDimensions;
        this.createHitbox(hitboxStartingPos);
    }

    private void createHitbox(final Pair<Double, Double> hitboxStartingPos) {
        final Double width = this.hitboxDimensions.get1();
        final Double height = this.hitboxDimensions.get2();

        final Double initialX = hitboxStartingPos.get1() - width / 2;
        final Double initialY = hitboxStartingPos.get2() - height / 2;
        this.hitbox = new HashSet<>();
        this.hitbox.add(new Pair<>(initialX, initialY));
        this.hitbox.add(new Pair<>(initialX + width, initialY));
        this.hitbox.add(new Pair<>(initialX, initialY + height));
        this.hitbox.add(new Pair<>(initialX + width, initialY + height));
    }

    private Pair<Double, Double> computeNewPoint(final Pair<Double, Double> toCompute, final Pair<Double, Double> anchor, final Double angle) {
        final AffineTransform rotationTransform = new AffineTransform();
        rotationTransform.rotate(Math.toRadians(angle), anchor.get1(), anchor.get2());

        final Point2D oldPoint = new Point2D.Double();
        final Point2D newPoint = new Point2D.Double();

        oldPoint.setLocation(toCompute.get1(), toCompute.get2());

        rotationTransform.transform(oldPoint, newPoint);
        return new Pair<>(newPoint.getX(), newPoint.getY());
    }

    @Override
    public void updateHitbox(final Pair<Double, Double> newPosition, final Double angle) {
        this.hitboxRotation = angle;
        this.hitboxPosition = newPosition;
        this.createHitbox(newPosition);

        final Set<Pair<Double, Double>> newHitbox = new HashSet<>();

        for (final var elem : this.hitbox) {
            final Pair<Double, Double> newPoint = computeNewPoint(elem, this.hitboxPosition, angle);
            newHitbox.add(
                    new Pair<>(newPoint.get1() + (newPosition.get1() - this.hitboxPosition.get1()), newPoint.get2() + (newPosition.get2() - this.hitboxPosition.get2())));
        }

        this.hitbox = newHitbox;
    }

    private boolean isTouchingHelper(final Hitbox firstHitbox, final Hitbox secondHitbox) {
        final Polygon allPoints = new Polygon();
        boolean isTouching = false;

        for (final var vertex : firstHitbox.getHitboxVertex()) {
            allPoints.addPoint(vertex.get1().intValue(), vertex.get2().intValue());
        }

        for(final var otherVertex : secondHitbox.getHitboxVertex()) {
            if(allPoints.contains(otherVertex.get1(), otherVertex.get2())) {
                isTouching = true;
            }
        }

        if(allPoints.contains(secondHitbox.getHitboxPosition().get1(), secondHitbox.getHitboxPosition().get2())) {
            isTouching = true;
        }

        return isTouching;
    }

    @Override
    public boolean isTouching(final Hitbox otherHitbox) {
        return this.isTouchingHelper(this, otherHitbox) || this.isTouchingHelper(otherHitbox, this);
    }

    @Override
    public Pair<Double, Double> getHitboxPosition() {
        return this.hitboxPosition;
    }

    @Override
    public Double getHitboxRotation() {
        return this.hitboxRotation;
    }

    @Override
    public Pair<Double,Double> getHitboxDimensions() {
        return this.hitboxDimensions;
    }

    @Override
    public Set<Pair<Double, Double>> getHitboxVertex() {
        return Collections.unmodifiableSet(this.hitbox);
    }
}