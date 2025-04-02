package com.amin.waterpipe.view.components;

import com.amin.waterpipe.model.entities.pipe.Block;
import com.amin.waterpipe.model.enums.PipeType;
import javafx.animation.Transition;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.util.ArrayList;
import java.util.stream.Stream;

public class MapComponent extends GridPane {


    public MapComponent(ArrayList<Block> blocks) {

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
                var pipeImgPath = "/images/pipes/" + block.get_pipe().getPipeType().toString() + ".png";
                Node pipe;
                if (block.get_pipe().getPipeType().equals(PipeType.DESTINATION) ||
                        block.get_pipe().getPipeType().equals(PipeType.ORIGIN)) {
                    pipe = new StaticPipe(pipeImgPath);
                } else {
                    pipe = new NormalPipe(pipeImgPath);
                }
                this.add(pipe, block.getCoordinate().y(), block.getCoordinate().x());
            }
        }


//        var demo = "/images/pipes/" + blocks.get(0).get_pipe().getPipeType().toString() + ".png";
//        var pipeImg = new ImageView(MapComponent.class.getResource(demo).toString());
//        pipeImg.setPreserveRatio(true);
//        var pane = new Pane();
//
//
////        pipeImg.fitWidthProperty().bind(pane.widthProperty());
////        pipeImg.fitHeightProperty().bind(pane.heightProperty());
////        pipeImg.setPreserveRatio(true);
//
//        pane.getChildren().add(pipeImg);
//        this.add(pane, blocks.get(0).getCoordinate().y(), blocks.get(0).getCoordinate().x(), 1, 1);
//
//        demo = "/images/pipes/" + blocks.get(5).get_pipe().getPipeType().toString() + ".png";
//        pipeImg = new ImageView(MapComponent.class.getResource(demo).toString());
//        pipeImg.setPreserveRatio(true);
//        pane = new Pane();
//
//
////        pipeImg.fitWidthProperty().bind(pane.widthProperty());
////        pipeImg.fitHeightProperty().bind(pane.heightProperty());
////        pipeImg.setPreserveRatio(true);
//
//        pane.getChildren().add(pipeImg);
//        this.add(pane, blocks.get(5).getCoordinate().y(), blocks.get(5).getCoordinate().x(), 1, 1);
//
//        demo = "/images/pipes/" + blocks.get(6).get_pipe().getPipeType().toString() + ".png";
//        pipeImg = new ImageView(MapComponent.class.getResource(demo).toString());
//        pane = new Pane();
//
//        pane.getChildren().add(pipeImg);
//        this.add(pane, 1, 1, 1, 1);
    }
}
