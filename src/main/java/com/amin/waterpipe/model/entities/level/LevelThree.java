package com.amin.waterpipe.model.entities.level;

import com.amin.waterpipe.model.data.Maps;
import com.amin.waterpipe.model.entities.Map;
import com.amin.waterpipe.model.entities.pipe.BasePipe;
import com.amin.waterpipe.model.entities.Block;
import com.amin.waterpipe.model.entities.pipe.IOPipe;
import com.amin.waterpipe.model.entities.pipe.NormalPipe;
import com.amin.waterpipe.model.enums.PipeType;
import com.amin.waterpipe.model.valueobjects.Coordinate;

public class LevelThree implements BaseLevel {

    private final Map _map;

    private final int dimension = 0;

    public LevelThree() {
        _map = mapInitialization();
    }

    private Map mapInitialization() {
        int[][] mapTemplate = Maps.LevelThreeMapSeed;
        int width = mapTemplate.length; // First dim
        int height = mapTemplate[0].length; // Second dim
        var blocks = new Block[width * height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                var pipeType = mapTemplate[i][j];
                BasePipe pipe;
                if (pipeType == 7 || pipeType == 8) {
                    pipe = new IOPipe(PipeType.values()[pipeType], new Coordinate(j, width - 1 - i));
                } else if (pipeType != 0) {
                    pipe = new NormalPipe(PipeType.values()[pipeType], new Coordinate(j, width - 1 - i));
                } else {
                    pipe = null;
                }

                blocks[width * i + j] = new Block(new Coordinate(j, width - 1 - i), pipe);
            }
        }
        return new Map(width, height, blocks);
    }

    public Map getMap() {
        return this._map;
    }
}
