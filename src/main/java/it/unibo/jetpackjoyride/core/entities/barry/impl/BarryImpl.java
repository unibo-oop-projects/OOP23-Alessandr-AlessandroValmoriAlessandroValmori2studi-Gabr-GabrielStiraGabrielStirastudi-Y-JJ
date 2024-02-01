package it.unibo.jetpackjoyride.core.entities.barry.impl;

import it.unibo.jetpackjoyride.core.entities.barry.api.Barry;
import it.unibo.jetpackjoyride.utilities.Pair;
import java.awt.event.KeyEvent;

public class BarryImpl implements Barry {

    

    private final double PROPEL_MODIFIER=1.0;
    private final double FALL_MODIFIER=0.3;
    private final double FALL_ACCELERATION=0.3;
    private final double PROPEL_ACCELERATION=0.6;
    private final double X_POSITION= 50.0;
    private final double GROUND_LIMIT=700.0;     
    private final double CEILING_LIMIT= 30.0;
    private BarryStatus status;
    private double currentFallMod=FALL_MODIFIER;
    private double currentPropelMod=PROPEL_MODIFIER;
    private double position;

    private double propelSpeed=2.0;
    private double fallSpeed=1.2;


    /*FUTURE */

    private int coins;
    private int distance;

    /*------- */
    


   

    public BarryImpl(){
        this.status= BarryStatus.WALKING;
        this.position=GROUND_LIMIT;
        
    }
    
    /*RETURN TRUE IF I AM FALLING, FALSE IF NOT */
    public boolean fall(){
        
       
        
        this.currentPropelMod= PROPEL_MODIFIER;
        

        if(this.position+ this.fallSpeed * this.currentFallMod < GROUND_LIMIT){
        this.position+= this.fallSpeed * this.currentFallMod;
        return true;
        }

        

        this.position= GROUND_LIMIT;
        this.status=BarryStatus.WALKING;
        return false;
        
    }

    
    /*RETURNS TRUE IF I AM GOING UP, FALSE IF NOT   */
    public boolean propel(){

        
        
        this.currentFallMod= FALL_MODIFIER;

        if(this.position- this.propelSpeed * this.currentPropelMod > CEILING_LIMIT){
        this.position-= this.propelSpeed * this.currentPropelMod;
        return true;
        }

        

        this.position= CEILING_LIMIT;
        this.status=BarryStatus.HEAD_DRAGGING;
        return false;
    }

    public void move(boolean jumping){


        

        if(jumping){
            System.out.println(this.propel());
            this.currentPropelMod+= this.PROPEL_ACCELERATION;
        }else if(!this.status.equals(BarryStatus.WALKING)){
            this.status = this.fall() ? BarryStatus.FALLING : BarryStatus.LAND;
            this.currentFallMod+= this.FALL_ACCELERATION;
        }

       
    }

    @Override
    public BarryStatus getBarryStatus(){
        return this.status;
    }

	@Override
	public Pair<Double, Double> getPosition() {
		return new Pair<>(this.X_POSITION, this.position);
	}



}
