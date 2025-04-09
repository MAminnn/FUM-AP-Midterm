package com.amin.waterpipe.model.entities.level;

import com.amin.waterpipe.model.entities.Map;
import com.amin.waterpipe.model.entities.pipe.BasePipe;
import com.amin.waterpipe.model.entities.pipe.Block;
import com.amin.waterpipe.model.entities.pipe.IOPipe;
import com.amin.waterpipe.model.entities.pipe.NormalPipe;
import com.amin.waterpipe.model.enums.PipeType;
import com.amin.waterpipe.model.valueobjects.Coordinate;

public abstract class BaseLevel {

    protected final Map _map;

    protected Map mapInitialization(int[][] mapTemplate) {
        int width = mapTemplate.length; // First dim
        int height = mapTemplate[0].length; // Second dim
        var blocks = new Block[width * height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                var pipeType = mapTemplate[i][j];
                BasePipe pipe;
                if (pipeType == 7 || pipeType == 8) {
                    pipe = new IOPipe(PipeType.values()[pipeType], new Coordinate(i, j));
                } else if (pipeType != 0) {
                    pipe = new NormalPipe(PipeType.values()[pipeType], new Coordinate(i, j));
                } else {
                    pipe = null;
                }

                blocks[width * i + j] = new Block(new Coordinate(i, j), pipe);
            }
        }
        return new Map(width, height, blocks);
    }

    public BaseLevel(int[][] mapTemplate) {
        this._map = mapInitialization(mapTemplate);
    }


}
