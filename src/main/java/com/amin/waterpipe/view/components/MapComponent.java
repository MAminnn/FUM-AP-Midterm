package com.amin.waterpipe.view.components;

import com.amin.waterpipe.model.entities.Block;
import com.amin.waterpipe.model.entities.pipe.FrozenPipe;
import com.amin.waterpipe.model.entities.pipe.IOPipe;
import com.amin.waterpipe.model.entities.pipe.NormalPipe;
import com.amin.waterpipe.model.enums.PipeType;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import java.util.ArrayList;


public class MapComponent extends GridPane {

    private final ArrayList<Block> _blocks;
    private Runnable _winCheckHandler;

    public MapComponent(ArrayList<Block> blocks, ObservableValue<? extends Number> maxWidthProperty,
                        ObservableValue<? extends Number> maxHeightProperty) {

        this._blocks = blocks;

        maxWidthProperty().bind(maxWidthProperty);
        maxHeightProperty().bind(maxHeightProperty);

        this.setVgap(0);
        this.setHgap(0);

        setAlignment(Pos.CENTER);

        int colsNumber = blocks.stream().mapToInt(b -> b.getCoordinate().x()).max().orElse(0) + 1;
        int rowsNumber = blocks.stream().mapToInt(b -> b.getCoordinate().y()).max().orElse(0) + 1;

        for (int i = 0; i < colsNumber; i++) {
            ColumnConstraints cc = new ColumnConstraints();
            getColumnConstraints().add(cc);
        }

        for (int i = 0; i < rowsNumber; i++) {
            RowConstraints rc = new RowConstraints();
            getRowConstraints().add(rc);
        }

        heightProperty().addListener((obs, oldVal, newVal) -> {
            getColumnConstraints().forEach(cc -> {
                cc.setMaxWidth(getHeight() / rowsNumber);
            });
        });

        for (Block block : blocks) {
            if (block.get_pipe() != null) {

                Node pipe;
                if (block.get_pipe() instanceof IOPipe) {
                    pipe = new IOPipeComponent((IOPipe) block.get_pipe());
                } else if (block.get_pipe() instanceof NormalPipe) {
                    pipe = new NormalPipeComponent((NormalPipe) block.get_pipe());
                } else //block.get_pipe() instanceof FrozenPipe)
                {
                    pipe = new FrozenPipeComponent((FrozenPipe) block.get_pipe());
                }
                this.add(pipe, block.getCoordinate().x(), rowsNumber - block.getCoordinate().y());
            }
        }
    }

    public void setRotationEventHandler(Runnable winCheckHandler) {
        this._winCheckHandler = winCheckHandler;
        this.getChildren().forEach(p -> {
            if (p instanceof NormalPipeComponent) {
                ((NormalPipeComponent) p).setRotationEventHandler(_winCheckHandler);
            }
        });

    }

    public void stopAllAnimations() {
        this.getChildren().forEach(p -> {
            if (p instanceof NormalPipeComponent) {
                ((NormalPipeComponent) p).stopAnimation();
            }
        });
    }
}
