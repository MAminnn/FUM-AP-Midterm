package com.amin.waterpipe.model.entities;

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

    public Map getSubset(Coordinate Bottom_LeftCoordinate, int width, int height) {
        var blocks = new Block[width * height];

        var tempCoordinate = Bottom_LeftCoordinate;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                blocks[width * i + j] = this.getBlock(tempCoordinate);
                tempCoordinate = tempCoordinate.Up();
            }
            tempCoordinate = new Coordinate(tempCoordinate.x(), Bottom_LeftCoordinate.y());
            tempCoordinate = tempCoordinate.Right();
        }

        return new Map(width, height, blocks);
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
