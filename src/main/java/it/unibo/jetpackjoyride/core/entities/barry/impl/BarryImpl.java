/**
 * BarryImpl class represents the implementation of the Barry interface,
 * which defines the behavior of the player character in the Jetpack Joyride game.
 * 
 * This class manages Barry's position, movement, and status, including walking,
 * falling, and propelling using a jetpack. It also handles hitbox-related operations.
 */
package it.unibo.jetpackjoyride.core.entities.barry.impl;

import it.unibo.jetpackjoyride.core.entities.barry.api.Barry;
import it.unibo.jetpackjoyride.core.hitbox.impl.PlayerHitbox;
import it.unibo.jetpackjoyride.utilities.GameInfo;
import it.unibo.jetpackjoyride.utilities.Pair;

/**
 * BarryImpl class implements the Barry interface and provides the functionality
 * for controlling the player character, Barry, in the Jetpack Joyride game.
 */
public class BarryImpl implements Barry {

    private final double DOWNWARD_ACC = 0.6; // gravity
    private final double UPWARDS_ACC = 1.2; // jetpack propulsion

    private double speed; // can be negative or positive, negative goes up, positive down
    // its standard value is 0, when Barry is walking

    private final double X_POSITION = 100.0; // fixed x position
    private double position; // variable y position

    private final double GROUND_LIMIT;
    private final double CEILING_LIMIT = 30.0;

    private BarryStatus status; // walking, falling ...

    private PlayerHitbox hitbox;

    private GameInfo gameInfo;

    /* FUTURE */
    private int coins;
    private int distance;
    /* ------- */

    /**
     * Constructs a new instance of BarryImpl.
     * Initializes the initial state of Barry, including position, speed, status, hitbox, and game information.
     */
    public BarryImpl() {
        this.status = BarryStatus.WALKING;
        gameInfo = GameInfo.getInstance();

        this.GROUND_LIMIT = gameInfo.getScreenHeight() / 1.2;
        this.position = GROUND_LIMIT;
        this.speed = 0;
        this.hitbox = new PlayerHitbox(this.getPosition(), 0.0);
        this.hitbox.setHitboxOn();
    }

    /**
     * Checks if Barry is falling and updates the position accordingly.
     * 
     * @return true if Barry is falling, false otherwise
     */
    private boolean fall() {
        if (this.position + this.speed < GROUND_LIMIT) {
            if (this.position + this.speed < CEILING_LIMIT) {
                this.speed = 0;
                this.position = CEILING_LIMIT;
            }
            this.speed += this.DOWNWARD_ACC;
            this.position += this.speed;
            this.status = BarryStatus.FALLING;
            return true;
        }

        this.position = GROUND_LIMIT;
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
        if (this.position + this.speed > CEILING_LIMIT) {
            if (this.position + this.speed > GROUND_LIMIT) {
                this.speed = 0;
                this.position = GROUND_LIMIT;
            }
            this.speed -= this.UPWARDS_ACC;
            this.position += this.speed;
            this.status = BarryStatus.PROPELLING;
            return true;
        }

        this.position = CEILING_LIMIT;
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
    public void move(boolean jumping) {
        if (jumping) {
            this.propel();
        } else if (!this.status.equals(BarryStatus.WALKING)) {
            this.fall();
        }

        this.hitbox.updateHitbox(getPosition(), 0.0);
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
        return new Pair<>(this.X_POSITION, this.position);
    }

    /**
     * Gets the hitbox of Barry.
     * 
     * @return the hitbox of Barry
     */
    @Override
    public PlayerHitbox getHitbox() {
        return this.hitbox;
    }
}
