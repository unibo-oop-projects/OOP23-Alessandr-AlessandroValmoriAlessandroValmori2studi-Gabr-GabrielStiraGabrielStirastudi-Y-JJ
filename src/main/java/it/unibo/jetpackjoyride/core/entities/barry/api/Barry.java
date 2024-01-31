package it.unibo.jetpackjoyride.core.entities.barry.api;

import it.unibo.jetpackjoyride.utilities.Pair;

public interface Barry {

    public enum BarryStatus{
        WALKING, PROPELLING, FALLING, HEAD_DRAGGING;
    }

    public Pair<Double, Double> getPosition();
    
    public void controlPlayer();

    public boolean propel();

    public boolean fall();


}
