package it.unibo.jetpackjoyride.core.movement.impl;

import it.unibo.jetpackjoyride.core.movement.AbstractMovement;
import it.unibo.jetpackjoyride.core.movement.Movement;
import it.unibo.jetpackjoyride.utilities.Pair;

public class MovementFactoryImpl implements MovementFactory {

    public Double time = 0.1;

    private final static Double DEFAULTSPEED = 1.0; /* 100% normal */
    private final static Double SPEEDYMODIFIER = 100.5; /* 50% faster */
    private final static Double SLOWMODIFIER = 0.7; /* 70% slower */
    
    private void changeAccelerationBy(Pair<Double, Double> oldAcceleration, Pair<Double,Double> newAcceleration) {
        oldAcceleration = new Pair<>(oldAcceleration.get1() + newAcceleration.get1()*time, oldAcceleration.get2() + newAcceleration.get2()*time);
    }

    private void updateSpeed(Pair<Double, Double> speed, Pair<Double, Double> acceleration) {
        speed= new Pair<>(speed.get1() + acceleration.get1()*time, speed.get2() + acceleration.get2()*time);
    }

    private void updatePosition(Pair<Double, Double> oldPosition, Pair<Double,Double> speed) {
        oldPosition = new Pair<>(oldPosition.get1() + speed.get1()*time, oldPosition.get2() + speed.get2()*time);
    }

    @Override
    public Movement defaultMovement(Pair<Double, Double> position, Pair<Double, Double> speed, Pair<Double, Double> acceleration) {
        return new AbstractMovement(position, speed, acceleration) {
            @Override
            public void update() {
                xyacceleration=acceleration;
                xyspeed=speed;
                currentPosition = new Pair<>(currentPosition.get1() + speed.get1()*time, currentPosition.get2() + speed.get2()*time);

                /*changeAccelerationBy(xyacceleration, new Pair<>(0.0, 0.0));
                updateSpeed(new Pair<>(DEFAULTSPEED*speed.get1(), DEFAULTSPEED*speed.get2()), acceleration);
                updatePosition(currentPosition, xyspeed);*/
            }
        };
        
    }

    @Override
    public Movement homingMovement(Pair<Double, Double> position, Pair<Double, Double> speed, Pair<Double, Double> acceleration) {
        return new AbstractMovement(position, speed, acceleration) {
            @Override
            public void update() {
                changeAccelerationBy(xyacceleration, new Pair<>(xyacceleration.get1(), (/*playerPosition.get2()*/ - position.get2())));
                defaultMovement(position, speed, acceleration).update();
            }
        };
    }

    @Override
    public Movement bouncingMovement(Pair<Double, Double> position, Pair<Double, Double> speed,Pair<Double, Double> acceleration) {
        return new AbstractMovement(position, speed, acceleration) {

            @Override
            public void update() {
                defaultMovement(position, speed, acceleration).update();
                if(currentPosition.get2()<=0 || currentPosition.get2()>=100) {
                    xyspeed = new Pair<>(xyacceleration.get1(), -xyspeed.get2());
                }
            }
            
        };
    }

    @Override
    public Movement speedyMovement(Pair<Double, Double> position, Pair<Double, Double> speed, Pair<Double, Double> acceleration) {
       return new AbstractMovement(position, speed, acceleration) {
            @Override
            public void update() {
                defaultMovement(new Pair<>(currentPosition.get1()*SPEEDYMODIFIER, currentPosition.get2()+SPEEDYMODIFIER*xyspeed.get2()), xyspeed, xyacceleration).update();
            }
       };
    }

    @Override
    public Movement slowMovement(Pair<Double, Double> position, Pair<Double, Double> speed,Pair<Double, Double> acceleration) {
        return new AbstractMovement(position, speed, acceleration) {
            @Override
            public void update() {
                defaultMovement(new Pair<>(position.get1()+SLOWMODIFIER*position.get1(), position.get2()+SLOWMODIFIER*position.get2()), speed, acceleration).update();
            }
        };
    }

    @Override
    public Movement combineMore(Movement firstMovement, Movement secondMovement) {
        return new AbstractMovement(firstMovement.getCurrentPosition(), firstMovement.getSpeed(), firstMovement.getAcceleration()) {
            @Override
            public void update() {
                firstMovement.update();
                secondMovement.update();
            }
        };
    }

    
}
