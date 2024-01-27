package it.unibo.jetpackjoyride.core.handler;
import java.util.List;
import java.util.Random;

import it.unibo.jetpackjoyride.core.entities.entity.api.EntityGenerator;
import it.unibo.jetpackjoyride.core.entities.entity.impl.EntityGeneratorImpl;
import it.unibo.jetpackjoyride.core.entities.obstacle.api.Obstacle.ObstacleType;
import it.unibo.jetpackjoyride.core.entities.obstacle.impl.ObstacleImpl;
import it.unibo.jetpackjoyride.core.hitbox.Hitbox;
import it.unibo.jetpackjoyride.core.hitbox.impl.MissileHitbox;
import it.unibo.jetpackjoyride.core.movement.Movement;
import it.unibo.jetpackjoyride.core.movement.MovementGenerator;
import it.unibo.jetpackjoyride.core.movement.MovementGenerator.MovementChangers;
import it.unibo.jetpackjoyride.utilities.Pair;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.awt.Dimension;
import java.awt.Toolkit;


public class EntityHandlerImpl implements EntityHandler {

    private final static Movement DEFAULTMOVEMENT = new MovementGenerator(new Pair<>(1200.0,800.0), new Pair<>(0.0,0.0), new Pair<>(0.0,0.0)).setMovementChangers(List.of(MovementChangers.DEFAULT));
	private final static Movement HOMINGMOVEMENT = new MovementGenerator(new Pair<>(1200.0,800.0), new Pair<>(0.0,0.0), new Pair<>(0.0,0.0)).setMovementChangers(List.of(MovementChangers.DEFAULT, MovementChangers.HOMING));
    private final static Hitbox MISSILEHITBOX = new MissileHitbox(new Pair<>(0.0, 0.0));

    private EntityGenerator entityGenerator;
    private EntityView view;
    private ObstacleImpl ciao;
    private Random random = new Random();
    final Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();

    public void initialize() {
        entityGenerator = new EntityGeneratorImpl();
        ciao = this.entityGenerator.generateObstacle(ObstacleType.MISSILE, HOMINGMOVEMENT, MISSILEHITBOX);

        int index=0;
        Image[] images = new Image[35];
        for (int i = 0; i < 7; i++) {
            String imagePath = getClass().getClassLoader().getResource("sprites/entities/obstacles/missile/missile_" + (i+1) + ".png").toExternalForm();
            for(int j = 0 ; j < 5; j++) {
                images[index] = new Image(imagePath);  
                index++;
            }
        }
        view = new EntityView(images);
    }

    public void update() {
        ciao.update();
        view.updateView(ciao);
        if(ciao.isOutOfBounds()) {
            ciao.getEntityMovement().setCurrentPosition(new Pair<>(screen.getWidth(),random.nextDouble()*700));
        }
        System.out.println("Position: " + ciao.getEntityMovement().getCurrentPosition());
    }

    public ImageView getImageView() {
        return view.getImageView();
    }
}
