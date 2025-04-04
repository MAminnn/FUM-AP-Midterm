package com.amin.waterpipe.model.entities.pipe;

import com.amin.waterpipe.model.enums.PipeType;
import com.amin.waterpipe.model.valueobjects.Coordinate;

public abstract class BasePipe {
    protected PipeType _pipeType;
    protected final Coordinate _coordinate;

    public BasePipe(PipeType pipeType, Coordinate coordinate) {
        this._pipeType = pipeType;
        this._coordinate = coordinate;
    }

    protected void setPipeType(PipeType pipeType) {
        this._pipeType = pipeType;
    }

    public PipeType getPipeType() {
        return _pipeType;
    }

    public Coordinate getCoordinate() {
        return _coordinate;
    }

    public abstract Coordinate nextCoordinate(Coordinate inputCoordinate);

    protected abstract BasePipe copy();

    public static BasePipe copy(BasePipe pipe) {
        return pipe.copy();
    }
}
