package it.unibo.jetpackjoyride.core.movement;

import it.unibo.jetpackjoyride.utilities.MovementChangers;
import it.unibo.jetpackjoyride.utilities.Pair;
import java.util.*;

/**
 * The {@link Movement} class is one of the two elements which characterize every entity along with 
 * the hitbox. 
 * This class encapsulates elements such as position, speed, acceleration and rotation which allow 
 * entity to be represented in a two dimensional space. A record is used as a simple data-carrying 
 * class to store the most important values which characterize the movement.
 * A list of modifiers is also used to define common behaviours for the classes which use Movement.
 * This class also provides a method to get a relative position which is the position of the entity 
 * scaled based on the current screen size.
 * This is an immutable class, hence the creation of a new Movement class is required every time the
 * movement has to be updated. While it could seem more resource consuming compared to a mutable
 * implementation, there are several reason a mutable one can be preferred.
 * 
 * @author gabriel.stira@studio.unibo.it
 */

public final class Movement {
    /**
     * Defines how much the speed will be increased downwards.
     */
    private static final Double GRAVITYMODIFIER = 0.3;
    /**
     * Defines how much the speed will be increased upwards.
     */
    private static final Double INVERSEGRAVITYMODIFIER = -0.3;

    /**
     * Defines what coordinate could be considered as the upper bound of the map.
     */

    private static final Double MAPBOUNDUP = 80.0;
    /**
     * Defines what coordinate could be considerated as the lower bound of the map.
     */
    private static final Double MAPBOUNDDOWN = 630.0;

    /*
     * A record is used to store the four most important values of the movement class. Since Movement is immutable, 
     * a record is a good idea to avoid having to implement getters, is more readable and guarantees immutability.
     */
    record MovCharacterizing(Pair<Double,Double> pos, Pair<Double,Double> speed, Pair<Double,Double> acc, Pair<Double,Double> rot) {}

    /**
     * Defines the record which will be used to store the movement characteristics.
     */
    private final MovCharacterizing movementSpecifiers;

    /**
     * Defines all the modifiers which affect the movement behavior.
     */
    private final List<MovementChangers> listOfChangers;

     /**
     * Constructor used to create the instance of the class Movement.
     *
     * @param position          The position of the entity (x and y coordinates).
     * @param speed             The speed of the entity (x and y coordinates).
     * @param acceleration      The acceleration of the entity (x and y coordinates).
     * @param rotation          The rotation values of the entity (x and y coordinates).
     * (v1 is the angle by which the entity is rotated, v2 is how much it rotates with every call of the update method)
     * @param listOfChangers    The modifiers affecting the entity.
     * @param relativePosition  The relative position of the entity.
     */
    private Movement(final Pair<Double, Double> position, final Pair<Double, Double> speed, 
                     final Pair<Double, Double> acceleration, final Pair<Double, Double> rotation, 
                     final  List<MovementChangers> listOfChangers) {
        this.movementSpecifiers = new MovCharacterizing(position, speed, acceleration, rotation);
        this.listOfChangers = listOfChangers;
    }

    /**
     * Gets the real position of the entity.
     *
     * @return The real position of the entity.
     */
    public Pair<Double, Double> getPosition() {
        return this.movementSpecifiers.pos();
    }

    /**
     * Gets the speed of the entity.
     *
     * @return The speedof the entity.
     */
    public Pair<Double, Double> getSpeed() {
        return this.movementSpecifiers.speed();
    }

    /**
     * Gets the accelerationof the entity.
     *
     * @return The acceleration of the entity.
     */
    public Pair<Double, Double> getAcceleration() {
        return this.movementSpecifiers.acc();
    }

    /**
     * Gets the rotation values of the entity.
     *
     * @return The rotation values of the entity.
     */
    public Pair<Double, Double> getRotation() {
        return this.movementSpecifiers.rot();
    }

    /**
     * Gets all the modifiers of the movement of the entity.
     *
     * @return The movement modifiers of the entity.
     */
    public List<MovementChangers> getMovementChangers() {
        return Collections.unmodifiableList(this.listOfChangers);
    }

    /**
     * Applies the modifiers to the movement depending on what the listOfChangers contains.
     * Since the possible modifiers are few and a correct order has to be followed when 
     * applying them, to avoid complications, some if statements are used.
     * 
     * @param toModifyPosition    The position modified by the changers.
     * @param toModifySpeed       The speed modified by the changers.
     * @param toModifyAcceleration  The acceleration modified by the changers.
     * @param toModifyRotation    The rotation modified by the changers.
     * @return The modified movement characteristics (as a record).
     */
    private MovCharacterizing applyModifiers(Pair<Double, Double> toModifyPosition,
                                         Pair<Double, Double> toModifySpeed,
                                         Pair<Double, Double> toModifyAcceleration,
                                         Pair<Double, Double> toModifyRotation) {
        Pair<Double, Double> modifiedPosition = new Pair<>(toModifyPosition.get1(), toModifyPosition.get2());
        Pair<Double, Double> modifiedSpeed = new Pair<>(toModifySpeed.get1(), toModifySpeed.get2());
        Pair<Double, Double> modifiedAcceleration = new Pair<>(toModifyAcceleration.get1(), toModifyAcceleration.get2());
        Pair<Double, Double> modifiedRotation = new Pair<>(toModifyRotation.get1(), toModifyRotation.get2());

    // Applying modifiers
    for (MovementChangers changer : listOfChangers) {
        switch (changer) {
             /* GRAVITY */ /* The entity will be constantly accelerated downwards*/
            case GRAVITY:
                modifiedSpeed = new Pair<>(modifiedSpeed.get1(), modifiedSpeed.get2() + GRAVITYMODIFIER);
                break;
                 /* INVERSEGRAVITY */ /* The entity will be constantly accelerated upwards*/
            case INVERSEGRAVITY:
                modifiedSpeed = new Pair<>(modifiedSpeed.get1(), modifiedSpeed.get2() + INVERSEGRAVITYMODIFIER);
                break;
            case BOUNCING: /* BOUNCING */ /* The entity has its speed and rotation inverted when touching one of the edges of the map*/
                if (this.movementSpecifiers.pos().get2() < MAPBOUNDUP) {
                    modifiedSpeed = new Pair<>(modifiedSpeed.get1(), Math.abs(modifiedSpeed.get2()));
                    modifiedRotation = new Pair<>(-this.movementSpecifiers.rot().get1(), this.movementSpecifiers.rot().get2());
                } else if (this.movementSpecifiers.pos().get2() > MAPBOUNDDOWN) {
                    modifiedSpeed = new Pair<>(modifiedSpeed.get1(), -Math.abs(modifiedSpeed.get2()));
                    modifiedRotation = new Pair<>(-this.movementSpecifiers.rot().get1(), this.movementSpecifiers.rot().get2());
                }
                break;
            case BOUNDS: /* BOUNDS */ /* The entity can't go further that the limits of the map*/
                if (this.movementSpecifiers.pos().get2() > MAPBOUNDDOWN) {
                    modifiedPosition = new Pair<>(modifiedPosition.get1(), MAPBOUNDDOWN);
                    modifiedSpeed = new Pair<>(this.movementSpecifiers.speed().get1(), Math.min(0.0, this.movementSpecifiers.speed().get2()));
                } else if (this.movementSpecifiers.pos().get2() < MAPBOUNDUP) {
                    modifiedPosition = new Pair<>(modifiedPosition.get1(), MAPBOUNDUP);
                    modifiedSpeed = new Pair<>(this.movementSpecifiers.speed().get1(), Math.max(0.0, this.movementSpecifiers.speed().get2()));
                }
                break;
        }
    }
    return new MovCharacterizing(modifiedPosition, modifiedSpeed, modifiedAcceleration, modifiedRotation);
}

    /**
     * Updates the movement of the object by applying simple uniform or accelerated rectilinear motion formulas.
     * Computes the new values of position, speed, acceleration and rotation and uses them as parameters for
     * the methods of a builder. The method to calculate the relative position is also called here.
     *
     * @return The updated Movement as a new immutable class but with the new values computed.
    */
    public Movement update() {
        final MovCharacterizing modifiedSpecifiers = this.applyModifiers(this.movementSpecifiers.pos(), this.movementSpecifiers.speed(), this.movementSpecifiers.acc(), this.movementSpecifiers.rot());
        Pair<Double,Double> modifiedPosition = modifiedSpecifiers.pos();
        Pair<Double,Double> modifiedSpeed = modifiedSpecifiers.speed();
        final Pair<Double,Double> modifiedAcceleration = modifiedSpecifiers.acc();
        Pair<Double,Double> modifiedRotation = modifiedSpecifiers.rot();

        modifiedSpeed = new Pair<>(modifiedSpeed.get1() + modifiedAcceleration.get1(), modifiedSpeed.get2() + modifiedAcceleration.get2());

        modifiedPosition = new Pair<>(modifiedPosition.get1() + modifiedSpeed.get1(), modifiedPosition.get2() + modifiedSpeed.get2());

        modifiedRotation = new Pair<>(modifiedRotation.get1() + modifiedRotation.get2(), modifiedRotation.get2());
        
        return new Builder().setPosition(modifiedPosition)
                            .setSpeed(modifiedSpeed)
                            .setAcceleration(modifiedAcceleration)
                            .setRotation(modifiedRotation)
                            .setMovementChangers(listOfChangers)
                            .build();
    }

   /**
     * Pattern builder: used here mainly because:
     * 
     * - all the parameters of the Movement class have a default value, which
     * means that we would like to have all the possible combinations of
     * constructors (one with three parameters, three with two parameters, three
     * with a single parameter), which are way too many and confusing to use
     * 
     * - the Movement class has more parameters of the same type, and it is
     * unclear to understand, in a call to its contructor, which is which. By using
     * the builder, we emulate the so-called "named arguments". 
     * 
     * Since the Movement class is used by all entities and its values (position, speed, etc...)
     * may need to be changed also from outside, this solution is better than accessing directly 
     * the values or using lots of getters and setters, especially if only one getter for the Movement
     * class is provided and the only way to change the values is by doing a get().set(newValue), which 
     * "may expose internal representation by storing an externally mutable...". 
     * The movement class was actually mutable before, but all these problems led to think that 
     * an immutable, side-effects free implementation was better, even if more resource-consuming.
     * (since a new class has to be created every time an entity has to update its movement, which actually
     * happens a lot, but the number of entities which do this at all times is generally really low).
     * 
     */
    public static class Builder {

        private static final Pair<Double, Double> DEFAULT = new Pair<>(0.0,0.0);

        private Pair<Double, Double> position = DEFAULT;
        private Pair<Double, Double> speed = DEFAULT;
        private Pair<Double, Double> acceleration = DEFAULT;
        private Pair<Double, Double> rotation = DEFAULT;
        private List<MovementChangers> listOfChangers = List.of();
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
            this.listOfChangers = Collections.unmodifiableList(newChangers);
            return this;
        }

        public final Movement build() {
            if (consumed) {
                throw new IllegalStateException("The builder can only be used once");
            }
            consumed = true;
            return new Movement(position, speed, acceleration, rotation, listOfChangers);
        }
    }
}