/**
 * BarryImpl class represents the implementation of the Barry interface,
 * which defines the behavior of the player character in the Jetpack Joyride game.
 *
 * This class manages Barry's position, movement, and performingAction, including walking,
 * falling, and propelling using a jetpack. It also handles hitbox-related operations.
 */
package it.unibo.jetpackjoyride.core.entities.barry.impl;
import it.unibo.jetpackjoyride.core.entities.barry.api.Barry;
import it.unibo.jetpackjoyride.core.entities.entity.api.AbstractEntity;

import it.unibo.jetpackjoyride.core.entities.obstacle.api.Obstacle.ObstacleType;

import it.unibo.jetpackjoyride.core.movement.Movement;

import it.unibo.jetpackjoyride.core.statistical.api.GameStatsController;
import it.unibo.jetpackjoyride.core.hitbox.api.Hitbox;
import it.unibo.jetpackjoyride.utilities.Pair;
import java.util.List;
import it.unibo.jetpackjoyride.utilities.MovementChangers;


/**
 * BarryImpl class implements the Barry interface and provides the functionality
 * for controlling the player character, Barry, in the Jetpack Joyride game.
 */
public final class BarryImpl extends AbstractEntity implements Barry {

    private PerformingAction performingAction;
    private boolean hasShield;
    private BarryLifeStatus lifeStatus;

    private static final Double HIGH_BOUND = 80.0;
    /**
     * Defines what coordinate could be considerated as the lower bound of the map.
     */
    private static final Double LOW_BOUND = 630.0;


   
    public BarryImpl(final Movement movement, final Hitbox hitbox) {
       
        super(EntityType.BARRY,movement, hitbox);
        this.hasShield = false;
        this.lifeStatus = BarryLifeStatus.ALIVE;
        this.entityStatus = EntityStatus.ACTIVE;
        this.performingAction = PerformingAction.FALLING;

    }
    @Override
    public PerformingAction getPerformingAction(){
        return this.performingAction;
    }

    
   
    @Override
    public boolean hasShield() {
        return this.hasShield;
    }

    
    @Override
    public boolean isAlive() {
        return this.lifeStatus.equals(BarryLifeStatus.ALIVE);
    }

    /**
     * Kills Barry based on the type of obstacle.
     * Updates Barry's hitbox and performingAction accordingly.
     *
     * @param type the type of obstacle that killed Barry
     */
    @Override
    public void kill(final ObstacleType type) {

        this.lifeStatus= BarryLifeStatus.DEAD;
        this.performingAction = type.equals(ObstacleType.ZAPPER) ? PerformingAction.ZAPPED : 
        type.equals(ObstacleType.LASER) ? PerformingAction.LASERED : 
        type.equals(ObstacleType.MISSILE) ? PerformingAction.BURNED : PerformingAction.UNDEFINED;
    }

    /**
     * Removes the shield from Barry.
     */
    @Override
    public void removeShield() {
        this.hasShield=false;
    }
    /**
     * Sets the shield on Barry.
     */
    @Override
    public void setShieldOn() {
        this.hasShield=true;
    }
    /**
     * Sets the life performingAction of Barry.
     *
     * @param lifeStatus the life performingAction of Barry
     */
    @Override
    public void setLifeStatus(final BarryLifeStatus lifeStatus) {
        this.lifeStatus = lifeStatus;
    }
    

    @Override
    protected void updateStatus(boolean isSpaceBarPressed) {
    
       
        switch(this.performingAction){
            case WALKING : 
            if(isSpaceBarPressed){
                this.performingAction = PerformingAction.PROPELLING;
            }
            break;
            case PROPELLING : 
            if(!isSpaceBarPressed){
                this.performingAction = PerformingAction.FALLING;
            }
            if(this.movement.getRealPosition().get2() <= HIGH_BOUND){
                this.performingAction = PerformingAction.HEAD_DRAGGING;
            }
            break;

            case FALLING : 
            if(isSpaceBarPressed){
                this.performingAction = PerformingAction.PROPELLING;
                
            }
            if(this.movement.getRealPosition().get2() >= LOW_BOUND){
                this.performingAction = PerformingAction.WALKING;
            }
            break;
            case HEAD_DRAGGING:

            if(!isSpaceBarPressed){
                this.performingAction = PerformingAction.FALLING;
            }
            
            break;

            default : 
            break;

        }
        System.out.println(this.performingAction);

        this.movement = new Movement.Builder()
        .setPosition(this.movement.getRealPosition())
        .setSpeed(this.movement.getSpeed())
        .setAcceleration(new Pair<>(this.movement.getAcceleration().get1(), this.movement.getAcceleration().get2()*100.0))
        .setRotation(this.movement.getSpeed().get2()*2, 0.0)
        .setMovementChangers(List.of(MovementChangers.BOUNDS,
             this.performingAction.equals(PerformingAction.PROPELLING) || this.performingAction.equals(PerformingAction.HEAD_DRAGGING) ? MovementChangers.SPEDUP : MovementChangers.GRAVITY))
        .build();

       

    }
  

    
}
