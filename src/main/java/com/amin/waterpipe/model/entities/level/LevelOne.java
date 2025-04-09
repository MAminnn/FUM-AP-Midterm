package com.amin.waterpipe.model.entities.level;

import com.amin.waterpipe.model.data.Maps;
import com.amin.waterpipe.model.entities.Map;

public class LevelOne extends BaseLevel {

    public LevelOne() {
        super(Maps.LevelOneMapTemplate);
    }

    public Map getMap() {
        return this._map;
    }
}
