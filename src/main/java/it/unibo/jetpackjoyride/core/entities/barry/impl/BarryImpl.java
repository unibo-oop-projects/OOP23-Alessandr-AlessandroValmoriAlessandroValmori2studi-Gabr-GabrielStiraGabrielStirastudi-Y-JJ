package it.unibo.jetpackjoyride.core.entities.barry.impl;

import java.util.Optional;

import it.unibo.jetpackjoyride.core.entities.barry.api.Barry;
import it.unibo.jetpackjoyride.core.entities.obstacle.api.Obstacle.ObstacleType;
import it.unibo.jetpackjoyride.core.hitbox.api.Hitbox;
import it.unibo.jetpackjoyride.core.hitbox.impl.HitboxImpl;
import it.unibo.jetpackjoyride.utilities.GameInfo;
import it.unibo.jetpackjoyride.utilities.Pair;

/**
 * BarryImpl class represents the implementation of the Barry interface,
 * which defines the behavior of the player character in the Jetpack Joyride game.
 *
 * This class manages Barry's position, movement, and status, including walking,
 * falling, and propelling using a jetpack. It also handles hitbox-related operations.
 */

public final class BarryImpl implements Barry {
    // Constants for gravity force and jump force
    private static final double GRAVITY_FORCE = 0.6;
    private static final double JUMP_FORCE = 1.2;

    private double speed;
    private double xPosition;
    private double position;
    private double height;
    private double width;
    private double lowBound;
    private double upBound;
    private BarryStatus status;
    private BarryLifeStatus lifeStatus;
    private Optional<Hitbox> hitbox;
    private boolean hasShield;
    private boolean isActive;
    private int counter = 0;
    private static final int DURATION = 50;

    /**
     * Constructs a new instance of BarryImpl.
     * Initializes the initial state of Barry, including position, speed, status,
     * hitbox, and game information.
     */
    public BarryImpl() {
        this.isActive = true;
        this.lifeStatus = BarryLifeStatus.ALIVE;
        this.status = BarryStatus.WALKING;
        this.width = GameInfo.getInstance().getScreenWidth();
        this.height = GameInfo.getInstance().getScreenHeight();
        this.lowBound = GameInfo.getInstance().getScreenHeight() - GameInfo.getInstance().getScreenHeight() / 8;
        this.upBound = GameInfo.getInstance().getScreenHeight() / 8;
        this.xPosition = GameInfo.getInstance().getScreenWidth() / 6;
        this.position = lowBound;
        this.speed = 0;
        this.hitbox = Optional.of(new HitboxImpl(this.getPosition(), new Pair<>(this.width / 17, this.height / 7)));
    }
    /**
     * Checks if Barry is falling and updates the position accordingly.
     *
     * @param isAlive indicates if Barry is alive
     * @return true if Barry is falling, false otherwise
     */
    private boolean fall(final boolean isAlive) {
        if (this.position + this.speed < lowBound) {
            if (this.position + this.speed < upBound) {
                this.speed = 0;
                this.position = upBound;
            }
            this.speed += GRAVITY_FORCE;
            this.position += this.speed;
            this.status = isAlive ? BarryStatus.FALLING : this.status;
            return true;
        }

        this.position = lowBound;
        this.status = isAlive ? BarryStatus.WALKING : this.status;
        this.speed = 0;
        return false;
    }
    /**
     * Checks if Barry is propelling upwards and updates the position accordingly.
     *
     * @return true if Barry is going up, false otherwise
     */
    private boolean propel() {
        if (this.position + this.speed > upBound) {
            if (this.position + this.speed > lowBound) {
                this.speed = 0;
                this.position = lowBound;
            }
            this.speed -= JUMP_FORCE;
            this.position += this.speed;
            this.status = BarryStatus.PROPELLING;
            return true;
        }

        this.position = upBound;
        this.speed = 0;
        this.status = BarryStatus.HEAD_DRAGGING;
        return false;
    }
    /**
     * Moves Barry based on the jumping condition.
     * If jumping is true, Barry propels upwards; otherwise, Barry falls.
     * Updates the hitbox after the movement.
     *
     * @param jumping true if Barry is jumping, false if not
     * @return true if the movement was successful, false otherwise
     */
    @Override
    public boolean move(final boolean jumping) {
        if (this.lifeStatus.equals(BarryLifeStatus.DEAD)) {
            this.counter++;
            if (counter < DURATION) {
                return true;
            }
            this.fall(false);
            return this.position < lowBound;
        }

        if (jumping) {
            this.propel();
        } else {
            this.fall(true);
        }

        this.hitbox.get().updateHitbox(getPosition(), 0.0);
        return true;
    }
     /**
     * Gets the current status of Barry.
     *
     * @return the current Barry status
     */
    @Override
    public BarryStatus getBarryStatus() {
        return this.status;
    }
    /**
     * Gets the current position of Barry as a Pair of X and Y coordinates.
     *
     * @return the current position of Barry
     */
    @Override
    public Pair<Double, Double> getPosition() {
        return new Pair<>(this.xPosition, this.position);
    }
    /**
     * Gets the hitbox of Barry.
     *
     * @return the hitbox of Barry
     */
    @Override
    public Optional<Hitbox> getHitbox() {
        return this.hitbox;
    }
    /**
     * Checks if Barry has a shield.
     *
     * @return true if Barry has a shield, false otherwise
     */
    @Override
    public boolean hasShield() {
        return hasShield;
    }
    /**
     * Checks if Barry is alive.
     *
     * @return true if Barry is alive, false otherwise
     */
    @Override
    public boolean isAlive() {
        return this.lifeStatus.equals(BarryLifeStatus.ALIVE);
    }
    /**
     * Kills Barry based on the type of obstacle.
     * Updates Barry's hitbox and status accordingly.
     *
     * @param type the type of obstacle that killed Barry
     */
    @Override
    public void kill(final ObstacleType type) {
        this.hitbox = Optional.empty();
        this.lifeStatus = BarryLifeStatus.DEAD;
        this.status = switch (type) {
            case ZAPPER -> BarryStatus.ZAPPED;
            case LASER -> BarryStatus.LASERED;
            case MISSILE -> BarryStatus.BURNED;
            default -> BarryStatus.UNDEFINED;
        };
    }
    /**
     * Removes the shield from Barry.
     */
    @Override
    public void removeShield() {
        this.hasShield = false;
    }
     /**
     * Sets the shield on Barry.
     */
    @Override
    public void setShieldOn() {
        this.hasShield = true;
    }
    /**
     * Sets the life status of Barry.
     *
     * @param lifeStatus the life status of Barry
     */
    @Override
    public void setLifeStatus(final BarryLifeStatus lifeStatus) {
        this.lifeStatus = lifeStatus;
    }
    /**
     * Sets the active value of Barry.
     *
     * @param value the active value of Barry
     */
    @Override
    public void setActiveValue(final boolean value) {
        this.isActive = value;
    }
    /**
     * Checks if Barry is active.
     *
     * @return true if Barry is active, false otherwise
     */
    @Override
    public boolean isActive() {
        return this.isActive;
    }
    /**
     * Updates the limits of Barry based on width and height ratios.
     *
     * @param widthRatio  the ratio for the width of Barry
     * @param heightRatio the ratio for the height of Barry
     */
    @Override
    public void updateLimits(final double widthRatio, final double heightRatio) {
        this.width *= widthRatio;
        this.height *= heightRatio;
        this.lowBound *= heightRatio;
        this.upBound *= heightRatio;
        this.xPosition *= widthRatio;
        this.position *= heightRatio;
    }
}
