package it.unibo.jetpackjoyride.core.handler.obstacle;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import it.unibo.jetpackjoyride.core.entities.entity.api.Entity;
import it.unibo.jetpackjoyride.core.entities.entity.api.Entity.EntityStatus;
import it.unibo.jetpackjoyride.core.entities.obstacle.api.Obstacle;
import it.unibo.jetpackjoyride.core.handler.entity.EntityHandler;
import it.unibo.jetpackjoyride.core.handler.generic.EntityView;
import it.unibo.jetpackjoyride.core.view.EntityAnimation;
import it.unibo.jetpackjoyride.utilities.Pair;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;

public class EntityController {
    private Map<Entity,EntityView> modelViewMapper = new HashMap<>();
    private EntityHandler entityHandler;
    private EntityAnimation animation = new EntityAnimation();

    public void update(final Group entityGroup, final boolean isSpaceBarPressed) {
        this.entityHandler.update(isSpaceBarPressed);

        for(final Entity entity : this.entityHandler.getAllEntities()) {
            if(!this.modelViewMapper.containsKey(entity)) {
                final EntityView entityView = new EntityView(this.calculateView(entity));
                modelViewMapper.put(entity, entityView);
                entityGroup.getChildren().add((Node) entityView.getImageView());
            }
        }

        this.modelViewMapper.keySet().retainAll(this.entityHandler.getAllEntities());


            if (entity.getEntityStatus().equals(EntityStatus.INACTIVE)) {
                entityGroup.getChildren().remove((Node) entityView.getImageView());
                this.listEntityModels.remove(entity);
            }
        }
    }

    private List<Image> calculateView(Entity entity) {
        return animation.loadAniationFor(entity);
    }
}





