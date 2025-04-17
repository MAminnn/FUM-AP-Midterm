package com.amin.waterpipe.common;

import com.amin.waterpipe.model.entities.pipe.BasePipe;
import com.amin.waterpipe.model.valueobjects.Coordinate;

public class PipeTraverseException extends Exception {
    BasePipe _pipe;
    Coordinate _inputCoordinate;

    public PipeTraverseException(BasePipe pipe, Coordinate inputCoordinate) {
        _pipe = pipe;
        _inputCoordinate = inputCoordinate;
    }

    public BasePipe getPipe() {
        return this._pipe;
    }

    public Coordinate getInputCoordinate() {
        return this._inputCoordinate;
    }
}
