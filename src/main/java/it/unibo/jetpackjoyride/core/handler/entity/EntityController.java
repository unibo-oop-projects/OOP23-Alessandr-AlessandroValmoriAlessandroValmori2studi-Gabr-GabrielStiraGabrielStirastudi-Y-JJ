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

public class EntityController {
    private EntityHandler entityHandler;
    private EntityImageLoader imageLoader = new EntityImageLoader();
    private Map<Entity, EntityView> modelViewMapper;

    public EntityController(ShopController shop) {
        this.entityHandler = new EntityHandler();
        this.entityHandler.initialize(shop);
        this.modelViewMapper = new HashMap<>();
    }

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
                var shield = ((BarryView) this.modelViewMapper.get(entity)).getShieldImageView();
                if ((((BarryImpl) entity).hasShield()) && !entityGroup.getChildren().contains(shield)) {
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
        } catch (Exception e) {
            entityView = new ObstacleView(this.imageLoader.loadImages(entity));
        }

        return entityView;
    }

    public void stop() {
        this.entityHandler.stop();
    }

    public void start() {
        this.entityHandler.start();
    }

    public void reset() {
        this.entityHandler.reset();
    }

}
