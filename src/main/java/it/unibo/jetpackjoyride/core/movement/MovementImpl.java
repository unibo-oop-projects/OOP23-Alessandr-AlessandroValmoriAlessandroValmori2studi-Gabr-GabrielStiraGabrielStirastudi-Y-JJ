package it.unibo.jetpackjoyride.core.movement;

import java.util.ArrayList;
import java.util.List;

import it.unibo.jetpackjoyride.utilities.GameInfo;
import it.unibo.jetpackjoyride.utilities.Pair;

public class MovementImpl implements Movement {

    private final static Double SPEEDYMODIFIER = 1.5;
    private final static Double SLOWMODIFIER = 0.7;
    private final static Double GRAVITYMODIFIER = 0.5;
    private final static Double INVERSEGRAVITYMODIFIER = -0.5;

    private Pair<Double, Double> position;
    private Pair<Double, Double> acceleration;
    private Pair<Double, Double> speed;
    private Pair<Double, Double> rotation;
    private List<MovementChangers> listOfChangers = new ArrayList<>();

    private Pair<Double, Double> screenLastSize;

    public MovementImpl(Pair<Double, Double> startingPosition, Pair<Double, Double> startingSpeed,
            Pair<Double, Double> startingAcceleration, Pair<Double, Double> rotationInfo,
            List<MovementChangers> listOfChangers) {
        this.position = startingPosition;
        this.speed = startingSpeed;
        this.acceleration = startingAcceleration;
        this.rotation = rotationInfo;
        this.setMovementChangers(listOfChangers);

        this.screenLastSize = new Pair<>(GameInfo.getInstance().getScreenWidth(),
                GameInfo.getInstance().getScreenHeight());
    }

    @Override
    public Pair<Double, Double> getCurrentPosition() {
        return this.position;
    }

    @Override
    public Pair<Double, Double> getSpeed() {
        return this.speed;
    }

    @Override
    public Pair<Double, Double> getAcceleration() {
        return this.acceleration;
    }

    @Override
    public List<MovementChangers> getMovementChangers() {
        return this.listOfChangers;
    }

    @Override
    public Pair<Double, Double> getRotation() {
        return this.rotation;
    }

    @Override
    public void setCurrentPosition(Pair<Double, Double> currPos) {
        this.position = currPos;
    }

    @Override
    public void setSpeed(Pair<Double, Double> newSpeed) {
        this.speed = newSpeed;
    }

    @Override
    public void setAcceleration(Pair<Double, Double> newAcceleration) {
        this.acceleration = newAcceleration;
    }

    @Override
    public void setMovementChangers(List<MovementChangers> listOfChangers) {
        this.listOfChangers = listOfChangers;

        final Double speedModifier = (this.listOfChangers.contains(MovementChangers.SPEEDY) ? SPEEDYMODIFIER
                : this.listOfChangers.contains(MovementChangers.SLOW) ? SLOWMODIFIER : 1.0);
        this.speed = new Pair<>(this.speed.get1() * speedModifier, this.speed.get2() * speedModifier);

        final Double accelerationModifier = (this.listOfChangers.contains(MovementChangers.GRAVITY) ? GRAVITYMODIFIER
                : this.listOfChangers.contains(MovementChangers.INVERSEGRAVITY) ? INVERSEGRAVITYMODIFIER : 0.0);
        this.acceleration = new Pair<>(this.acceleration.get1(), accelerationModifier);
    }

    @Override
    public void setRotation(Pair<Double, Double> newRotationInfo) {
        this.rotation = newRotationInfo;
    }

    @Override
    public void update() {
        final Double screenSizeX = GameInfo.getInstance().getScreenWidth();
        final Double screenSizeY = GameInfo.getInstance().getScreenHeight();

        this.checkForScreen(screenSizeX, screenSizeY);
        this.applyModifiers(screenSizeX, screenSizeY);

        /* V = U + A */
        this.speed = new Pair<>(this.speed.get1() + this.acceleration.get1(),
                this.speed.get2() + this.acceleration.get2());
        /* S = So + V */
        this.position = new Pair<>(this.position.get1() + this.speed.get1(), this.position.get2() + this.speed.get2());
        this.rotation = new Pair<>(this.rotation.get1() + this.rotation.get2(), this.rotation.get2());
    }

    private void checkForScreen(Double screenSizeX, Double screenSizeY) {
        Pair<Double, Double> currentScreenSize = new Pair<>(screenSizeX, screenSizeY);

        if (!this.screenLastSize.equals(currentScreenSize)) {
            final Double xChange = currentScreenSize.get1() / this.screenLastSize.get1();
            final Double yChange = currentScreenSize.get2() / this.screenLastSize.get2();

            this.acceleration = new Pair<>(this.acceleration.get1() * xChange, this.acceleration.get2() * yChange);
            this.speed = new Pair<>(this.speed.get1() * xChange, this.speed.get2() * yChange);
            this.position = new Pair<>(this.position.get1() * xChange, this.position.get2() * yChange);

            this.screenLastSize = currentScreenSize;
        }
    }

    private void applyModifiers(Double sizeX, Double sizeY) {
        final Double mapBoundUp = sizeY / 8;
        final Double mapBoundDown = sizeY - sizeY / 8;

        /* BOUNCING */
        if (this.listOfChangers.contains(MovementChangers.BOUNCING)) {
            if (this.position.get2() < mapBoundUp) {
                this.speed = new Pair<>(this.speed.get1(), Math.abs(this.speed.get2()));
                this.rotation = new Pair<>(-Math.abs(this.rotation.get1()), this.rotation.get2());
            }
            if (this.position.get2() > mapBoundDown) {
                this.speed = new Pair<>(this.speed.get1(), -Math.abs(this.speed.get2()));
                this.rotation = new Pair<>(Math.abs(this.rotation.get1()), this.rotation.get2());
            }
        }

        /* HOMING */
        /*
         * if(this.listOfChangers.contains(MovementChangers.HOMING)) {
         * if(this.position.get2() != player.getPosY() ) {
         * this.acceleration = new Pair<>(this.acceleration.get1(),
         * this.acceleration.get2() + (player.getPosY() - this.position.get2()));
         * }
         * }
         */

        /* STATIC */
        if (this.listOfChangers.contains(MovementChangers.STATIC)) {
            this.acceleration = new Pair<>(0.0, 0.0);
            this.speed = new Pair<>(0.0, 0.0);
        }

        /* BOUNDS */
        if (this.listOfChangers.contains(MovementChangers.BOUNDS)) {
            if (this.position.get2() > mapBoundDown) {
                this.position = new Pair<>(this.position.get1(), mapBoundDown);
                if (this.speed.get2() > 0) {
                    this.speed = new Pair<>(this.speed.get1(), 0.0);
                }
            }
            if (this.position.get2() < mapBoundUp) {
                this.position = new Pair<>(this.position.get1(), mapBoundUp);
                if (this.speed.get2() < 0) {
                    this.speed = new Pair<>(this.speed.get1(), 0.0);
                }
            }
        }
    }

}
