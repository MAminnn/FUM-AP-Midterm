package com.amin.waterpipe.model.entities.level;

import com.amin.waterpipe.model.constances.Maps;
import com.amin.waterpipe.model.entities.Map;
import com.amin.waterpipe.model.entities.pipe.Block;
import com.amin.waterpipe.model.entities.pipe.NormalPipe;
import com.amin.waterpipe.model.enums.PipeType;
import com.amin.waterpipe.model.valueobjects.Coordinate;

public class LevelOne extends BaseLevel {

    public LevelOne() {
        super();
    }

    @Override
    protected Map mapInitialization() {
        int width = Maps.LevelOneMapTemplate.length; // First dim
        int height = Maps.LevelOneMapTemplate[0].length; // Second dim
        var blocks = new Block[width * height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                var pipeType = Maps.LevelOneMapTemplate[i][j];
                var pipe = pipeType != 0 ? new NormalPipe(PipeType.values()[pipeType], new Coordinate(i, j)) : null;
                blocks[width * i + j] = new Block(new Coordinate(i, j), pipe);
            }
        }
        return new Map(width, height, blocks);
    }

    public Map getMap() {
        return this._map;
    }
}
