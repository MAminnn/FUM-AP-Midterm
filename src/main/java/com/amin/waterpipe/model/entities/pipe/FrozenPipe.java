package com.amin.waterpipe.model.entities.pipe;

import com.amin.waterpipe.common.PipeTraverseException;
import com.amin.waterpipe.model.enums.PipeType;
import com.amin.waterpipe.model.valueobjects.Coordinate;

public class FrozenPipe extends BasePipe {
    public FrozenPipe(PipeType pipeType, Coordinate coordinate) {
        super(pipeType, coordinate);
    }

    @Override
    public Coordinate traverse(Coordinate inputCoordinate) throws PipeTraverseException {
        int input_X, input_Y;
        input_X = inputCoordinate.x();
        input_Y = inputCoordinate.y();
        int pipe_X, pipe_Y;
        pipe_X = this.getCoordinate().x();
        pipe_Y = this.getCoordinate().y();
        switch (this.getPipeType()) {
            case VERTICAL -> {
                if (input_X != pipe_X) {
                    throw new PipeTraverseException(this, inputCoordinate);
                }
                return input_Y > pipe_Y ? inputCoordinate.Down().Down() : inputCoordinate.Up().Up();
            }
            case HORIZONTAL -> {
                if (input_Y != pipe_Y) {
                    throw new PipeTraverseException(this, inputCoordinate);
                }
                return input_X > pipe_X ? inputCoordinate.Left().Left() : inputCoordinate.Right().Right();
            }
            case RIGHT_UP -> {
                if (input_Y < pipe_Y || input_X < pipe_X) {
                    throw new PipeTraverseException(this, inputCoordinate);
                }
                return input_X > pipe_X ? inputCoordinate.Left().Up() : inputCoordinate.Down().Right();
            }
            case LEFT_DOWN -> {
                if (input_Y > pipe_Y || input_X > pipe_X) {
                    throw new PipeTraverseException(this, inputCoordinate);
                }
                return input_Y < pipe_Y ? inputCoordinate.Up().Left() : inputCoordinate.Right().Down();
            }
            case LEFT_UP -> {
                if (input_Y < pipe_Y || input_X > pipe_X) {
                    throw new PipeTraverseException(this, inputCoordinate);
                }
                return input_Y > pipe_Y ? inputCoordinate.Down().Left() : inputCoordinate.Right().Up();
            }
            case RIGHT_DOWN -> {
                if (input_Y > pipe_Y || input_X < pipe_X) {
                    throw new PipeTraverseException(this, inputCoordinate);
                }
                return input_Y < pipe_Y ? inputCoordinate.Right().Up() : inputCoordinate.Left().Down();
            }
            default -> {
                return null;
            }
        }
    }

    @Override
    protected BasePipe copy() {
        return new NormalPipe(this.getPipeType(), new Coordinate(this.getCoordinate().x(),
                this.getCoordinate().y()));
    }

}
