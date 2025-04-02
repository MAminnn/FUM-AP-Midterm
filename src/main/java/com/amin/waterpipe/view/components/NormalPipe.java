package com.amin.waterpipe.view.components;

import javafx.animation.RotateTransition;
import javafx.animation.Transition;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.util.Objects;

public class NormalPipe extends Pane {

    public NormalPipe(String path) {
        super();
        var pipeImg = new ImageView(Objects.requireNonNull(MapComponent.class.getResource(path)).toString());

        pipeImg.fitWidthProperty().bind(this.widthProperty());
        pipeImg.fitHeightProperty().bind(this.heightProperty());
        pipeImg.setPreserveRatio(true); // Maintain aspect ratio

        pipeImg.setCursor(Cursor.HAND);

        this.getChildren().add(pipeImg);
    }
}
