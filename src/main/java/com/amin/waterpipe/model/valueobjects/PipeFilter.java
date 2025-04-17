package com.amin.waterpipe.model.valueobjects;

import com.amin.waterpipe.common.PipeTraverseException;
import com.amin.waterpipe.model.entities.Map;
import com.amin.waterpipe.model.entities.pipe.BasePipe;
import com.amin.waterpipe.model.entities.Block;
import com.amin.waterpipe.model.entities.pipe.NormalPipe;
import com.amin.waterpipe.model.enums.PipeType;
import javafx.util.Pair;

public class PipeFilter {


    private Block _inputBlock;
    private Coordinate _inputCoordinate;
//    private Block _output;

    private Map _map;

    public Block[] getBlocks() {
        return this._map.getBlocks();
    }

    public Block getTOP_RIGHT() {
        return this._map.getBlocks()[3];
    }

    public Block getBOTTOM_LEFT() {
        return this._map.getBlocks()[0];
    }

    public Block getTOP_LEFT() {
        return this._map.getBlocks()[1];
    }

    public Block getBOTTOM_RIGHT() {
        return this._map.getBlocks()[2];
    }

    public Coordinate getInputCoordinate() {
        return this._inputCoordinate;
    }

    public Block getBlock(Coordinate coordinate) {
        return this._map.getBlock(coordinate);
    }

    public PipeFilter reachToCoordinate(Coordinate coordinateToReach) {

        int pipeTypeIndexer = 1;

        BasePipe pipeBlockToBeChanged;
        while (true) {

            try {

                var block = this._inputBlock;
                var blockInput = this._inputCoordinate;
                var outputCoordinate = block.get_pipe().traverse(blockInput);
                if (outputCoordinate.equals(coordinateToReach)) {
                    return this;
                }
                if (Coordinate.calculateDistance(coordinateToReach, outputCoordinate)
                        >= Coordinate.calculateDistance(coordinateToReach, this._inputCoordinate)) {

                    // A pipe has to be changed

                    pipeBlockToBeChanged = block.get_pipe();
                    this.updatePipe(new NormalPipe(PipeType.values()[pipeTypeIndexer], pipeBlockToBeChanged.getCoordinate()));
                    pipeTypeIndexer++;
                } else {


                    // The current pipe is correct and need to reset the pipeTypeIndexer
                    this._inputBlock = _map.getBlock(outputCoordinate);
                    this._inputCoordinate = block.getCoordinate();
                    pipeTypeIndexer = 1;
                }

            } catch (PipeTraverseException e) {

                this.updatePipe(new NormalPipe(PipeType.values()[pipeTypeIndexer], e.getPipe().getCoordinate()));
                pipeTypeIndexer++;
            }
        }


//        PipeFilter filter = this;
//        boolean flag = true;
//        Coordinate next;
//        int pipeTypeIndexer = 1;
//        while (flag) {
//            try {
//                next = this.getInput().get_pipe().traverse(this._inputCoordinate);
//                if (next.equals(coordinateToReach)) {
//                    return this;
//                }
//                pipeTypeIndexer = 1;
//                while (Coordinate.calculateDistance(next, coordinateToReach) <=
//                        Coordinate.calculateDistance(this.getInputCoordinate(), coordinateToReach)) {
////                    this._inputBlock = new Block(_inputBlock.getCoordinate(), new NormalPipe(PipeType.values()[pipeTypeIndexer],
////                            _inputBlock.getCoordinate()));
//                    next = this.getInput().get_pipe().traverse(this._inputCoordinate);
//                    pipeTypeIndexer++;
//                    if (next == null) {
//                        this.updatePipe(new NormalPipe(PipeType.VERTICAL, next));
//                        pipeTypeIndexer = 0;
//                    }
//                }
//                this._inputBlock = this.getBlock(this._inputBlock.getCoordinate());
//                this.updatePipe(this._inputBlock.get_pipe());
//                flag = false;
//            } catch (PipeTraverseException e) {
//                this._inputBlock = new Block(_inputBlock.getCoordinate(), new NormalPipe(PipeType.values()[pipeTypeIndexer],
//                        _inputBlock.getCoordinate()));
//                pipeTypeIndexer++;
////                this._inputBlock = this.getBlock(this._inputBlock.getCoordinate());
//                this.updatePipe(this._inputBlock.get_pipe());
//            }
//        }
//        return filter;
    }

    public void updatePipe(BasePipe pipe) {
        for (int i = 0; i < _map.getBlocks().length; i++) {
            if (_map.getBlocks()[i].getCoordinate().equals(pipe.getCoordinate())) {
                _map.getBlocks()[i] = new Block(pipe.getCoordinate(), pipe);
            }
        }
    }

    public Pair<Coordinate, Coordinate> getFilterOutput() throws PipeTraverseException {
        int turns = 0;

        var pipeBlock = _map.getBlock(_inputBlock.getCoordinate());
//        var pipeInput = new Coordinate(_inputPipe.getCoordinate().x(), _inputPipe.getCoordinate().y() + 1);
        var pipeInput = _inputCoordinate;
        var nextPipeInput = _inputBlock.getCoordinate();
        var pipeOutput = pipeBlock.get_pipe().traverse(pipeInput);
        turns++;
        nextPipeInput = pipeBlock.getCoordinate();
        while (pipeOutput.x() <= getTOP_RIGHT().getCoordinate().x() &&
                pipeOutput.x() >= getBOTTOM_LEFT().getCoordinate().x() &&
                pipeOutput.y() <= getTOP_RIGHT().getCoordinate().y() &&
                pipeOutput.y() >= getBOTTOM_LEFT().getCoordinate().y()) {

            if (turns == 5) {
                throw new PipeTraverseException(pipeBlock.get_pipe(), nextPipeInput);
            }

            pipeBlock = _map.getBlock(pipeOutput);
            if (pipeBlock.get_pipe() == null) {
                pipeBlock = new Block(pipeBlock.getCoordinate(), new NormalPipe(PipeType.VERTICAL,
                        pipeBlock.getCoordinate()));
            }
            pipeOutput = pipeBlock.get_pipe().traverse(nextPipeInput);
            turns++;
            nextPipeInput = pipeBlock.getCoordinate();
        }
        this.updatePipe(pipeBlock.get_pipe());
        return new Pair<>(nextPipeInput, pipeOutput);
    }

    public Pair<Coordinate, Coordinate> getNextOutput() throws PipeTraverseException {
        var pipe = _map.getBlock(_inputBlock.getCoordinate());
//        var pipeInput = new Coordinate(_inputPipe.getCoordinate().x(), _inputPipe.getCoordinate().y() + 1);
        var pipeInput = _inputCoordinate;
        var nextPipeInput = _inputBlock.getCoordinate();
        var pipeOutput = pipe.get_pipe().traverse(pipeInput);
        nextPipeInput = pipe.getCoordinate();

        _inputCoordinate = nextPipeInput;
        _inputBlock = _map.getBlock(pipeOutput);

        return new Pair<>(nextPipeInput, pipeOutput);
    }


    public Block getInput() {
        return this._inputBlock;
    }

    public PipeFilter(int width, int height, Block[] blocks, Block inputPipe, Coordinate inputCoordinate) {
        _inputBlock = inputPipe;
        _inputCoordinate = inputCoordinate;
//        _output = output;
        _map = new Map(width, height, blocks);
    }
}
