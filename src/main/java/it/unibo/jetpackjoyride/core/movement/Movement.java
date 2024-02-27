package it.unibo.jetpackjoyride.core.movement;

import it.unibo.jetpackjoyride.core.hitbox.api.Hitbox;
import it.unibo.jetpackjoyride.utilities.MovementChangers;
import it.unibo.jetpackjoyride.utilities.Pair;
import it.unibo.jetpackjoyride.utilities.exceptions.NotImplementedObjectException;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

/**
 * The {@link Movement} is one of the two elements which characterize every
 * entity along with the {@link Hitbox}.
 * This class encapsulates elements such as position, speed, acceleration and
 * rotation which allow entities to be represented in a two dimensional space.
 * A record is used as a simple data-carrying class to store the most important
 * values which characterize the movement ({@link MovCharacterizing});
 * A list of modifiers is also used to define common behaviours for the classes
 * which use {@link Movement}. The list of modifiers is associated to a list of
 * {@link MovementModifier}, a class used to create and add every kind of personalized 
 * modifier to a movement.
 * This class also provides a method to get a relative position which is the
 * position of the entity scaled based on the current screen size.
 * This is an immutable class, hence the creation of a new {@link Movement} class is
 * required every time the movement has to be updated. 
 * While it could seem more resource consuming compared to a mutable
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

    /**
     * A record is used to store the four most important values of the movement
     * class. Since Movement is immutable,
     * a record is a good idea to avoid having to implement getters, is more
     * readable and guarantees immutability.
     * 
     * @param pos   The position of the entity, represented as a pair of (x, y)
     *              coordinates.
     * @param speed The speed of the entity, represented as a pair of (x-speed,
     *              y-speed).
     * @param acc   The acceleration of the entity, represented as a pair of
     *              (x-acceleration, y-acceleration).
     * @param rot   The rotation of the entity.
     */
    record MovCharacterizing(Pair<Double, Double> pos, Pair<Double, Double> speed, Pair<Double, Double> acc,
            Pair<Double, Double> rot) {
    }

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
     * @param position       The position of the entity (x and y coordinates).
     * @param speed          The speed of the entity (x and y coordinates).
     * @param acceleration   The acceleration of the entity (x and y coordinates).
     * @param rotation       The rotation values of the entity (x and y
     *                       coordinates).
     *                       (v1 is the angle by which the entity is rotated, v2
     *                       is how much it rotates with every call of the update
     *                       method)
     * @param listOfChangers The modifiers affecting the entity.
     */
    private Movement(final Pair<Double, Double> position, final Pair<Double, Double> speed,
            final Pair<Double, Double> acceleration, final Pair<Double, Double> rotation,
            final List<MovementChangers> listOfChangers) {
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
     * Applies the modifiers to the movement depending on what the listOfChangers
     * contains.
     * Since the possible modifiers are few and a correct order has to be followed
     * when
     * applying them, to avoid complications, some if statements are used.
     * 
     * @return The modified movement characteristics (as a record).
     */
    private MovCharacterizing applyModifiers() {

        final MovementModifierFactory modifiersFactory = new MovementModifierFactoryImpl();
        final List<MovementModifier> listOfModifiers = new ArrayList<>();

        // Applying modifiers
        for (final MovementChangers changer : listOfChangers) {
            MovementModifier newModifier;
            try {
                switch (changer) {
                    /** The entity will be constantly accelerated downwards */
                    case GRAVITY:
                        newModifier = modifiersFactory.gravity(GRAVITYMODIFIER);
                        break;
                    /** The entity will be constantly accelerated upwards */
                    case INVERSEGRAVITY:
                        newModifier = modifiersFactory.gravity(INVERSEGRAVITYMODIFIER);
                        break;
                    case BOUNCING:
                        /**
                         * The entity has its speed and rotation inverted when touching one of the edges
                         * of the map
                         */
                        newModifier = modifiersFactory.bouncing(MAPBOUNDDOWN, MAPBOUNDUP);
                        break;
                    case BOUNDS:
                        /** The entity can't go further that the limits of the map */
                        newModifier = modifiersFactory.bounds(MAPBOUNDDOWN, MAPBOUNDUP);
                        break;
                    default:
                        throw new NotImplementedObjectException("The provided modifier does not exist: " + changer);
                }
                listOfModifiers.add(newModifier);
            } catch (NotImplementedObjectException e) {
                listOfModifiers.add(modifiersFactory.gravity(GRAVITYMODIFIER));
            }
        }
        return modifiersFactory.combineList(listOfModifiers).applyModifier(movementSpecifiers);
    }

    /**
     * Updates the movement of the object by applying simple uniform or accelerated
     * rectilinear motion formulas.
     * Computes the new values of position, speed, acceleration and rotation and
     * uses them as parameters for
     * the methods of a builder. The method to calculate the relative position is
     * also called here.
     *
     * @return The updated Movement as a new immutable class but with the new values
     *         computed.
     */
    public Movement update() {
        final MovCharacterizing modifiedSpecifiers = this.applyModifiers();
        Pair<Double, Double> modifiedPosition = modifiedSpecifiers.pos();
        Pair<Double, Double> modifiedSpeed = modifiedSpecifiers.speed();
        /* Acceleration declared final only because currently no modifiers change it, but
         * they could!!! */
        final Pair<Double, Double> modifiedAcceleration = modifiedSpecifiers.acc();
        Pair<Double, Double> modifiedRotation = modifiedSpecifiers.rot();

        // V = Vo + a
        modifiedSpeed = new Pair<>(modifiedSpeed.get1() + modifiedAcceleration.get1(),
                modifiedSpeed.get2() + modifiedAcceleration.get2());
        // S = So + V
        modifiedPosition = new Pair<>(modifiedPosition.get1() + modifiedSpeed.get1(),
                modifiedPosition.get2() + modifiedSpeed.get2());
        //Every call to update add a rotation.get2() angle to rotation.get1()
        modifiedRotation = new Pair<>(modifiedRotation.get1() + modifiedRotation.get2(), modifiedRotation.get2());

        return new Builder().addNewPosition(modifiedPosition)
                .addNewSpeed(modifiedSpeed)
                .addNewAcceleration(modifiedAcceleration)
                .addNewRotation(modifiedRotation)
                .addNewMovementChangers(listOfChangers)
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
     * Since the Movement class is used by all entities and its values (position,
     * speed, etc...) may need to be changed also from outside, this solution is 
     * better than accessing directly the values or using lots of getters and 
     * setters, especially if only one getter for the Movement class is provided 
     * and the only way to change the values is by doing a get().set(newValue),
     * which "may expose internal representation by storing an externally mutable...".
     * The movement class was actually mutable before, but all these problems led to
     * think that an immutable, side-effects free implementation was better, even 
     * if more resource-consuming (since a new class has to be created every time
     * an entity updates its movement, which actually happens a lot, but the number
     * of entities which do this at all times is generally really low).
     */
    public static final class Builder {

        private static final Pair<Double, Double> DEFAULT = new Pair<>(0.0, 0.0);

        private Pair<Double, Double> position = DEFAULT;
        private Pair<Double, Double> speed = DEFAULT;
        private Pair<Double, Double> acceleration = DEFAULT;
        private Pair<Double, Double> rotation = DEFAULT;
        private List<MovementChangers> listOfChangers = List.of();
        private boolean consumed;

        /**
         * Sets the new position of the movement.
         * 
         * @param x The new X coordinate.
         * @param y The new Y coordinate.
         * @return A new instance of Builder with the modified position.
         */
        public Builder addNewPosition(final Double x, final Double y) {
            this.position = new Pair<>(x, y);
            return this;
        }

        /**
         * Sets the new speed of the movement.
         * 
         * @param x The new X speed.
         * @param y The new Y speed.
         * @return A new instance of Builder with the modified speed.
         */
        public Builder addNewSpeed(final Double x, final Double y) {
            this.speed = new Pair<>(x, y);
            return this;
        }

        /**
         * Sets the new acceleration of the movement.
         * 
         * @param x The new X acceleration.
         * @param y The new Y acceleration.
         * @return A new instance of Builder with the modified acceleration.
         */
        public Builder addNewAcceleration(final Double x, final Double y) {
            this.acceleration = new Pair<>(x, y);
            return this;
        }

        /**
         * Sets the new rotation of the movement.
         * 
         * @param r1 The new rotation angle.
         * @param r2 The angle the entity rotates at every update.
         * @return A new instance of Builder with the modified rotation.
         */
        public Builder addNewRotation(final Double r1, final Double r2) {
            this.rotation = new Pair<>(r1, r2);
            return this;
        }

        /**
         * Sets the new position of the movement.
         * 
         * @param newPosition The new position expressed as a pair of coordinates (x,
         *                    y).
         * @return A new instance of Builder with the modified position.
         */
        public Builder addNewPosition(final Pair<Double, Double> newPosition) {
            this.position = newPosition;
            return this;
        }

        /**
         * Sets the new speed of the movement.
         * 
         * @param newSpeed The new speed expressed as a pair of coordinates (x, y).
         * @return A new instance of Builder with the modified speed.
         */
        public Builder addNewSpeed(final Pair<Double, Double> newSpeed) {
            this.speed = newSpeed;
            return this;
        }

        /**
         * Sets the new acceleration of the movement.
         * 
         * @param newAcceleration The new acceleration expressed as a pair of
         *                        coordinates (x, y).
         * @return A new instance of Builder with the modified acceleration.
         */
        public Builder addNewAcceleration(final Pair<Double, Double> newAcceleration) {
            this.acceleration = newAcceleration;
            return this;
        }

        /**
         * Sets the new rotation of the movement.
         * 
         * @param newRotation The new rotation expressed as a pair of coordinates (x,
         *                    y).
         * @return A new instance of Builder with the modified rotation.
         */
        public Builder addNewRotation(final Pair<Double, Double> newRotation) {
            this.rotation = newRotation;
            return this;
        }

        /**
         * Sets the new list of changers of the movement.
         * 
         * @param newChangers The new list of changers.
         * @return A new instance of Builder with the modified list of changers.
         */
        public Builder addNewMovementChangers(final List<MovementChangers> newChangers) {
            this.listOfChangers = Collections.unmodifiableList(newChangers);
            return this;
        }

        /**
         * Build the new Movement class with the specified movement characteristics.
         * 
         * @return A new instance of Movement class.
         */
        public Movement build() {
            if (consumed) {
                throw new IllegalStateException("The builder can only be used once");
            }
            consumed = true;
            return new Movement(position, speed, acceleration, rotation, listOfChangers);
        }
    }
}
