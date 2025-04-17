package com.amin.waterpipe.controller;

import com.amin.waterpipe.model.entities.Map;
import com.amin.waterpipe.model.entities.level.LevelTwo;
import com.amin.waterpipe.model.entities.Block;
import com.amin.waterpipe.model.entities.pipe.NormalPipe;
import com.amin.waterpipe.model.enums.PipeType;

import java.util.Arrays;
import java.util.Random;

public class LevelTwoController {

    private final com.amin.waterpipe.model.entities.level.LevelTwo _model;
    private Map _correctMap;
    private final Block[] _blocks;


    public LevelTwoController() {
        this._model = new LevelTwo();
        this._blocks = loadBlocks();
        this.randomInitialization();
    }

    public Block[] getBlocks() {
        return this._blocks;
    }

    public int getMovesLimit() {
        return this._model.numberOfMoves;
    }

    public boolean isPositionWinning() {
        return Arrays.stream(_blocks).allMatch(b ->
        {
            if (b.get_pipe() == null ||
                    _correctMap.getBlock(b.getCoordinate()).get_pipe() == null) {
                return true;
            }
            return b.get_pipe().getPipeType().equals(_correctMap.getBlock(b.getCoordinate()).get_pipe().getPipeType());

        });
    }

    private Block[] loadBlocks() {
        var map = this._model.getMap();
        var originalBlocks = new Block[map.getBlocks().length];
        for (int i = 0; i < originalBlocks.length; i++) {
            originalBlocks[i] = Block.copy(map.getBlocks()[i]);
        }
        this._correctMap = new Map(map.Width, map.Height, originalBlocks);
        return map.getBlocks();
    }

    private void randomInitialization() {
        Random random = new Random();
        for (int i = 0; i < this._blocks.length; i++) {
            var block = this._blocks[i];
            if (block.get_pipe() instanceof NormalPipe) {
                var rotationsCount = random.nextInt(4);
                for (int j = 0; j < rotationsCount; j++) {
                    ((NormalPipe) block.get_pipe()).rotateClockWise();
                }
            } else if (block.get_pipe() == null) {
                if (random.nextFloat() <= 0.5) {
                    var rotationsCount = random.nextInt(4);
                    this._blocks[i] = new Block(block.getCoordinate(), new NormalPipe(random.nextBoolean() ? PipeType.RIGHT_UP : PipeType.HORIZONTAL,
                            block.getCoordinate()
                    ));
                    for (int j = 0; j < rotationsCount; j++) {
                        ((NormalPipe) this._blocks[i].get_pipe()).rotateClockWise();
                    }
                }
            }
        }
    }
}
