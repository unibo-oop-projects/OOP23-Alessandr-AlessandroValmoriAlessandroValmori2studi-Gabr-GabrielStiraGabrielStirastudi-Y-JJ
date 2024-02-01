package it.unibo.jetpackjoyride.core.entities.barry.api;

import it.unibo.jetpackjoyride.utilities.Pair;

public interface Barry {

    public enum BarryStatus{
        WALKING, FLYING, HEAD_DRAGGING, DEAD;
    }

    public Pair<Double, Double> getPosition();
    
    public void controlPlayer();

    public boolean propel();

    public boolean fall();

    public BarryStatus getBarryStatus();


}
