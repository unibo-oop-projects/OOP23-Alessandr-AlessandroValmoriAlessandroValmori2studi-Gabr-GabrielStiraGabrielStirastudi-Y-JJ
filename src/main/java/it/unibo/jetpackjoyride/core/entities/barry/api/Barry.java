package it.unibo.jetpackjoyride.core.entities.barry.api;

import it.unibo.jetpackjoyride.utilities.Pair;

public interface Barry {

    public enum BarryStatus{
        WALKING, PROPELLING, FALLING, LAND, HEAD_DRAGGING, DEAD;
    }

    public Pair<Double, Double> getPosition();
    
    
    public boolean propel();

    public boolean fall();

    public BarryStatus getBarryStatus();


    public void move(boolean jumping);


}
