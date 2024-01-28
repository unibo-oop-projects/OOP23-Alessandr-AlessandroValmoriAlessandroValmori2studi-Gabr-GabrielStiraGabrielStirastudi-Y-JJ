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
    private Double rotationAngle;

    private List<MovementChangers> listOfModifiers = new ArrayList<>();

    public enum MovementChangers{
        DEFAULT, DIAGONALUP, DIAGONALDOWN, BOUNCING, HOMING, SLOW, SPEEDY,
    }

    public MovementGenerator(Pair<Double, Double> startingPosition, Pair<Double, Double> startingSpeed, Pair<Double, Double> startingAcceleration) {
        this.currentPos = startingPosition;
        this.speed = startingSpeed;
        this.acceleration = startingAcceleration;
        this.rotationAngle = 0.0;
        setMovementChangers(List.of(MovementChangers.DEFAULT));
    }

    public Movement setMovementChangers(List<MovementChangers> listOfChangers) {
        this.listOfModifiers = listOfChangers;

        for (var type : listOfModifiers) {
            switch (type) {
                case DEFAULT:
                    this.speed = DEFAULTSPEED;
                    break;
                case SLOW:
                    this.speed = new Pair<>(SLOWMODIFIER*(this.speed.get1()), SLOWMODIFIER*(this.speed.get2()));
                    break;
                case SPEEDY:
                    this.speed = new Pair<>(SPEEDYMODIFIER*(this.speed.get1()), SPEEDYMODIFIER*(this.speed.get2())); 
                    break;
                case DIAGONALUP:
                    this.speed = new Pair<>(this.speed.get1(), this.speed.get1());
                    this.rotationAngle = 45.0;
                    break;
                case DIAGONALDOWN:
                    this.speed = new Pair<>(this.speed.get1(), -this.speed.get1());
                    this.rotationAngle = -45.0;
                default:
                    break;
            }
        }


        return new AbstractMovement(this.currentPos, this.speed, this.acceleration, this.listOfModifiers, this.rotationAngle) {

            @Override
            public void update() {
                applyModifiers();
                /* V = U + AT */
                xyspeed = new Pair<>(xyspeed.get1() + xyacceleration.get1() * TIME , xyspeed.get2() + xyacceleration.get2() * TIME);
                /* S = V * T */
                currentPosition = new Pair<>(currentPosition.get1() + xyspeed.get1() * TIME, currentPosition.get2() + xyspeed.get2() * TIME);
            }

            @Override
            public void applyModifiers() {
                /* HOMING */
                if(listOfChangers.contains(MovementChangers.HOMING)) {
                    xyacceleration = new Pair<>(xyacceleration.get1(), 0.1*(playerPos.get2() - currentPosition.get2()));
                }

                /* BOUNCING */
                if(listOfChangers.contains(MovementChangers.BOUNCING)) {
                    if(currentPosition.get2()<0 || currentPosition.get2()>700) {
                        xyspeed = new Pair<>(xyspeed.get1(), -xyspeed.get2());
                        rotationAngle = -rotationAngle;
                    }
                }
            }
            
        };
    }


}