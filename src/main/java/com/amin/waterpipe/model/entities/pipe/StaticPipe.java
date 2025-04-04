package com.amin.waterpipe.model.entities.pipe;

import com.amin.waterpipe.model.enums.PipeType;
import com.amin.waterpipe.model.valueobjects.Coordinate;

public class StaticPipe extends BasePipe {


    public StaticPipe(PipeType pipeType, Coordinate coordinate) {
        super(pipeType, coordinate);
    }

    @Override
    public Coordinate nextCoordinate(Coordinate inputCoordinate) {
        return null;
    }

    @Override
    protected BasePipe copy() {
        return new StaticPipe(this.getPipeType(), new Coordinate(this.getCoordinate().x(),
                this.getCoordinate().y()));
    }
}
