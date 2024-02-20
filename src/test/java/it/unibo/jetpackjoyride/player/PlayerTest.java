package it.unibo.jetpackjoyride.player;

import static it.unibo.jetpackjoyride.core.entities.entity.api.Entity.EntityType.OBSTACLE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.testfx.framework.junit5.ApplicationTest;

import it.unibo.jetpackjoyride.core.entities.barry.api.Barry.BarryStatus;
import it.unibo.jetpackjoyride.core.entities.barry.impl.PlayerMover;
import it.unibo.jetpackjoyride.core.entities.obstacle.api.Obstacle.ObstacleType;
import it.unibo.jetpackjoyride.core.statistical.api.GameStatsController;
import it.unibo.jetpackjoyride.core.statistical.impl.GameStatsHandler;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;



public class PlayerTest extends ApplicationTest {

    private PlayerMover playermover;
    private GameStatsController gameStatsController;

    
    @Override
    public void start(Stage stage) {
        this.gameStatsController = new GameStatsHandler();
        this.playermover = new PlayerMover(this.gameStatsController);
    }

    @Test
    public void testPlayerInitialization(){
      assertTrue(this.playermover.getModel().isActive());
      assertTrue(this.playermover.getModel().isAlive());
      assertEquals(this.playermover.getModel().getBarryStatus(), BarryStatus.WALKING);
      

    }
    @Test 
    public void testPlayerHit(){
        this.playermover.setBarryShield();
        var status = this.playermover.getModel().getBarryStatus();
        this.playermover.hit(ObstacleType.ZAPPER);
        assertEquals(this.playermover.getModel().getBarryStatus(), status);
        assertTrue(this.playermover.getModel().isAlive());
        
      this.playermover.hit(ObstacleType.MISSILE);
      assertFalse(this.playermover.getModel().isAlive());
      assertEquals(this.playermover.getModel().getBarryStatus(), BarryStatus.BURNED);
    }

    @Test 
    public void testPlayerHit2(){
        this.playermover.hit(ObstacleType.ZAPPER);
        assertFalse(this.playermover.getModel().isAlive());
        assertEquals(BarryStatus.ZAPPED, this.playermover.getModel().getBarryStatus());
    }

    @Test 
    public void testPlayerMove(){
        this.playermover.move(true);
        assertEquals(BarryStatus.PROPELLING, this.playermover.getModel().getBarryStatus());
        this.playermover.move(true);
        this.playermover.move(false);
        assertEquals(BarryStatus.FALLING, this.playermover.getModel().getBarryStatus());
        for(int i =0 ; i<10; i++){
            this.playermover.move(false);
        }
 
        assertEquals(BarryStatus.WALKING, this.playermover.getModel().getBarryStatus());
        
        for(int i =0 ; i<100; i++){
            this.playermover.move(true);
        }
        assertEquals(BarryStatus.HEAD_DRAGGING, this.playermover.getModel().getBarryStatus());
    }

    @Test
    public void testDeactivation(){
        this.playermover.deactivate();
        assertFalse(this.playermover.getModel().isActive());
    }

    




}
