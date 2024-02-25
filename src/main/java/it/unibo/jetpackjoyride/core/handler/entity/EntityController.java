package it.unibo.jetpackjoyride.core.handler.entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import it.unibo.jetpackjoyride.core.entities.barry.impl.BarryImpl;
import it.unibo.jetpackjoyride.core.entities.entity.api.Entity;
import it.unibo.jetpackjoyride.core.handler.obstacle.ObstacleView;
import it.unibo.jetpackjoyride.core.handler.pickup.PickUpView;
import it.unibo.jetpackjoyride.core.handler.player.BarryView;
import it.unibo.jetpackjoyride.core.handler.powerup.PowerUpView;
import it.unibo.jetpackjoyride.menu.shop.api.ShopController;
import it.unibo.jetpackjoyride.utilities.EntityImageLoader;
import it.unibo.jetpackjoyride.utilities.exceptions.NotImplementedObjectException;
import javafx.scene.Group;
import javafx.scene.Node;

/**
 * A single, universal controller, used to manage the view of every entity whose
 * model and view extend {@link AbstractEntity} and {@link AbstractEntityView} 
 * respectively.
 * 
 * @author alessandro.valmori2@studio.unibo.it
 * @author gabriel.stira@studio.unibo.it
 */

public class EntityController {

    /** 
     * The model that incapsulates all other entity models .
     * */
    private final EntityHandler entityHandler;

    /**
     * A utility class used to calculate the corresponding
     * list of images based on model information.
     */
    private final EntityImageLoader imageLoader = new EntityImageLoader();
    /**
     * A map used to maintain a correspondence between active entities
     * and the associated view, mainly used for boosting performance, in order
     * not to reinstanciate the view of each model every update cycle.
     */
    private final Map<Entity, EntityView> modelViewMapper;

    /**
     * The constructor of the {@link EntityController} class.
     * 
     * @param shop the {@link ShopController}, only to pass it down
     *             to {@link EntityHandler}, which then uses shop to extract information
     *             regarding unlocked items.
     */
    public EntityController(final ShopController shop) {
        this.entityHandler = new EntityHandler();
        this.entityHandler.initialize(shop);
        this.modelViewMapper = new HashMap<>();
    }

    /**
     * The update function which gets executed each frame, responsible
     * for updating the {@link modelViewMapper} in its interity.
     * Its only responsibility is to update the view component of each entity model
     * by extracting information through getter methods, never anything else.
     * 
     * @param entityGroup
     * @param isSpaceBarPressed
     * @return false if the game has ended, true otherwise.
     */
    public boolean update(final Group entityGroup, final boolean isSpaceBarPressed) {
        if (!this.entityHandler.update(entityGroup, isSpaceBarPressed)) {
            return false;
        }
        for (final Entity entity : this.entityHandler.getAllEntities()) {
            if (!this.modelViewMapper.containsKey(entity)) {
                final EntityView entityView = this.viewImagesLoader(entity);
                this.modelViewMapper.put(entity, entityView);
                entityGroup.getChildren().add(entityView.getImageView());
            }
            if (entity instanceof BarryImpl) {
                final var shield = ((BarryView) this.modelViewMapper.get(entity)).getShieldImageView();
                if (((BarryImpl) entity).hasShield() && !entityGroup.getChildren().contains(shield)) {
                    entityGroup.getChildren().add(shield);
                } else if (entityGroup.getChildren().contains(shield) && !(((BarryImpl) entity).hasShield())) {
                    entityGroup.getChildren().remove(shield);
                }
            }
            this.modelViewMapper.get(entity).updateView(entity);
        }
        final List<EntityView> entityViews = this.modelViewMapper.entrySet().stream()
                .filter(p -> !this.entityHandler.getAllEntities().contains(p.getKey())).map(p -> p.getValue()).toList();
        this.modelViewMapper.keySet().retainAll(this.entityHandler.getAllEntities());
        entityGroup.getChildren().removeAll(entityViews.stream().map(e -> (Node) e.getImageView()).toList());
        return true;
    }

    /**
     * A utility function used to instanciate the view associated with the
     * {@link Entity}.
     * 
     * @param entity the {@link Entity}
     * @return the {@link EntityView} constructed based on the {@link EntityType}
     */
    private EntityView viewImagesLoader(final Entity entity) {
        EntityView entityView;
        try {
            switch (entity.getEntityType()) {
                case BARRY:
                    entityView = new BarryView();
                    break;
                case OBSTACLE:
                    entityView = new ObstacleView(this.imageLoader.loadImages(entity));
                    break;
                case POWERUP:
                    entityView = new PowerUpView(this.imageLoader.loadImages(entity));
                    break;
                case PICKUP:
                    entityView = new PickUpView(this.imageLoader.loadImages(entity));
                    break;
                default:
                    throw new NotImplementedObjectException("Tried to spawn an alien entity");

            }
        } catch (NotImplementedObjectException e) {
            entityView = new ObstacleView(this.imageLoader.loadImages(entity));
        }

        return entityView;
    }

    /** A method used to signal {@link EntityHandler} to stop.*/
    public void stop() {
        this.entityHandler.stop();
    }

    /**
     * A method usede to signal {@link EntityHandler} to start.
     */
    public void start() {
        this.entityHandler.start();
    }

    /**
     * A method used to reset {@link EntityHandler}.
     */
    public void reset() {
        this.entityHandler.reset();
    }
}
