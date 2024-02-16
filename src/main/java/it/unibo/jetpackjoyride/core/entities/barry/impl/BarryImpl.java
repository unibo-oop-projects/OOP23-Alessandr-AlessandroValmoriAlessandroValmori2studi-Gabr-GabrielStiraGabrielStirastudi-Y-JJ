/**
 * BarryImpl class represents the implementation of the Barry interface,
 * which defines the behavior of the player character in the Jetpack Joyride game.
 *
 * This class manages Barry's position, movement, and status, including walking,
 * falling, and propelling using a jetpack. It also handles hitbox-related operations.
 */
package it.unibo.jetpackjoyride.core.entities.barry.impl;

import it.unibo.jetpackjoyride.core.entities.barry.api.Barry;
import it.unibo.jetpackjoyride.core.hitbox.impl.HitboxImpl;
import it.unibo.jetpackjoyride.utilities.GameInfo;
import it.unibo.jetpackjoyride.utilities.Pair;

/**
 * BarryImpl class implements the Barry interface and provides the functionality
 * for controlling the player character, Barry, in the Jetpack Joyride game.
 */
public final class BarryImpl implements Barry {

    private static final double GRAVITYFORCE = 0.6; // gravity
    private static final double JUMPFORCE = 1.2; // jetpack propulsion

    private double speed; // can be negative or positive, negative goes up, positive down
    // its standard value is 0, when Barry is walking

    private double x_position; // fixed x position
    private double position; // variable y position

    private final double height;
    private final double width;

    private double lowBound;
    private double upBound;

    private BarryStatus status; // walking, falling ..
    private BarryLifeStatus lifeStatus; // dead or alive

    private final HitboxImpl hitbox;

    private final GameInfo gameInfo;

    private boolean hasShield = true;

    /**
     * Constructs a new instance of BarryImpl.
     * Initializes the initial state of Barry, including position, speed, status,
     * hitbox, and game information.
     */
    public BarryImpl() {
        this.lifeStatus = BarryLifeStatus.ALIVE;
        this.status = BarryStatus.WALKING;
        gameInfo = GameInfo.getInstance();
        this.width = gameInfo.getDefaultWidth();
        this.height = gameInfo.getScreenHeight();
        this.lowBound = gameInfo.getScreenHeight() - gameInfo.getScreenHeight() / 8;
        this.upBound = gameInfo.getScreenHeight() / 8;
        this.x_position = gameInfo.getDefaultWidth() / 6;
        this.position = lowBound;
        this.speed = 0;
        this.hitbox = new HitboxImpl(this.getPosition(), new Pair<>(120.0, 60.0), 0.0);
    }

    /**
     * Checks if Barry is falling and updates the position accordingly.
     *
     * @return true if Barry is falling, false otherwise
     */
    private boolean fall() {
        if (this.position + this.speed < lowBound) {
            if (this.position + this.speed < upBound) {
                this.speed = 0;
                this.position = upBound;
            }
            this.speed += this.GRAVITYFORCE;
            this.position += this.speed;
            this.status = BarryStatus.FALLING;

            return true;
        }

        this.position = lowBound;
        this.status = BarryStatus.WALKING;
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
            this.speed -= this.JUMPFORCE;
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
     */
    @Override
    public void move(final boolean jumping) {
        if (jumping) {
            this.propel();
        } else {
            this.fall();
        }

        this.hitbox.updateHitbox(getPosition(), 0.0);

        final double height = GameInfo.getInstance().getScreenHeight();
        final double width = GameInfo.getInstance().getScreenWidth();

    

        if (height != this.height || width != this.width) {
            this.updateScreen(width, height);
        }
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
        return new Pair<>(this.x_position, this.position);
    }

    /**
     * Gets the hitbox of Barry.
     *
     * @return the hitbox of Barry
     */
    @Override
    public HitboxImpl getHitbox() {
        return this.hitbox;
    }

    private void updateScreen(final double width, final double height) {
        this.lowBound = height - height / 8;
        this.upBound = height / 8;
        this.x_position = width / 6;

    }

    @Override
    public boolean hasShield() {
        return hasShield;
    }

    @Override
    public void setLifeStatus(BarryLifeStatus status) {
        if(status.equals(BarryLifeStatus.ALIVE) || status.equals(BarryLifeStatus.DEAD)){
            throw new IllegalArgumentException();
        }
        else{
            this.lifeStatus = status;
        }
    }

    @Override
    public boolean isAlive() {
        return this.lifeStatus.equals(BarryLifeStatus.ALIVE);
    }
}
