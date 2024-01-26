package it.unibo.jetpackjoyride.core.movement;

import it.unibo.jetpackjoyride.utilities.Pair;
import java.util.*;

public class MovementGenerator {

    public Pair<Double, Double> startingPosition;
    public Pair<Double, Double> currentPosition;
    public Pair<Double,Double> xyacceleration;
    public Pair<Double,Double> xyspeed;
    public Double time = 0.1;

    private final static Double SPEEDYMODIFIER = 1.5;
    private final static Double SLOWMODIFIER = 0.3;

    public enum MovementChangers {
        DEFAULT, SLOW, SPEEDY, BOUNCING, HOMING
    }
    
    List<MovementType> accelerationChangers = new LinkedList<>();
    List<MovementType> speedChangers = new LinkedList<>();
    List<MovementType> positionChangers = new LinkedList<>();

    List<MovementType> movementInfo = new LinkedList<>();

    public MovementGenerator(Pair<Double, Double> startingPosition, Pair<Double, Double> startingSpeed, Pair<Double, Double> startingAcceleration, List<MovementChangers> listOfChangers) {
        this.startingPosition = startingPosition;
        this.currentPosition = startingPosition;
        this.xyspeed = startingSpeed;
        this.xyacceleration = startingAcceleration;
        createUpdate(listOfChangers);
    }

    public MovementGenerator(Movement oldMovement, List<MovementChangers> listOfChangers) {
        this(oldMovement.getCurrentPosition(), oldMovement.getSpeed(), oldMovement.getAcceleration(), listOfChangers);
    }

    private void createUpdate(List<MovementChangers> listOfChangers) {
        for(MovementChangers changer : listOfChangers) {
            switch (changer) {
                case HOMING:
                    accelerationChangers.add(new HomingMovement());
                case BOUNCING:
                    speedChangers.add(new BouncingMovement());
                case DEFAULT:
                    speedChangers.add(new DefaultSpeed());
                case SPEEDY:
                    positionChangers.add(new SpeedyMovement());
                case SLOW:
                    positionChangers.add(new SlowMovement());
                default:
                    break;
            }
        }
        movementInfo.addAll(accelerationChangers);
        movementInfo.addAll(speedChangers);
        movementInfo.addAll(positionChangers);
    }

    private interface MovementType {
        public abstract void updateAll();
    }

    private class HomingMovement implements MovementType{
        @Override
        public void updateAll() {
            xyacceleration = new Pair<>(xyacceleration.get1(), (/*playerPosition.get2()*/ - currentPosition.get2()));
        }
    }

    private class DefaultSpeed implements MovementType{
        @Override
        public void updateAll() {
            xyspeed = new Pair<>(xyspeed.get1() + xyacceleration.get1() * time , xyspeed.get2() + xyacceleration.get2() * time);
        }
    }

    private class SpeedyMovement implements MovementType{
        @Override
        public void updateAll() {
            currentPosition = new Pair<>(currentPosition.get1() + SPEEDYMODIFIER*xyspeed.get1() * time, currentPosition.get2() + SPEEDYMODIFIER*xyspeed.get2() * time);
        }
    }

    private class SlowMovement implements MovementType{
        @Override
        public void updateAll() {
            currentPosition = new Pair<>(currentPosition.get1() + SLOWMODIFIER*xyspeed.get1() * time, currentPosition.get2() + SLOWMODIFIER*xyspeed.get2() * time);
        }
    }

    private class BouncingMovement implements MovementType{
        @Override
        public void updateAll() {
            if(currentPosition.get2()<=0 || currentPosition.get2()>=100) {
                xyspeed = new Pair<>(xyspeed.get1(), -xyspeed.get2());
            }
        }
    }

    private void updateAll() {
        for(var movement : movementInfo) {
            movement.updateAll();
        }
    }

    public Movement generateMovement() {
        return new AbstractMovement(currentPosition, xyspeed, xyacceleration) {

            public void update() {
                updateAll();
            }
        };
        
    }
}
