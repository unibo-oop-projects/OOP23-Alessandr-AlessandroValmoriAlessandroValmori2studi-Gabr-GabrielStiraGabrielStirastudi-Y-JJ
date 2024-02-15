package it.unibo.jetpackjoyride.core.map.impl;

import it.unibo.jetpackjoyride.core.map.api.MapBackground;
import it.unibo.jetpackjoyride.core.map.api.MapBackgroundModel;
import it.unibo.jetpackjoyride.core.map.api.MapBackgroundView;
import javafx.scene.layout.Pane;


public class MapBackgroundImpl implements MapBackground {

    private MapBackgroundModel model;
    private MapBackgroundView view;

    public MapBackgroundImpl(){
        model = new MapBackgroundModelImpl();
        view = new MapBackgroungViewImpl();
    }

    @Override
    public void updateBackgroundView() {
        this.model.updateBackgroundModel();
    }

    @Override
    public void updateBackgroundModel() {
        this.view.updateBackgroundView(model.getPosX().get(0), model.getPosX().get(1)
        ,model.getSize().get1(), model.getSize().get2());
    }

    @Override
    public Pane getPane(){
        return view.getPane();
    }

}