package com.amin.waterpipe.model.entities.pipe;

import com.amin.waterpipe.model.valueobjects.Coordinate;

public class Block {
    private final Coordinate _coordinate;
    private final BasePipe _pipe;

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

    //    public Block Left() {
//        return new Block(new Coordinate(this._coordinate.x() - 1, this._coordinate.y()));
//    }
//
//    public Block Right() {
//        return new Block(new Coordinate(this._coordinate.x() + 1, this._coordinate.y()));
//    }
//
//    public Block Up() {
//        return new Block(new Coordinate(this._coordinate.x(), this._coordinate.y() + 1));
//    }
//
//    public Block Down() {
//        return new Block(new Coordinate(this._coordinate.x(), this._coordinate.y() - 1));
//    }
}
