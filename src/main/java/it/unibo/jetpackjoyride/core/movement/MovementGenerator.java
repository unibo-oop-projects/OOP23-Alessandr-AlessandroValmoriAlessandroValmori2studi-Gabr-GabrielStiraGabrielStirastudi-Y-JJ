package it.unibo.jetpackjoyride.core.movement;

import it.unibo.jetpackjoyride.utilities.Pair;
import java.util.*;

public class MovementGenerator {

    private final static Pair<Double,Double> DEFAULTSPEED = new Pair<>(-100.0, 0.0);
    private final static Double SPEEDYMODIFIER = 1.5;
    private final static Double SLOWMODIFIER = 0.7;

    private Pair<Double,Double> currentPos;
    private Pair<Double,Double> speed;
    private Pair<Double,Double> acceleration;
    private Pair<Double, Double> rotationInfo;

    private List<MovementChangers> listOfModifiers = new ArrayList<>();

    public enum MovementChangers{
        DIAGONALUP, // Same speed is set for both x and y velocity, creating a diagonal trajectory (UP)
        DIAGONALDOWN, // Same speed is set for both x and y velocity (now inverted), creating a diagonal trajectory (DOWN)
        BOUNCING, // Once the upper or lower bound of the screen is hit, the y speed is inverted
        HOMING, // Acceleration of y in changed based on the y difference between the player entity and this entity
        SLOW, // Slower speed set by the SLOWMODIFIER
        SPEEDY, // Faster speed set by the SPEEDYMODIFIER
        STATIC, // No velocity, the object is still and doesn't move
        GRAVITY // y speed is accelerated downwards
    }

    public MovementGenerator(Pair<Double, Double> startingPosition, Pair<Double, Double> startingSpeed, Pair<Double, Double> startingAcceleration, Pair<Double, Double> rotationInfo) {
        this.currentPos = startingPosition;
        this.speed = startingSpeed;
        this.acceleration = startingAcceleration;
        this.rotationInfo = rotationInfo;
        this.setMovementChangers(List.of());
    }

    public Movement setMovementChangers(List<MovementChangers> listOfChangers) {
        this.listOfModifiers = listOfChangers;
        this.speed = DEFAULTSPEED;

        for (var type : listOfModifiers) {
            switch (type) {
                case SLOW:
                    this.speed = new Pair<>(SLOWMODIFIER*(this.speed.get1()), SLOWMODIFIER*(this.speed.get2()));
                    break;
                case SPEEDY:
                    this.speed = new Pair<>(SPEEDYMODIFIER*(this.speed.get1()), SPEEDYMODIFIER*(this.speed.get2())); 
                    break;
                case DIAGONALUP:
                    this.speed = new Pair<>(this.speed.get1(), this.speed.get1());
                    this.rotationInfo = new Pair<>(45.0, this.rotationInfo.get2());
                    break;
                case DIAGONALDOWN:
                    this.speed = new Pair<>(this.speed.get1(), -this.speed.get1());
                    this.rotationInfo = new Pair<>(-45.0, this.rotationInfo.get2());
                    break;
                case STATIC:
                    this.speed = new Pair<>(0.0,0.0);
                default:
                    break;
            }
        }
        return new AbstractMovement(this.currentPos, this.speed, this.acceleration, this.rotationInfo, this.listOfModifiers) {

            @Override
            public void update() {
                applyModifiers();
                /* V = U + AT */
                xyspeed = new Pair<>(xyspeed.get1() + xyacceleration.get1() * TIME , xyspeed.get2() + xyacceleration.get2() * TIME);
                /* S = V * T */
                currentPosition = new Pair<>(currentPosition.get1() + xyspeed.get1() * TIME, currentPosition.get2() + xyspeed.get2() * TIME);
                rotationInfo = new Pair<>(rotationInfo.get1()+rotationInfo.get2(), rotationInfo.get2());
            }

            @Override
            public void applyModifiers() {
                /* HOMING */
                if(listOfChangers.contains(MovementChangers.HOMING)) {
                    xyacceleration = new Pair<>(xyacceleration.get1(), 0.1*(playerPos.get2() - currentPosition.get2()));
                }

                /* BOUNCING */
                if(listOfChangers.contains(MovementChangers.BOUNCING)) {
                    if(currentPosition.get2()<0 ) {
                        xyspeed = new Pair<>(xyspeed.get1(), Math.abs(xyspeed.get2()));
                        rotationInfo = new Pair<>(-Math.abs(rotationInfo.get1()),rotationInfo.get2());
                    }
                    if(currentPosition.get2()>700) {
                        xyspeed = new Pair<>(xyspeed.get1(), -Math.abs(xyspeed.get2()));
                        rotationInfo = new Pair<>(Math.abs(rotationInfo.get1()),rotationInfo.get2());
                    }
                }

                /* GRAVITY */
                if(listOfChangers.contains(MovementChangers.GRAVITY)) {
                    if(currentPosition.get2()<650) {
                        xyacceleration = new Pair<>(xyacceleration.get1(), xyacceleration.get2()+8.0);
                    }
                }
            }
            
        };
    }


}