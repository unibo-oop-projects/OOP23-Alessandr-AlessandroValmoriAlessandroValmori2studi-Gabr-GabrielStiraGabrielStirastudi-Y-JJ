package it.unibo.jetpackjoyride.core.hitbox.api;

import it.unibo.jetpackjoyride.utilities.GameInfo;
import it.unibo.jetpackjoyride.utilities.Pair;
import java.awt.Polygon;
import java.awt.geom.*;

import java.util.*;

public abstract class AbstractHitbox implements Hitbox {
    private Set<Pair<Double, Double>> hitbox;
    private Pair<Double, Double> screenLastSize;
    private Pair<Double, Double> hitboxDimensions;

    public AbstractHitbox(final Pair<Double, Double> hitboxStartingPos, final Pair<Double, Double> hitboxDimensions) {
                this.screenLastSize = new Pair<>(GameInfo.getInstance().getScreenWidth(),
                GameInfo.getInstance().getScreenHeight());
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

    @Override
    public Pair<Double,Double> getHitboxDimensions() {
        return this.hitboxDimensions;
    }

    private Pair<Double, Double> computeCenter() {
        final Double maxX = this.hitbox.stream().mapToDouble(p -> p.get1()).max().getAsDouble();
        final Double minX = this.hitbox.stream().mapToDouble(p -> p.get1()).min().getAsDouble();
        final Double maxY = this.hitbox.stream().mapToDouble(p -> p.get2()).max().getAsDouble();
        final Double minY = this.hitbox.stream().mapToDouble(p -> p.get2()).min().getAsDouble();

        return new Pair<>(maxX - (maxX - minX) / 2, maxY - (maxY - minY) / 2);
    }

    private Pair<Double, Double> computeNewPoint(final Pair<Double, Double> toCompute, final Pair<Double, Double> anchor,
            final Double angle) {
        final AffineTransform rotationTransform = new AffineTransform();
        rotationTransform.rotate(Math.toRadians(angle), anchor.get1(), anchor.get2());

        final Point2D oldPoint = new Point2D.Double();
        final Point2D newPoint = new Point2D.Double();

        oldPoint.setLocation(Double.valueOf(toCompute.get1()), Double.valueOf(toCompute.get2()));

        rotationTransform.transform(oldPoint, newPoint);
        return new Pair<>(newPoint.getX(), newPoint.getY());
    }

    @Override
    public void updateHitbox(final Pair<Double, Double> newPosition, final Double angle) {
        final Double screenSizeX = GameInfo.getInstance().getScreenWidth();
        final Double screenSizeY = GameInfo.getInstance().getScreenHeight();
        final Pair<Double, Double> currentScreenSize = new Pair<>(screenSizeX, screenSizeY);

        if(!this.screenLastSize.equals(currentScreenSize)) {
            final Double xChange = currentScreenSize.get1() / this.screenLastSize.get1();
            final Double yChange = currentScreenSize.get2() / this.screenLastSize.get2();
            this.screenLastSize = currentScreenSize;
            this.hitboxDimensions = new Pair<>(this.hitboxDimensions.get1()*xChange, this.hitboxDimensions.get2()*yChange);
            this.createHitbox(newPosition);
            return;
        }

        final Double newX = newPosition.get1();
        final Double newY = newPosition.get2();

        final Set<Pair<Double, Double>> newHitbox = new HashSet<>();

        for (final var elem : this.hitbox) {
            final Pair<Double, Double> center = computeCenter();
            final Pair<Double, Double> newPoint = computeNewPoint(elem, center, angle);
            newHitbox.add(
                    new Pair<>(newPoint.get1() + (newX - center.get1()), newPoint.get2() + (newY - center.get2())));
        }

        this.hitbox = newHitbox;
    }

    @Override
    public boolean isTouching(final Pair<Double, Double> pos) {
        final Polygon allPoints = new Polygon();

        for (final var vertex : this.hitbox) {
            allPoints.addPoint(vertex.get1().intValue(), vertex.get2().intValue());
        }

        return allPoints.contains(pos.get1(), pos.get2());
    }

    @Override
    public Pair<Double, Double> getHitboxPosition() {
        return computeCenter();
    }

    @Override
    public Set<Pair<Double, Double>> getHitboxVertex() {
        return this.hitbox;
    }
}
