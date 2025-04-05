package com.amin.waterpipe.model.entities.level;

import com.amin.waterpipe.model.constances.Maps;
import com.amin.waterpipe.model.entities.Map;
import com.amin.waterpipe.model.entities.pipe.Block;
import com.amin.waterpipe.model.entities.pipe.NormalPipe;
import com.amin.waterpipe.model.enums.PipeType;
import com.amin.waterpipe.model.valueobjects.Coordinate;

public class LevelOne extends BaseLevel {

    public LevelOne() {
        super(Maps.LevelOneMapTemplate);
    }

    public Map getMap() {
        return this._map;
    }
}
