package core.movement.impl;

import core.movement.AbstractMovement;
import core.utilities.Pair;
import java.util.*;

public class MissileMovement extends AbstractMovement {

    private final static Double SPEEDYMODIFIER = 5.5;
    private final static Double SLOWMODIFIER = 0.3;

    private Pair<Double,Double> playerPosition = new Pair<>(0.0, 10.0);
    private List<MissileMovementType> listOfModifiers = new ArrayList<>();

    public enum MissileMovementType{
        DEFAULT, SLOW, SPEEDY, BOUNCING, HOMING
    }

    public MissileMovement(Pair<Double, Double> startingPosition, Pair<Double, Double> startingSpeed, Pair<Double, Double> startingAcceleration, List<MissileMovementType> movementInfo) {
        super(startingPosition, startingSpeed, startingAcceleration);
        listOfModifiers = movementInfo;
        changeMovementType(listOfModifiers);
    }

    public void changeMovementType(List<MissileMovementType> listOfModifiers) {
        try {
            if((listOfModifiers.contains(MissileMovementType.SLOW) && listOfModifiers.contains(MissileMovementType.SPEEDY))
            ||  (listOfModifiers.contains(MissileMovementType.HOMING) && listOfModifiers.contains(MissileMovementType.BOUNCING))) {
                throw new IllegalArgumentException("Some movement types are conflicting with each other!");
            }
        }
        catch(Exception e) {
            System.out.println("Missile movement will be set to DEFAULT");
            listOfModifiers=List.of(MissileMovementType.DEFAULT);
        } 

        for (var type : listOfModifiers) {
            switch (type) {
                case DEFAULT:
                    break;
                case SLOW:
                    this.xyspeed = new Pair<>(SLOWMODIFIER*(this.xyspeed.get1()), SLOWMODIFIER*(this.xyspeed.get2()));
                    break;
                case SPEEDY:
                    this.xyspeed = new Pair<>(SPEEDYMODIFIER*(this.xyspeed.get1()), SPEEDYMODIFIER*(this.xyspeed.get2()));  
                default:
                    break;
            }
        }
    }

    private void applyModifiers() {
        /* HOMING */
        if(listOfModifiers.contains(MissileMovementType.HOMING)) {
            //playerPosition = getPlayerPosition();
            this.xyacceleration = new Pair<>(this.xyacceleration.get1(), (playerPosition.get2() - this.currentPosition.get2()));
        }

        /* BOUNCING */
        if(listOfModifiers.contains(MissileMovementType.BOUNCING)) {
            if(this.currentPosition.get2()<=0 || this.currentPosition.get2()>=100) {
                this.xyspeed = new Pair<>(this.xyspeed.get1(), -this.xyspeed.get2());
            }
        }
    }

    public void update(){
        this.applyModifiers();
        /* V = U + AT */
        this.xyspeed = new Pair<>(this.xyspeed.get1() + this.xyacceleration.get1() * time , this.xyspeed.get2() + this.xyacceleration.get2() * time);
        /* S = V * T */
        this.currentPosition = new Pair<>(this.currentPosition.get1() + this.xyspeed.get1() * time, this.currentPosition.get2() + this.xyspeed.get2() * time);
    }
}
