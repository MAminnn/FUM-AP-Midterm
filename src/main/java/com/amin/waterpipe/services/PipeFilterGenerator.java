package com.amin.waterpipe.services;

import com.amin.waterpipe.common.PipeTraverseException;
import com.amin.waterpipe.model.entities.Map;
import com.amin.waterpipe.model.entities.pipe.BasePipe;
import com.amin.waterpipe.model.entities.Block;
import com.amin.waterpipe.model.entities.pipe.NormalPipe;
import com.amin.waterpipe.model.enums.PipeType;
import com.amin.waterpipe.model.valueobjects.Coordinate;
import com.amin.waterpipe.model.valueobjects.PipeFilter;
import javafx.util.Pair;

import java.util.Random;

public class PipeFilterGenerator {

    private PipeFilter _currentFilter;
    private Map _map;

    public PipeFilterGenerator(Map map, PipeFilter startingFilter) {
        this._currentFilter = startingFilter;
        this._map = map;
    }

    public PipeFilter randomizeCurrentFilter() {
        PipeFilter randomFilter;
        boolean isFilterReplaceable;


        Pair<Coordinate, Coordinate> trueOutput = null;

        boolean isTrueFilterBroken = false;
        int pipeTypeTest = 1;
        BasePipe latestBrokenPipe = null;
        do {
            try {
                trueOutput = _currentFilter.getFilterOutput();
                isTrueFilterBroken = false;

                if (trueOutput.getValue().x() < 0 ||
                        trueOutput.getValue().x() >= _map.Width ||
                        trueOutput.getValue().y() < 0 ||
                        trueOutput.getValue().y() >= _map.Height) {
                    throw new PipeTraverseException(_currentFilter.getBlock(trueOutput.getKey()).get_pipe()
                            , trueOutput.getKey());
                }
            } catch (PipeTraverseException e) {

//                try {
//                    var demo = _currentFilter.getFilterOutput();
//                } catch (PipeTraverseException e2) {
//
//                }

                // The true filter is broken, and it has to be fixed
                isTrueFilterBroken = true;

                var brokenPipe = _currentFilter.getBlock(e.getPipe().getCoordinate()).get_pipe();
                if (brokenPipe == null) {
                    brokenPipe = new NormalPipe(PipeType.values()[pipeTypeTest], e.getPipe().getCoordinate());
                }
                if (latestBrokenPipe != null && !latestBrokenPipe.getCoordinate().equals(brokenPipe.getCoordinate())) {
                    pipeTypeTest = 1;
                }
                latestBrokenPipe = brokenPipe;
                if (brokenPipe instanceof NormalPipe) {
                    brokenPipe = new NormalPipe(PipeType.values()[pipeTypeTest], brokenPipe.getCoordinate());
                    _currentFilter.updatePipe(brokenPipe);
                }

                pipeTypeTest++;

            }
        }
        while (isTrueFilterBroken);


        do {
            Block[] randomBlocks = new Block[4];
            Random random = new Random();
            for (int i = 0; i < randomBlocks.length; i++) {
                var rotationsCount = random.nextInt(4);
                randomBlocks[i] = new Block(_currentFilter.getBlocks()[i].getCoordinate(), new NormalPipe(random.nextBoolean() ? PipeType.RIGHT_UP : PipeType.HORIZONTAL,
                        _currentFilter.getBlocks()[i].getCoordinate()
                ));
                for (int j = 0; j < rotationsCount; j++) {
                    ((NormalPipe) randomBlocks[i].get_pipe()).rotateClockWise();
                }
            }
            randomFilter = new PipeFilter(2, 2, randomBlocks, _currentFilter.getInput(),
                    _currentFilter.getInputCoordinate());
            isFilterReplaceable = false;


            try {
//                randomFilter = new PipeFilter(2, 2, new Block[]{
//                        new Block(new Coordinate(0,4), new NormalPipe(PipeType.LEFT_UP,new Coordinate(0,4))),
//                        new Block(new Coordinate(1,4), new NormalPipe(PipeType.VERTICAL,new Coordinate(0,4))),
//                        new Block(new Coordinate(1,3), new NormalPipe(PipeType.HORIZONTAL,new Coordinate(0,4))),
//                        new Block(new Coordinate(0,3), new NormalPipe(PipeType.LEFT_UP,new Coordinate(0,4))),
//                }, _currentFilter.getInput(),
//                        _currentFilter.getInputCoordinate());
                isFilterReplaceable = randomFilter.getFilterOutput().equals(trueOutput);

            } catch (Exception e) {

            }
        } while (!isFilterReplaceable);

        _currentFilter = randomFilter;
        return randomFilter;
    }

    public PipeFilter getCurrentFilter() {
        return _currentFilter;
    }

    public void next() {
        Block[] blocks = new Block[4];

        Pair<Coordinate, Coordinate> output = null;
        try {
            output = _currentFilter.getNextOutput(); // key is next pipe input & value is next pipe coordinate
        } catch (Exception e) {

        }

        if (_currentFilter.getBlock(output.getValue()) == null) {
            var bottomLeft = output.getValue();
            blocks[0] = _map.getBlock(bottomLeft);
            blocks[1] = _map.getBlock(bottomLeft.Up());
            blocks[2] = _map.getBlock(bottomLeft.Right());
            blocks[3] = _map.getBlock(bottomLeft.Right().Up());
        } else {
            if (output.getValue().x() == output.getKey().x()) {
                if (output.getValue().equals(_currentFilter.getTOP_RIGHT().getCoordinate())) {
                    var bottomLeft = output.getValue().Up();
                    blocks[0] = _map.getBlock(bottomLeft);
                    blocks[1] = _map.getBlock(bottomLeft.Up());
                    blocks[2] = _map.getBlock(bottomLeft.Right());
                    blocks[3] = _map.getBlock(bottomLeft.Right().Up());
                } else if (output.getValue().equals(_currentFilter.getBOTTOM_LEFT().getCoordinate())) {
                    var bottomLeft = output.getValue().y() == 0 ? output.getValue().Right() : output.getValue().Down();
                    blocks[0] = _map.getBlock(bottomLeft);
                    blocks[1] = _map.getBlock(bottomLeft.Up());
                    blocks[2] = _map.getBlock(bottomLeft.Right());
                    blocks[3] = _map.getBlock(bottomLeft.Right().Up());
                } else if (output.getValue().equals(_currentFilter.getBOTTOM_RIGHT().getCoordinate())) {
                    var bottomLeft = output.getValue().Up();
                    blocks[0] = _map.getBlock(bottomLeft);
                    blocks[1] = _map.getBlock(bottomLeft.Up());
                    blocks[2] = _map.getBlock(bottomLeft.Right());
                    blocks[3] = _map.getBlock(bottomLeft.Right().Up());
                } else if (output.getValue().equals(_currentFilter.getTOP_LEFT().getCoordinate())) {
                    var bottomLeft = output.getValue();
                    blocks[0] = _map.getBlock(bottomLeft);
                    blocks[1] = _map.getBlock(bottomLeft.Up());
                    blocks[2] = _map.getBlock(bottomLeft.Right());
                    blocks[3] = _map.getBlock(bottomLeft.Right().Up());
                }
            } else if (output.getValue().y() == output.getKey().y()) {
                if (output.getValue().equals(_currentFilter.getTOP_RIGHT().getCoordinate())) {
                    var bottomLeft = output.getValue().Down();
                    blocks[0] = _map.getBlock(bottomLeft);
                    blocks[1] = _map.getBlock(bottomLeft.Up());
                    blocks[2] = _map.getBlock(bottomLeft.Right());
                    blocks[3] = _map.getBlock(bottomLeft.Right().Up());
                } else if (output.getValue().equals(_currentFilter.getBOTTOM_LEFT().getCoordinate())) {
                    var bottomLeft = output.getValue().Right().Right();
                    blocks[0] = _map.getBlock(bottomLeft);
                    blocks[1] = _map.getBlock(bottomLeft.Up());
                    blocks[2] = _map.getBlock(bottomLeft.Right());
                    blocks[3] = _map.getBlock(bottomLeft.Right().Up());
                } else if (output.getValue().equals(_currentFilter.getBOTTOM_RIGHT().getCoordinate())) {
                    var bottomLeft = output.getValue();
                    blocks[0] = _map.getBlock(bottomLeft);
                    blocks[1] = _map.getBlock(bottomLeft.Up());
                    blocks[2] = _map.getBlock(bottomLeft.Right());
                    blocks[3] = _map.getBlock(bottomLeft.Right().Up());
                } else if (output.getValue().equals(_currentFilter.getTOP_LEFT().getCoordinate())) {
                    var bottomLeft = output.getValue().Right().Right().Down();
                    blocks[0] = _map.getBlock(bottomLeft);
                    blocks[1] = _map.getBlock(bottomLeft.Up());
                    blocks[2] = _map.getBlock(bottomLeft.Right());
                    blocks[3] = _map.getBlock(bottomLeft.Right().Up());
                }
            } else {
                var bottomLeft = output.getValue();
                blocks[0] = _map.getBlock(bottomLeft);
                blocks[1] = _map.getBlock(bottomLeft.Up());
                blocks[2] = _map.getBlock(bottomLeft.Right());
                blocks[3] = _map.getBlock(bottomLeft.Right().Up());
            }
        }

        // if _currentFilter.getTOP_RIGHT().getCoordinate().x() >= _map.Width - 2
//        if (false) {
//
//            var firstBlockOfLineAbove = _map.getBlock(new Coordinate(0, _currentFilter.getBOTTOM_LEFT().getCoordinate().y() - 2));
//            blocks[0] = _map.getBlock(firstBlockOfLineAbove.getCoordinate());
//            blocks[1] = _map.getBlock(firstBlockOfLineAbove.getCoordinate().Down());
//            blocks[2] = _map.getBlock(firstBlockOfLineAbove.getCoordinate().Right());
//            blocks[3] = _map.getBlock(firstBlockOfLineAbove.getCoordinate().Right().Down());
//        } else {
//            var bottomLeft = output.getValue().Down();
//            blocks[0] = _map.getBlock(bottomLeft);
//            blocks[1] = _map.getBlock(bottomLeft.Up());
//            blocks[2] = _map.getBlock(bottomLeft.Right());
//            blocks[3] = _map.getBlock(bottomLeft.Right().Up());

//            var bottomLeft = _currentFilter.getBOTTOM_LEFT();
//            blocks[0] = _map.getBlock(bottomLeft.getCoordinate().Right().Right());
//            blocks[1] = _map.getBlock(bottomLeft.getCoordinate().Right().Right().Up());
//            blocks[2] = _map.getBlock(bottomLeft.getCoordinate().Right().Right().Right());
//            blocks[3] = _map.getBlock(bottomLeft.getCoordinate().Right().Right().Right().Up());


//        }
        this._currentFilter = new PipeFilter(2, 2, blocks, _map.getBlock(output.getValue()), output.getKey());
    }
}
