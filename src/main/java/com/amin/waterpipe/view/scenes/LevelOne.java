package com.amin.waterpipe.view.scenes;

import com.amin.waterpipe.model.constances.Maps;
import com.amin.waterpipe.model.entities.pipe.Block;
import com.amin.waterpipe.model.entities.pipe.NormalPipe;
import com.amin.waterpipe.model.enums.PipeType;
import com.amin.waterpipe.model.valueobjects.Coordinate;
import com.amin.waterpipe.view.components.MapComponent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;

import java.util.ArrayList;
import java.util.Objects;

public class LevelOne extends Scene {

    private ArrayList<Block> _blocks;
    private static LevelOne _instance;

    private LevelOne(Parent parent, double width, double height, Paint fill) {
        super(parent, width, height, fill);
    }

    private static ArrayList<Block> loadBlocks() {
        int[][] levelOneMapTemplate = Maps.LevelOneMapTemplate;
        var blocks = new ArrayList<Block>();
        for (int i = 0; i < levelOneMapTemplate.length; i++) {
            for (int j = 0; j < levelOneMapTemplate[0].length; j++) {
                blocks.add(new Block(
                        new Coordinate(i, j),
                        levelOneMapTemplate[i][j] == 0 ? null :
                                new NormalPipe(PipeType.values()[levelOneMapTemplate[i][j]], new Coordinate(i, j))
                ));
            }
        }
        return blocks;
    }

    public static LevelOne getScene() {
        if (_instance == null) {
            var root = new VBox();
            var background = new BackgroundImage(new Image(Objects.requireNonNull(LevelOne.class.getResource("/images/background.jpg")).toString()), BackgroundRepeat.NO_REPEAT,
                    BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                    new BackgroundSize(root.getWidth(), root.getHeight(), true, true, true, true));
            root.setBackground(new Background(background));

            var blocks = loadBlocks();
            var map = new MapComponent(blocks);
            root.getChildren().add(map);
            root.setPadding(new Insets(50, 40, 50, 40));
            _instance = new LevelOne(root, 1280, 768, new ImagePattern(new Image(
                    Objects.requireNonNull(LevelOne.class.getResource("/images/background.jpg")).toString())
            ));
            _instance._blocks = blocks;
        }
        return _instance;
    }
}
