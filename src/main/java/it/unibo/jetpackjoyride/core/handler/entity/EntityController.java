package it.unibo.jetpackjoyride.core.handler.entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import it.unibo.jetpackjoyride.core.entities.entity.api.Entity;
import it.unibo.jetpackjoyride.core.entities.entity.api.Entity.EntityType;
import it.unibo.jetpackjoyride.core.handler.obstacle.ObstacleView;
import it.unibo.jetpackjoyride.core.handler.pickup.PickUpView;
import it.unibo.jetpackjoyride.core.handler.player.BarryView;
import it.unibo.jetpackjoyride.core.handler.powerup.PowerUpView;
import it.unibo.jetpackjoyride.menu.shop.api.ShopController;
import it.unibo.jetpackjoyride.utilities.EntityImageLoader;
import javafx.scene.Group;
import javafx.scene.Node;

public class EntityController {
    private EntityHandler entityHandler;
    private EntityImageLoader imageLoader = new EntityImageLoader();
    private Map<Entity,EntityView> modelViewMapper;

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
                final EntityView entityView;
                    switch (entity.getEntityType()) {
                        case BARRY:
                            entityView = new BarryView();
                            break;
                        case OBSTACLE:
                            entityView = new ObstacleView(this.imageLoader.loadImages(EntityType.OBSTACLE));
                            break;
                        case POWERUP:
                            entityView = new PowerUpView(this.imageLoader.loadImages(EntityType.POWERUP));
                            break;
                        case PICKUP:
                            entityView = new PickUpView(this.imageLoader.loadImages(EntityType.PICKUP));
                            break;
                        default:
                            entityView = new ObstacleView(this.imageLoader.loadImages(EntityType.OBSTACLE));
                            break;
                    }

                    this.modelViewMapper.put(entity, entityView);
                    entityGroup.getChildren().addAll(entityView.getImageView());
            }

            this.modelViewMapper.get(entity).updateView(entity);
        }

        final List<EntityView> entityViews = this.modelViewMapper.entrySet().stream()
                .filter(p -> !this.entityHandler.getAllEntities().contains(p.getKey())).map(p -> p.getValue()).toList();

        this.modelViewMapper.keySet().retainAll(this.entityHandler.getAllEntities());
        entityGroup.getChildren().removeAll(entityViews.stream().flatMap(v ->  v.getImageView().stream().map(e -> (Node)e)).toList());
        return true;
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
