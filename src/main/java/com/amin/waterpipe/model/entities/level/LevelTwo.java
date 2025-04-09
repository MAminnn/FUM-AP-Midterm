package com.amin.waterpipe.model.entities.level;

import com.amin.waterpipe.model.data.Maps;
import com.amin.waterpipe.model.entities.Map;

public class LevelTwo extends BaseLevel {
    public LevelTwo() {
        super(Maps.LevelTwoMapTemplate);
    }

    public Map getMap() {
        return this._map;
    }

    public final int numberOfMoves = 15;
}
