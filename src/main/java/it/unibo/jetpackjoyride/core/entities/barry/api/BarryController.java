package it.unibo.jetpackjoyride.core.entities.barry.api;

import it.unibo.jetpackjoyride.utilities.Pair;

public interface BarryController {
    
  public void controlPlayer(boolean pressed);

  public Pair<Double, Double> getPos();
}
