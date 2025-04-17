package com.amin.waterpipe.controller;

import com.amin.waterpipe.model.entities.Map;
import com.amin.waterpipe.model.entities.level.LevelOne;
import com.amin.waterpipe.model.entities.Block;
import com.amin.waterpipe.model.entities.pipe.NormalPipe;
import com.amin.waterpipe.model.enums.PipeType;

import java.util.Arrays;
import java.util.Random;

public class LevelOneController {
    private final com.amin.waterpipe.model.entities.level.LevelOne _model;
    private Map _correctMap;
    //    private final Block[] _blocks;
    private Map _currentMap;

    public LevelOneController() {
        this._model = new LevelOne();
//        this._blocks = loadBlocks();
        this.loadBlocks();
//        this.randomInitialization();
    }

    public Block[] getBlocks() {
        return this._currentMap.getBlocks();
    }

    private void loadBlocks() {
        var map = this._model.getMap();
        var copyBlocks = new Block[map.getBlocks().length];
        for (int i = 0; i < copyBlocks.length; i++) {
            copyBlocks[i] = Block.copy(map.getBlocks()[i]);
        }
        this._correctMap = map;
        this._currentMap = new Map(_correctMap.Width, _correctMap.Height, copyBlocks);
    }

    public boolean isPositionWinning() {

        var w = _correctMap.Width;
        var h = _correctMap.Height;

        return Arrays.stream(this._currentMap.getBlocks()).allMatch(b ->
        {
            if (b.get_pipe() == null ||
                    _correctMap.getBlock(b.getCoordinate()).get_pipe() == null) {
                return true;
            }
            return b.get_pipe().getPipeType().equals(_correctMap.getBlock(b.getCoordinate()).get_pipe().getPipeType());

        });
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
