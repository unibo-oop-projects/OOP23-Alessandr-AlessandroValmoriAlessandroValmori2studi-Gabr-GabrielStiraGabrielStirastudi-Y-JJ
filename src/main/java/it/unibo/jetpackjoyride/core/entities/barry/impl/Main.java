package it.unibo.jetpackjoyride.core.entities.barry.impl;

import it.unibo.jetpackjoyride.core.entities.barry.api.Barry;
import it.unibo.jetpackjoyride.utilities.Pair;

public class Main{


    Barry barry =new BarryImpl(new Pair<>(0,0), new Pair<>(0,1), new Pair<>(0,2));

    public void avviaLoop(){
        barry.loop();

        
    }
    
}