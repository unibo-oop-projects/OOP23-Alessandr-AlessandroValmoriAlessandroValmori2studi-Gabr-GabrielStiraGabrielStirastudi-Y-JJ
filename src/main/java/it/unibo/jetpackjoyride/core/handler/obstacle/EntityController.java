package it.unibo.jetpackjoyride.core.handler.obstacle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.swing.text.View;

import it.unibo.jetpackjoyride.core.entities.entity.api.Entity;
import it.unibo.jetpackjoyride.core.entities.entity.api.Entity.EntityStatus;
import it.unibo.jetpackjoyride.core.entities.obstacle.api.Obstacle;
import it.unibo.jetpackjoyride.core.handler.entity.EntityHandler;
import it.unibo.jetpackjoyride.core.handler.generic.EntityView;
import it.unibo.jetpackjoyride.core.view.AnimationInfo;
import it.unibo.jetpackjoyride.core.view.EntityAnimation;
import it.unibo.jetpackjoyride.utilities.Pair;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;

public class EntityController {
    private Map<Entity,EntityView> modelViewMapper = new HashMap<>();
    private EntityHandler entityHandler;
    private EntityAnimation animation = new EntityAnimation();
    private List<EntityView> entityViews = new ArrayList<>();

    public void update(final Group entityGroup, final boolean isSpaceBarPressed) {
        this.entityHandler.update(entityGroup, isSpaceBarPressed);

        for(final Entity entity : this.entityHandler.getAllEntities()) {
            if(!this.modelViewMapper.containsKey(entity)) {
                final EntityView entityView = new EntityView(this.getEntityImages(entity));
                this.modelViewMapper.put(entity, entityView);
                entityGroup.getChildren().add((Node) entityView.getImageView());
            } else {
                final EntityView entityView = this.modelViewMapper.get(entity);
                final AnimationInfo animationInfos = this.getAnimationInfos(entity);
                entityView.setAnimationInfos(entity.getHitbox(), animationInfos);

            }
            this.modelViewMapper.get(entity).updateView();
        }

        entityViews = this.modelViewMapper.entrySet().stream().filter(p -> !this.entityHandler.getAllEntities().contains(p.getKey())).map(p -> p.getValue()).toList();
        this.modelViewMapper.keySet().retainAll(this.entityHandler.getAllEntities());
        entityGroup.getChildren().removeAll(entityViews.stream().map(v -> (Node)v.getImageView()).toList());
    }

    private List<Image> getEntityImages(Entity entity) {
        return animation.loadImages(entity);
    }

    private AnimationInfo getAnimationInfos(Entity entity) {
        return animation.loadAnimationInfosFor(entity);
    }
    
}







