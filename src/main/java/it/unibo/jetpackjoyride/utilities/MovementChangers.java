package it.unibo.jetpackjoyride.utilities;

public enum MovementChangers {

    BOUNCING, // Once the upper or lower bound of the screen is hit, the y speed is inverted
    GRAVITY, // y speed is accelerated downwards
    INVERSEGRAVITY, // y speed is accelerated upwards
    BOUNDS, // x and y will only vary between specified limits
    SPEDUP //lol

    
}
