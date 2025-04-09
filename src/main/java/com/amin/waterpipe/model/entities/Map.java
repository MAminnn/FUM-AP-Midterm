package com.amin.waterpipe.model.entities;

import com.amin.waterpipe.model.entities.pipe.Block;
import com.amin.waterpipe.model.valueobjects.Coordinate;

public class Map {
    public final int Width;
    public final int Height;

    private final Block[] _blocks;

    public Map(int width, int height, Block[] blocks) {
        this.Width = width;
        this.Height = height;

        this._blocks = blocks;
    }

    public Block getBlock(Coordinate coordinate) {
        for (Block block : _blocks) {
            if (block.getCoordinate().equals(coordinate)) {
                return block;
            }
        }
        return null;
    }

    public Block[] getBlocks() {
        return _blocks;
    }
}
