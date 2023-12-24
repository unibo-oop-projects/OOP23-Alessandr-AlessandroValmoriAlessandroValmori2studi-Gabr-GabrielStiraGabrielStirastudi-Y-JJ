package core.movement.api;

import core.utilities.Pair;

public interface Movement {

    Pair<Double, Double> getStartingPosition();

    Pair<Double, Double> getCurrentPosition();
}
