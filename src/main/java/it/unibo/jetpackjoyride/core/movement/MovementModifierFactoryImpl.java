package it.unibo.jetpackjoyride.core.movement;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import it.unibo.jetpackjoyride.core.movement.Movement.MovCharacterizing;
import it.unibo.jetpackjoyride.utilities.Pair;

/**
 * Implementation of the {@link MovementModifierFactory}.
 * 
 * @author alessandro.valmori2@studio.unibo.it
 * @author gabriel.stira@studio.unibo.it
 */
public final class MovementModifierFactoryImpl implements MovementModifierFactory {
    /**
     * This is the key function of the strategy pattern applied here;
     * it creates a generic movement modifier based on the provided predicates and
     * applies the given transformation function to a {@link MovCharacterizing}
     * object if all predicates are satisfied; otherwise, it leaves the object
     * unchanged.
     *
     * @param predicate The list of predicates to check against the
     *                  {@link MovCharacterizing}.
     * @param toApply   The function to apply if all predicates are satisfied.
     * @return A {@link MovementModifier} based on the provided predicates and
     *         transformation function.
     */
    private MovementModifier generic(final List<Predicate<MovCharacterizing>> predicate,
            final Function<MovCharacterizing, MovCharacterizing> toApply) {
        return new MovementModifier() {

            @Override
            public MovCharacterizing applyModifier(final MovCharacterizing toModify) {
                return predicate.stream().allMatch(p -> p.test(toModify)) ? toApply.apply(toModify) : toModify;
            }
        };
    }

    @Override
    public MovementModifier gravity(final Double value) {
        return generic(List.of(m -> true), m -> new MovCharacterizing(m.pos(),
                new Pair<>(m.speed().get1(), m.speed().get2() + value), m.acc(), m.rot()));
    }

    @Override
    public MovementModifier bouncing(final Double mapBoundDown, final Double mapBoundUp) {
        return generic(
                List.of(m -> m.pos().get2() < mapBoundUp || m.pos().get2() > mapBoundDown),
                m -> new MovCharacterizing(
                        m.pos(),
                        new Pair<>(m.speed().get1(),
                                (m.pos().get2() < mapBoundUp ? 1.0 : -1.0) * Math.abs(m.speed().get2())),
                        m.acc(),
                        new Pair<>(-m.rot().get1(), m.rot().get2())));
    }

    @Override
    public MovementModifier bounds(final Double mapBoundDown, final Double mapBoundUp) {
        return generic(
                List.of(m -> m.pos().get2() < mapBoundUp || m.pos().get2() > mapBoundDown),
                m -> new MovCharacterizing(
                        new Pair<>(m.pos().get1(), m.pos().get2() < mapBoundUp ? mapBoundUp : mapBoundDown),
                        new Pair<>(m.speed().get1(), m.pos().get2() < mapBoundUp
                                ? Math.max(0.0, m.speed().get2())
                                : Math.min(0.0, m.speed().get2())),
                        m.acc(),
                        m.rot()));
    }

    /**
     * Utility method used to combine two {@link MovementModifier}.
     * 
     * @param m1 The first {@link MovementModifier} to combine.
     * @param m2 The second {@link MovementModifier} to combine.
     * @return A new {@link MovementModifier} that applies both modifications
     *         sequentially.
     */
    private MovementModifier combineTwo(final MovementModifier m1, final MovementModifier m2) {
        return new MovementModifier() {
            @Override
            public MovCharacterizing applyModifier(final MovCharacterizing toModify) {
                return m2.applyModifier(m1.applyModifier(toModify));
            }
        };
    }

    @Override
    public MovementModifier combineList(final List<MovementModifier> list) {
        // Return identity modifier if the list is empty
        if (list.isEmpty()) {
            return identityModifier();
        }

        // Reduce the list of modifiers using the combine function
        return list.stream()
                .reduce(this::combineTwo)
                .orElse(identityModifier());
    }

    /**
     * Creates a {@link MovementModifier} that serves as an identity modifier.
     * An identity modifier returns the {@link MovementModifier} without any
     * modification.
     *
     * @return A {@link MovementModifier} that serves as an identity modifier.
     */
    private MovementModifier identityModifier() {
        return new MovementModifier() {
            @Override
            public MovCharacterizing applyModifier(final MovCharacterizing toModify) {
                return toModify;
            }
        };
    }
}
