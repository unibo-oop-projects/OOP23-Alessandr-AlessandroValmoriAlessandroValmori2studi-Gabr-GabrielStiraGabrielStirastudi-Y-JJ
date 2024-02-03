package it.unibo.jetpackjoyride.core.entities.barry.impl;

import it.unibo.jetpackjoyride.core.entities.barry.api.Barry;
import it.unibo.jetpackjoyride.core.hitbox.impl.PlayerHitbox;
import it.unibo.jetpackjoyride.utilities.GameInfo;
import it.unibo.jetpackjoyride.utilities.Pair;

public class BarryImpl implements Barry {

    

    private final double DOWNWARD_ACC=0.6; // gravity
    private final double UPWARDS_ACC =1.2; // jetpack propulsion

    private double speed; //can be negative or positive, negative goes up, positive down
    //its standard value is 0, when barry is walking

    private final double X_POSITION= 100.0; // fixed x position 
    private double position; // variable y position

    private final double GROUND_LIMIT;    
    private final double CEILING_LIMIT= 30.0;

    private BarryStatus status; // walking, falling ...

    
   private PlayerHitbox hitbox;
    

 

    private GameInfo gameInfo;


    /*FUTURE */

    private int coins;
    private int distance;

    /*------- */
    
    public BarryImpl(){
        this.status= BarryStatus.WALKING;
        gameInfo = new GameInfo();
        
        this.GROUND_LIMIT  =gameInfo.getScreenHeight()/1.2; 
        this.position=GROUND_LIMIT;
        this.speed=0;
        this.hitbox= new PlayerHitbox(this.getPosition(), 0.0);
        this.hitbox.setHitboxOn();
        
    }
    
    /*RETURN TRUE IF I AM FALLING, FALSE IF NOT */
    public boolean fall(){
      
      

        if(this.position + this.speed < GROUND_LIMIT){
            if(this.position + this.speed < CEILING_LIMIT){
                this.speed=0;
                this.position=CEILING_LIMIT;

            }
            this.speed+= this.DOWNWARD_ACC;
            this.position+= this.speed;
            this.status= BarryStatus.FALLING;
            return true;
            }
    
            this.position= GROUND_LIMIT;
            this.status=BarryStatus.WALKING;
            this.speed=0;
            return false;

            
        
    }

    /*RETURNS TRUE IF I AM GOING UP, FALSE IF NOT   */
    public boolean propel(){
 
        

        if(this.position + this.speed > CEILING_LIMIT){

            if(this.position + this.speed > GROUND_LIMIT){
                this.speed=0;
                this.position=GROUND_LIMIT;

            }
        this.speed-= this.UPWARDS_ACC;
        this.position+= this.speed;
        this.status= BarryStatus.PROPELLING;
        return true;
        }

        this.position= CEILING_LIMIT;
        this.speed=0;
        this.status=BarryStatus.HEAD_DRAGGING;
        return false;
    }

    public void move(boolean jumping){

        if(jumping){
            this.propel();
            
        }else if(!this.status.equals(BarryStatus.WALKING)){
           this.fall();
           
        }

        this.hitbox.updateHitbox(getPosition(), 0.0);
    }

    @Override
    public BarryStatus getBarryStatus(){
        return this.status;
    }

	@Override
	public Pair<Double, Double> getPosition() {
		return new Pair<>(this.X_POSITION, this.position);
	}

    @Override
    public PlayerHitbox getHitbox(){
        return this.hitbox;
    }



}
