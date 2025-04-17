package com.amin.waterpipe.controller;

import com.amin.waterpipe.model.data.Maps;
import com.amin.waterpipe.model.entities.Map;
import com.amin.waterpipe.model.entities.level.LevelThree;
import com.amin.waterpipe.model.entities.Block;
import com.amin.waterpipe.model.entities.pipe.NormalPipe;
import com.amin.waterpipe.model.enums.PipeType;
import com.amin.waterpipe.model.valueobjects.Coordinate;
import com.amin.waterpipe.model.valueobjects.PipeFilter;
import com.amin.waterpipe.services.PipeFilterGenerator;

import java.util.ArrayList;
import java.util.Random;

public class LevelThreeController {

    private Map _currentMap;

    int h = Maps.LevelThreeMapSeed[0].length;
    int w = Maps.LevelThreeMapSeed.length;

    public LevelThreeController() {
        loadBlocks();
        for (int i = 0; i < 100; i++) {
            randomize();
        }

    }

    ArrayList<PipeFilter> filters = new ArrayList<>();

    public void randomize() {
        var blocks = _currentMap.getSubset(new Coordinate(0, h - 2), 2, 2).getBlocks();
        PipeFilterGenerator generator = new PipeFilterGenerator(_currentMap, new PipeFilter(
                2, 2, blocks, blocks[1], new Coordinate(0, h)
        ));


        PipeFilter filter;

        while (true) {
            try {

                Block destinationBlockInFilter = null;

                generator.next();

                for (Block b : generator.getCurrentFilter().getBlocks()) {
                    if (b != null && b.get_pipe() != null) {
                        if (b.get_pipe().getPipeType() == PipeType.HORIZONTAL_DESTINATION) {
                            destinationBlockInFilter = b;
                            break;
                        }
                    }
                }

                if (destinationBlockInFilter != null) {
                    filter = generator.getCurrentFilter().reachToCoordinate(destinationBlockInFilter.getCoordinate());
                } else {
                    filter = generator.randomizeCurrentFilter();

                }
                for (Block b : filter.getBlocks()) {
                    var mapBlocks = _currentMap.getBlocks();
                    for (int i = 0; i < mapBlocks.length; i++) {
                        if (mapBlocks[i].getCoordinate().equals(b.getCoordinate())) {
                            mapBlocks[i] = b;
                            break;
                        }
                    }
                }
                if (destinationBlockInFilter != null) {
                    return;
                }


            } catch (Exception e) {
                break;
            }
        }
    }

    public Block[] getBlocks() {
        return this._currentMap.getBlocks();
    }

    private void loadBlocks() {
        var model = new LevelThree();
        var map = model.getMap();
        var copyBlocks = new Block[map.getBlocks().length];
        for (int i = 0; i < copyBlocks.length; i++) {
            copyBlocks[i] = Block.copy(map.getBlocks()[i]);
        }
        this._currentMap = new Map(map.Width, map.Height, copyBlocks);
    }

    public boolean isPositionWinning() {
        return false;
    }

    private void randomInitialization() {
        Random random = new Random();
        var blocks = _currentMap.getBlocks();
        for (int i = 0; i < blocks.length; i++) {
            var block = blocks[i];
            if (block.get_pipe() instanceof NormalPipe) {
                var rotationsCount = random.nextInt(4);
                for (int j = 0; j < rotationsCount; j++) {
                    ((NormalPipe) block.get_pipe()).rotateClockWise();
                }
            } else if (block.get_pipe() == null) {
                if (random.nextFloat() <= 0.5) {
                    var rotationsCount = random.nextInt(4);
                    blocks[i] = new Block(block.getCoordinate(), new NormalPipe(random.nextBoolean() ? PipeType.RIGHT_UP : PipeType.HORIZONTAL,
                            block.getCoordinate()
                    ));
                    for (int j = 0; j < rotationsCount; j++) {
                        ((NormalPipe) blocks[i].get_pipe()).rotateClockWise();
                    }
                }
            }
        }
    }

}
