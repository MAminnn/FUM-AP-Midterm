package com.amin.waterpipe.model.entities.pipe;

import com.amin.waterpipe.model.enums.PipeType;
import com.amin.waterpipe.model.valueobjects.Coordinate;

public class NormalPipe extends BasePipe {

    public NormalPipe(PipeType pipeType, Coordinate coordinate) {
        super(pipeType, coordinate);
    }

    public void rotateClockWise() {
        if (this._pipeType.equals(PipeType.HORIZONTAL)) {
            this.setPipeType(PipeType.VERTICAL);
        }
        if (this._pipeType.equals(PipeType.VERTICAL)) {
            this.setPipeType(PipeType.HORIZONTAL);
        }
        if (this._pipeType.equals(PipeType.LEFT_UP)) {
            this.setPipeType(PipeType.RIGHT_UP);
        }
        if (this._pipeType.equals(PipeType.RIGHT_UP)) {
            this.setPipeType(PipeType.RIGHT_DOWN);
        }
        if (this._pipeType.equals(PipeType.RIGHT_DOWN)) {
            this.setPipeType(PipeType.LEFT_DOWN);
        }
        if (this._pipeType.equals(PipeType.LEFT_DOWN)) {
            this.setPipeType(PipeType.LEFT_UP);
        }

    }

    public void rotateCounterClockWise() {
        if (this._pipeType.equals(PipeType.HORIZONTAL)) {
            this.setPipeType(PipeType.VERTICAL);
        }
        if (this._pipeType.equals(PipeType.VERTICAL)) {
            this.setPipeType(PipeType.HORIZONTAL);
        }
        if (this._pipeType.equals(PipeType.RIGHT_UP)) {
            this.setPipeType(PipeType.LEFT_UP);
        }
        if (this._pipeType.equals(PipeType.RIGHT_DOWN)) {
            this.setPipeType(PipeType.RIGHT_UP);
        }
        if (this._pipeType.equals(PipeType.LEFT_DOWN)) {
            this.setPipeType(PipeType.RIGHT_DOWN);
        }
        if (this._pipeType.equals(PipeType.LEFT_UP)) {
            this.setPipeType(PipeType.LEFT_DOWN);
        }

    }

    @Override
    public Coordinate nextCoordinate(Coordinate inputCoordinate) {
        int input_X, input_Y;
        input_X = inputCoordinate.x();
        input_Y = inputCoordinate.y();
        int pipe_X, pipe_Y;
        pipe_X = this.getCoordinate().x();
        pipe_Y = this.getCoordinate().y();
        switch (this.getPipeType()) {
            case VERTICAL -> {
                return input_Y > pipe_Y ? inputCoordinate.Down() : inputCoordinate.Up();
            }
            case HORIZONTAL -> {
                return input_X > pipe_X ? inputCoordinate.Left() : inputCoordinate.Right();
            }
            case RIGHT_UP -> {
                return input_X < pipe_X ? inputCoordinate.Right().Up() : inputCoordinate.Down().Left();
            }
            case LEFT_DOWN -> {
                return input_Y < pipe_Y ? new Coordinate(input_X - 1, input_Y + 1) : new Coordinate(input_X + 1, input_Y - 1);
            }
            case LEFT_UP -> {
                return input_Y > pipe_Y ? new Coordinate(input_X - 1, input_Y - 1) : new Coordinate(input_X + 1, input_Y + 1);
            }
            case RIGHT_DOWN -> {
                return input_Y < pipe_Y ? new Coordinate(input_X + 1, input_Y + 1) : new Coordinate(input_X - 1, input_Y - 1);
            }
            default -> {
                return null;
            }
        }
    }

}
