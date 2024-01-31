package it.unibo.jetpackjoyride.core.entities.barry.impl;

import it.unibo.jetpackjoyride.core.entities.barry.api.Barry;
import it.unibo.jetpackjoyride.utilities.Pair;
import java.awt.event.KeyEvent;

public class BarryImpl implements Barry {

    

    private final double SPEED_MODIFIER=1.5;
    private final double FALL_MODIFIER=1.4;
    private final double X_POSITION= 50.0;
    private final double GROUND_LIMIT=30.0;     
    private final double CEILING_LIMIT= 600.0;
    private BarryStatus status;
    private double verticalSpeed;
    private double position;

    /*FUTURE */

    private int coins;
    private int distance;

    /*------- */
    


   

    public BarryImpl(){
        this.status= BarryStatus.WALKING;
        this.position=GROUND_LIMIT;
        this.verticalSpeed=0;
    }

    public boolean fall(){
        if(this.status.equals(BarryStatus.WALKING)){
            return false;
        }
        this.verticalSpeed = this.verticalSpeed- this.FALL_MODIFIER;
        this.position-= this.verticalSpeed;
        if(limitReached()){
            this.position= GROUND_LIMIT;
            this.status = BarryStatus.WALKING;
        }
        return true;
        
    }

    private boolean limitReached(){
        return this.position <= GROUND_LIMIT || this.position >= CEILING_LIMIT;
    }

    public boolean propel(){
        if(this.status.equals(BarryStatus.HEAD_DRAGGING)){
            return false;
        }
        this.verticalSpeed = this.verticalSpeed+ this.SPEED_MODIFIER;
        this.position+= this.verticalSpeed;
        if(limitReached()){
            this.position = CEILING_LIMIT;
            this.status = BarryStatus.HEAD_DRAGGING;
        }
        return true;
    }



	@Override
	public Pair<Double, Double> getPosition() {
		return new Pair<>(this.X_POSITION, this.position);
	}


	@Override
	public void controlPlayer() {
		
	}

}
