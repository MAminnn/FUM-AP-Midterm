package com.amin.waterpipe.model.entities.pipe;

import com.amin.waterpipe.model.enums.PipeType;
import com.amin.waterpipe.model.valueobjects.Coordinate;

public class IOPipe extends BasePipe {


    public IOPipe(PipeType pipeType, Coordinate coordinate) {
        super(pipeType, coordinate);
    }

    @Override
    public Coordinate nextCoordinate(Coordinate inputCoordinate) {
        return null;
    }

    @Override
    protected BasePipe copy() {
        return new IOPipe(this.getPipeType(), new Coordinate(this.getCoordinate().x(),
                this.getCoordinate().y()));
    }
}
