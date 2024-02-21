package it.unibo.jetpackjoyride.core.movement;

import it.unibo.jetpackjoyride.utilities.GameInfo;
import it.unibo.jetpackjoyride.utilities.MovementChangers;
import it.unibo.jetpackjoyride.utilities.Pair;
import java.util.*;

public final class Movement {
    private static final Double GRAVITYMODIFIER = 0.3;
    private static final Double INVERSEGRAVITYMODIFIER = -0.3;
    private static final Double MAPBOUNDUP = 50.0;
    private static final Double MAPBOUNDDOWN = 630.0;

    record MovCharacterizing(Pair<Double,Double> pos, Pair<Double,Double> speed, Pair<Double,Double> acc, Pair<Double,Double> rot) {}

    private final MovCharacterizing movementSpecifiers;
    private final List<MovementChangers> listOfChangers;

    private Pair<Double, Double> relativePosition;

    private Movement(final Pair<Double, Double> position, final Pair<Double, Double> speed, 
                     final Pair<Double, Double> acceleration, final Pair<Double, Double> rotation, 
                     final  List<MovementChangers> listOfChangers, final Pair<Double, Double> relativePosition) {
        this.movementSpecifiers = new MovCharacterizing(position, speed, acceleration, rotation);
        this.listOfChangers = listOfChangers;
        this.relativePosition = relativePosition;
    }

    public Pair<Double, Double> getRelativePosition() {
        return this.relativePosition;
    }

    public Pair<Double, Double> getRealPosition() {
        return this.movementSpecifiers.pos();
    }

    public Pair<Double, Double> getSpeed() {
        return this.movementSpecifiers.speed();
    }

    public Pair<Double, Double> getAcceleration() {
        return this.movementSpecifiers.acc();
    }

    public Pair<Double, Double> getRotation() {
        return this.movementSpecifiers.rot();
    }

    public List<MovementChangers> getMovementChangers() {
        return this.listOfChangers;
    }

    private void adaptToScreenSize(final Pair<Double,Double> modifiedPosition) {
        final GameInfo infoResolution = GameInfo.getInstance();
        final Double xScaling = infoResolution.getScreenWidth()/infoResolution.getDefaultWidth();
        final Double yScaling = infoResolution.getScreenHeight()/infoResolution.getDefaultHeight();

        this.relativePosition = new Pair<>(modifiedPosition.get1()*xScaling, modifiedPosition.get2()*yScaling);
    }

    private MovCharacterizing applyModifiers(Pair<Double,Double> toModifyPosition, Pair<Double,Double> toModifySpeed,
                                Pair<Double,Double> toModifyAcceleration, Pair<Double,Double> toModifyRotation) {
        Pair<Double,Double> modifiedPosition = toModifyPosition;
        Pair<Double,Double> modifiedSpeed = toModifySpeed;
        Pair<Double,Double> modifiedAcceleration = toModifyAcceleration;
        Pair<Double,Double> modifiedRotation = toModifyRotation;

        /* GRAVITY */
        if (this.listOfChangers.contains(MovementChangers.GRAVITY)) {
            modifiedPosition = new Pair<>(modifiedSpeed.get1(), modifiedSpeed.get2() + GRAVITYMODIFIER);
        }

        /* INVERSEGRAVITY */
        if (this.listOfChangers.contains(MovementChangers.INVERSEGRAVITY)) {
            modifiedSpeed = new Pair<>(modifiedSpeed.get1(), modifiedSpeed.get2() + INVERSEGRAVITYMODIFIER);
        }

        /* BOUNCING */
        if (this.listOfChangers.contains(MovementChangers.BOUNCING)) {
            if (this.movementSpecifiers.pos().get2() < MAPBOUNDUP) {
                modifiedSpeed = new Pair<>(modifiedSpeed.get1(), Math.abs(modifiedSpeed.get2()));
                modifiedRotation = new Pair<>(-this.movementSpecifiers.rot().get1(), this.movementSpecifiers.rot().get2());
            }
            if (this.movementSpecifiers.pos().get2() > MAPBOUNDDOWN) {
                modifiedSpeed = new Pair<>(modifiedSpeed.get1(), -Math.abs(modifiedSpeed.get2()));
                modifiedRotation = new Pair<>(-this.movementSpecifiers.rot().get1(), this.movementSpecifiers.rot().get2());
            }
        }

        /* BOUNDS */
        if (this.listOfChangers.contains(MovementChangers.BOUNDS)) {
            if (this.movementSpecifiers.pos().get2() > MAPBOUNDDOWN) {
                modifiedPosition = new Pair<>(modifiedPosition.get1(), MAPBOUNDDOWN);
                if (this.movementSpecifiers.speed().get2() > 0) {
                    modifiedSpeed = new Pair<>(this.movementSpecifiers.speed().get1(), 0.0);
                }
            }
            if (this.movementSpecifiers.pos().get2() < MAPBOUNDUP) {
                modifiedPosition = new Pair<>(modifiedPosition.get1(), MAPBOUNDUP);
                if (this.movementSpecifiers.speed().get2() < 0) {
                    modifiedSpeed = new Pair<>(this.movementSpecifiers.speed().get1(), 0.0);
                }
            }
        }
        return new MovCharacterizing(modifiedPosition, modifiedSpeed, modifiedAcceleration, modifiedRotation);
    }

    public Movement update() {
        final MovCharacterizing modifiedSpecifiers = this.applyModifiers(this.movementSpecifiers.pos(), this.movementSpecifiers.speed(), this.movementSpecifiers.acc(), this.movementSpecifiers.rot());
        Pair<Double,Double> modifiedPosition = modifiedSpecifiers.pos();
        Pair<Double,Double> modifiedSpeed = modifiedSpecifiers.speed();
        final Pair<Double,Double> modifiedAcceleration = modifiedSpecifiers.acc();
        Pair<Double,Double> modifiedRotation = modifiedSpecifiers.rot();

        modifiedSpeed = new Pair<>(modifiedSpeed.get1() + modifiedAcceleration.get1(), modifiedSpeed.get2() + modifiedAcceleration.get2());

        modifiedPosition = new Pair<>(modifiedPosition.get1() + modifiedSpeed.get1(), modifiedPosition.get2() + modifiedSpeed.get2());

        modifiedRotation = new Pair<>(modifiedRotation.get1() + modifiedRotation.get2(), modifiedRotation.get2());

        this.adaptToScreenSize(modifiedPosition);
        
        return new Builder().setPosition(modifiedPosition)
                            .setSpeed(modifiedSpeed)
                            .setAcceleration(modifiedAcceleration)
                            .setRotation(modifiedRotation)
                            .setMovementChangers(listOfChangers)
                            .setRelativePosition(this.relativePosition)
                            .build();
    }


    public static class Builder {

        private static final Pair<Double, Double> DEFAULT = new Pair<>(0.0,0.0);

        private Pair<Double, Double> position = DEFAULT;
        private Pair<Double, Double> speed = DEFAULT;
        private Pair<Double, Double> acceleration = DEFAULT;
        private Pair<Double, Double> rotation = DEFAULT;
        private List<MovementChangers> listOfChangers = List.of();

        private Pair<Double, Double> relativePosition = DEFAULT;
        private boolean consumed;

        public Builder setPosition(final Double x, final Double y) {
            this.position = new Pair<>(x,y);
            return this;
        }

        public Builder setSpeed(final Double x, final Double y) {
            this.speed = new Pair<>(x,y);
            return this;
        }

        public Builder setAcceleration(final Double x, final Double y) {
            this.acceleration = new Pair<>(x,y);
            return this;
        }

        public Builder setRotation(final Double r1, final Double r2) {
            this.rotation = new Pair<>(r1,r2);
            return this;
        }

        public Builder setPosition(final Pair<Double,Double> newPosition) {
            this.position = newPosition;
            return this;
        }

        public Builder setSpeed(final Pair<Double,Double> newSpeed) {
            this.speed = newSpeed;
            return this;
        }

        public Builder setAcceleration(final Pair<Double,Double> newAcceleration) {
            this.acceleration = newAcceleration;
            return this;
        }

        public Builder setRotation(final Pair<Double,Double> newRotation) {
            this.rotation = newRotation;
            return this;
        }

        public Builder setMovementChangers(final List<MovementChangers> newChangers) {
            this.listOfChangers = newChangers;
            return this;
        }

        public Builder setRelativePosition(final Pair<Double,Double> relativePosition) {
            this.relativePosition = relativePosition;
            return this;
        }

        public final Movement build() {
            if (consumed) {
                throw new IllegalStateException("The builder can only be used once");
            }
            consumed = true;
            return new Movement(position, speed, acceleration, rotation, listOfChangers, relativePosition);
        }
    }
}