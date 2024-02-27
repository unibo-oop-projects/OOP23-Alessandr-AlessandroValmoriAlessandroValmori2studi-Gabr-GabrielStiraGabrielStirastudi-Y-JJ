package it.unibo.jetpackjoyride.core.hitbox.api;

import it.unibo.jetpackjoyride.utilities.Pair;
import java.awt.Polygon;
import java.awt.geom.Point2D;
import java.awt.geom.AffineTransform;
import java.util.Set;
import java.util.HashSet;
import java.util.Collections;

 /**
 * The {@link AbstractHitbox} class is used to implements all methods and characteristics
 * of the {@link Hitbox} class. This class is used by all entities to keep track of a set 
 * of coordinates which define the collision bounds. Also, with every call of the updateHitbox
 * method, the hitbox is re-calculated based on the new position and angle (there can be rotating
 * entities). A method for collision detection is also provided (isTouching).
 * 
 * @author gabriel.stira@studio.unibo.it
 */
public abstract class AbstractHitbox implements Hitbox {
    /**
     * Defines the four coordinates (x, y) which correspond to the vertices of the polygon which
     * form the hitbox.
     */
    private Set<Pair<Double, Double>> hitbox;
    /**
     * Defines the dimensions (width, height) of the hitbox.
     */
    private final Pair<Double, Double> hitboxDimensions;
    /**
     * Defines the current position and center of the hitbox.
     * Since the hitbox is a rectangle, the center is the point of intersection of the diagonals.
     * The center of an ACTIVE entity hitbox is always equal to the position of the entity.
     */
    private Pair<Double, Double> hitboxPosition;
    /**
     * Defines the angle of rotation of the rectangle which form the hitbox.
     */
    private Double hitboxRotation;

    /**
     * Constructor to create an instance of AbstractHitbox.
     * @param hitboxStartingPos The hitbox starting position.
     * @param hitboxDimensions The hitbox dimensions.
     * @param hitboxRotation The hitbox rotation characteristics.
     */
    public AbstractHitbox(final Pair<Double, Double> hitboxStartingPos, 
                          final Pair<Double, Double> hitboxDimensions, 
                          final Double hitboxRotation) {
        this.hitboxRotation = hitboxRotation;
        this.hitboxPosition = hitboxStartingPos;
        this.hitboxDimensions = hitboxDimensions;
        this.createHitbox(hitboxStartingPos);
    }

    /**
     * Computes the new four coordinates which describe the vertices of the 
     * hitbox. The coordinates are then added to the hitbox set.
     * 
     * @param hitboxNewPos The new position of the hitbox.
     */
    private void createHitbox(final Pair<Double, Double> hitboxNewPos) {
        final Double width = this.hitboxDimensions.get1();
        final Double height = this.hitboxDimensions.get2();

        final Double initialX = hitboxNewPos.get1() - width / 2;
        final Double initialY = hitboxNewPos.get2() - height / 2;
        this.hitbox = new HashSet<>();
        this.hitbox.add(new Pair<>(initialX, initialY));
        this.hitbox.add(new Pair<>(initialX + width, initialY));
        this.hitbox.add(new Pair<>(initialX, initialY + height));
        this.hitbox.add(new Pair<>(initialX + width, initialY + height));
    }

    private Pair<Double, Double> computeNewPoint(
            final Pair<Double, Double> toCompute, 
            final Pair<Double, Double> anchor, 
            final Double angle) {
        final AffineTransform rotationTransform = new AffineTransform();
        rotationTransform.rotate(Math.toRadians(angle), anchor.get1(), anchor.get2());

        final Point2D oldPoint = new Point2D.Double();
        final Point2D newPoint = new Point2D.Double();

        oldPoint.setLocation(toCompute.get1(), toCompute.get2());

        rotationTransform.transform(oldPoint, newPoint);
        return new Pair<>(newPoint.getX(), newPoint.getY());
    }

    @Override
    public final void updateHitbox(final Pair<Double, Double> newPosition, final Double angle) {
        this.hitboxRotation = angle;
        this.hitboxPosition = newPosition;
        this.createHitbox(newPosition);

        final Set<Pair<Double, Double>> newHitbox = new HashSet<>();

        for (final var elem : this.hitbox) {
            final Pair<Double, Double> newPoint = computeNewPoint(elem, this.hitboxPosition, angle);
            newHitbox.add(
                    new Pair<>(newPoint.get1() + (newPosition.get1() - this.hitboxPosition.get1()),
                            newPoint.get2() + (newPosition.get2() - this.hitboxPosition.get2())));
        }

        this.hitbox = newHitbox;
    }

    /**
     * Is used to avoid code duplication in the isTouching method.
     * Since the same operation has to be performed twice, this method is
     * called two times instead.
     * Check if one of the vertices or the center of one hitbox is inside the polygon
     * described by the vertices of the other.
     * 
     * @param firstHitbox The first hitbox.
     * @param secondHitbox The second hitbox.
     * @return True if the two hitbox are colliding, false otherwise.
     */
    private boolean isTouchingHelper(final Hitbox firstHitbox, final Hitbox secondHitbox) {
        final Polygon allPoints = new Polygon();

        firstHitbox.getHitboxVertex().stream()
                .forEach(vertex -> allPoints.addPoint(
                    vertex.get1().intValue(), vertex.get2().intValue()));

        boolean isTouching;
        isTouching = secondHitbox.getHitboxVertex().stream()
            .anyMatch(otherVertex -> allPoints.contains(otherVertex.get1(), otherVertex.get2()));

        if (allPoints.contains(secondHitbox.getHitboxPosition().get1(), secondHitbox.getHitboxPosition().get2())) {
            isTouching = true;
        }

        return isTouching;
    }

    @Override
    public final boolean isTouching(final Hitbox otherHitbox) {
        return this.isTouchingHelper(this, otherHitbox) || this.isTouchingHelper(otherHitbox, this);
    }

    @Override
    public final Pair<Double, Double> getHitboxPosition() {
        return this.hitboxPosition;
    }

    @Override
    public final Double getHitboxRotation() {
        return this.hitboxRotation;
    }

    @Override
    public final Pair<Double, Double> getHitboxDimensions() {
        return this.hitboxDimensions;
    }

    @Override
    public final Set<Pair<Double, Double>> getHitboxVertex() {
        return Collections.unmodifiableSet(this.hitbox);
    }
}
