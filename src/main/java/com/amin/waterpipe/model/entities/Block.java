package com.amin.waterpipe.model.entities;

import com.amin.waterpipe.model.entities.pipe.BasePipe;
import com.amin.waterpipe.model.valueobjects.Coordinate;

public class Block {
    private final Coordinate _coordinate;
    private BasePipe _pipe;

    public Block(Coordinate coordinate, BasePipe pipe) {
        _coordinate = coordinate;
        _pipe = pipe;
    }

    public Coordinate getCoordinate() {
        return this._coordinate;
    }

    public BasePipe get_pipe() {
        return _pipe;
    }

    public void setPipe(BasePipe pipe) {
        this._pipe = pipe;
    }

    public static Block copy(Block block) {
        return new Block(new Coordinate(block.getCoordinate().x(), block._coordinate.y()),
                block.get_pipe() != null ? BasePipe.copy(block.get_pipe()) : null);
    }
}
