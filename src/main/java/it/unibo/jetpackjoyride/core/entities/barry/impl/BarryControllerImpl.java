package it.unibo.jetpackjoyride.core.entities.barry.impl;

import it.unibo.jetpackjoyride.core.entities.barry.api.Barry;
import it.unibo.jetpackjoyride.core.entities.barry.api.BarryController;
import it.unibo.jetpackjoyride.utilities.Pair;


public class BarryControllerImpl implements BarryController{

    Barry barryModel= new BarryImpl();
    BarryView barryView;

    @Override
    public void controlPlayer(boolean pressed) {
    if(pressed){
        this.barryModel.propel();
    }
    else{
        this.barryModel.fall();
    }

    //System.out.println(this.barryModel.getPosition());
    }
    @Override
    public Pair<Double, Double> getPos(){
        return this.barryModel.getPosition();
    }
    

    
    
}
