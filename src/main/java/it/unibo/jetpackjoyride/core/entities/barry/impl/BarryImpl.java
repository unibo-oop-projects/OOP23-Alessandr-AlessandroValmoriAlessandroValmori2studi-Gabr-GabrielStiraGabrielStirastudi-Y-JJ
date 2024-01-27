package it.unibo.jetpackjoyride.core.entities.barry.impl;

import it.unibo.jetpackjoyride.core.entities.barry.api.Barry;
import it.unibo.jetpackjoyride.utilities.Pair;
import java.awt.event.KeyEvent;

public class BarryImpl implements Barry {

    private Pair<Integer, Integer> barryPos;
    private Pair<Integer, Integer> velocity;
    private Pair<Integer, Integer> acceleration;

    private final KeyHandler keyH = new KeyHandler();

    private int heightLimit = 300;

    public BarryImpl(Pair<Integer, Integer> barryPos, Pair<Integer, Integer> velocity, Pair<Integer, Integer> acceleration ){
        this.barryPos=barryPos;
        this.velocity=velocity;
        this.acceleration=acceleration;
    }

  
    public void loop(){
        while(true) {
			try {
				Thread.sleep(30);
			  } catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			  }

              this.controlPlayer();

			System.out.println(this.barryPos);
		}
    }

    private void controlPlayer() {
        if (keyH.getCurrentInput(KeyEvent.VK_SPACE)) {
            this.propel();

            System.out.println("oooooooooooooooooooo");
        } else if (this.barryPos.get2() > 0) {
            this.descend();

        }
    }


    
    private void propel() {
      this.barryPos = new Pair<>(this.barryPos.get1(), this.barryPos.get2()+1);
    }

    private void descend(){
        if(this.barryPos.get2() > 0){
            this.barryPos= new Pair<>(this.barryPos.get1(), this.barryPos.get2()-1);
        }
    }


  
    
    
}
