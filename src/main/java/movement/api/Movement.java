package movement.api;

import utilities.Pair;

public interface Movement {

    Pair<Double, Double> getStartingPosition();

    Pair<Double, Double> getCurrentPosition();
}
