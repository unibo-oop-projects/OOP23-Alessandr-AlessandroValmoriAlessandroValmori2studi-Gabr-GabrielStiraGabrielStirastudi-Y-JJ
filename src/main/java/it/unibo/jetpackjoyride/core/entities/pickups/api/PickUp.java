package it.unibo.jetpackjoyride.core.entities.pickups.api;

import it.unibo.jetpackjoyride.core.entities.entity.api.Entity;

public interface PickUp extends Entity {
    enum PickUpType {
        VEHICLE, //COIN, //SHIELD, //BOOST
    }

    PickUpType getPickUpType();
}