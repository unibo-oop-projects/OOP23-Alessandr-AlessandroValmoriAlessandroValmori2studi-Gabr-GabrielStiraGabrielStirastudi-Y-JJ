package it.unibo.jetpackjoyride.core.movement;

import it.unibo.jetpackjoyride.utilities.Pair;
import java.util.*;

public class MovementGenerator {

    private final static Pair<Double,Double> MAPMOVINGSPEED = new Pair<>(-100.0, 0.0);
    private final static Double SPEEDYMODIFIER = 1.5;
    private final static Double SLOWMODIFIER = 0.7;

    private Pair<Double,Double> currentPos;
    private Pair<Double,Double> speed;
    private Pair<Double,Double> acceleration;
    private Pair<Double, Double> rotationInfo;

    private List<MovementChangers> listOfModifiers = new ArrayList<>();

    public enum MovementChangers{
        INITIALLYSTILL, // x and y speed is initially 0 but can be changed
        BOUNCING, // Once the upper or lower bound of the screen is hit, the y speed is inverted
        HOMING, // Acceleration of y in changed based on the y difference between the player entity and this entity
        SLOW, // Slower initial speed set by the SLOWMODIFIER
        SPEEDY, // Faster initial speed set by the SPEEDYMODIFIER
        STATIC, // No velocity, the object is still and doesn't move
        GRAVITY, // y speed is accelerated downwards
        INVERSEGRAVITY, // y speed is accelerated upwards
        BOUNDS, // x and y will only vary between specified limits
    }

    public MovementGenerator(Pair<Double, Double> startingPosition, Pair<Double, Double> startingSpeed, Pair<Double, Double> startingAcceleration, Pair<Double, Double> rotationInfo) {
        this.currentPos = startingPosition;
        this.speed = startingSpeed;
        this.acceleration = startingAcceleration;
        this.rotationInfo = rotationInfo;
    }

    public MovementGenerator(Movement oldMovement) {
        this.currentPos = oldMovement.getCurrentPosition();
        this.speed = oldMovement.getSpeed();
        this.acceleration = oldMovement.getAcceleration();
        this.rotationInfo = oldMovement.getRotation();
    }

    public Movement setMovementChangers(List<MovementChangers> listOfChangers) {
        this.listOfModifiers = listOfChangers;

        Double speedModifier = (this.listOfModifiers.contains(MovementChangers.SPEEDY) ? SPEEDYMODIFIER : this.listOfModifiers.contains(MovementChangers.SLOW) ? SLOWMODIFIER : this.listOfModifiers.contains(MovementChangers.INITIALLYSTILL) ? 0.0 : 1.0);
        this.speed = new Pair<>(MAPMOVINGSPEED.get1()*speedModifier, MAPMOVINGSPEED.get2()*speedModifier); //initial speed

        return new AbstractMovement(this.currentPos, this.speed, this.acceleration, this.rotationInfo, this.listOfModifiers) {

            @Override
            public void update() {
                this.applyModifiers();
                /* V = U + AT */
                this.setSpeed(new Pair<>(this.getSpeed().get1() + this.getAcceleration().get1() * TIME , this.getSpeed().get2() + this.getAcceleration().get2() * TIME));
                /* S = V * T */
                this.setCurrentPosition(new Pair<>(this.getCurrentPosition().get1() + this.getSpeed().get1() * TIME, this.getCurrentPosition().get2() + this.getSpeed().get2() * TIME));
                this.setRotation(new Pair<>(this.getRotation().get1()+this.getRotation().get2(), this.getRotation().get2()));
            }

            @Override
            public void applyModifiers() {
                /* HOMING */
                if(this.getMovementChangers().contains(MovementChangers.HOMING)) {
                    this.setAcceleration(new Pair<>(this.getAcceleration().get1(), 0.1*(playerPos.get2() - this.getCurrentPosition().get2())));
                }

                /* GRAVITY */
                if(this.getMovementChangers().contains(MovementChangers.GRAVITY)) {
                    this.setAcceleration(new Pair<>(this.getAcceleration().get1(), +30.0));
                }

                /* INVERSEGRAVITY */
                if(this.getMovementChangers().contains(MovementChangers.INVERSEGRAVITY)) {
                    this.setAcceleration(new Pair<>(this.getAcceleration().get1(), -30.0));
                }

                /* BOUNCING */
                if(this.getMovementChangers().contains(MovementChangers.BOUNCING)) {
                    if(this.getCurrentPosition().get2()<0 ) {
                        this.setSpeed(new Pair<>(this.getSpeed().get1(), Math.abs(this.getSpeed().get2())));
                        this.setRotation(new Pair<>(-Math.abs(this.getRotation().get1()),this.getRotation().get2()));
                    }
                    if(this.getCurrentPosition().get2()>700) {
                        this.setSpeed(new Pair<>(this.getSpeed().get1(), -Math.abs(this.getSpeed().get2())));
                        this.setRotation(new Pair<>(Math.abs(this.getRotation().get1()),this.getRotation().get2()));
                    }
                }

                /* STATIC */
                if(this.getMovementChangers().contains(MovementChangers.STATIC)) {
                    this.setAcceleration(new Pair<>(0.0,0.0));
                    this.setSpeed(new Pair<>(0.0,0.0));
                }

                /* BOUNDS */
                if(this.getMovementChangers().contains(MovementChangers.BOUNDS)) {
                    if(this.getCurrentPosition().get2()>650) {
                        this.setCurrentPosition(new Pair<>(this.getCurrentPosition().get1(), 650.0));
                        if(this.getSpeed().get2()>0) {
                            this.setSpeed(new Pair<>(this.getSpeed().get1(), 0.0));
                        }
                    }
                    if(this.getCurrentPosition().get2()<150) {
                        this.setCurrentPosition(new Pair<>(this.getCurrentPosition().get1(), 150.0));
                        if(this.getSpeed().get2()<0) {
                            this.setSpeed(new Pair<>(this.getSpeed().get1(), 0.0));
                        }
                    }
                }
            }
        };
    }


}