package com.amin.waterpipe.view.components;

import javafx.scene.Cursor;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.Objects;

public class StaticPipe extends Pane {

    public StaticPipe(String path) {
        super();
        var pipeImg = new ImageView(Objects.requireNonNull(MapComponent.class.getResource(path)).toString());

        pipeImg.fitWidthProperty().bind(this.widthProperty());
        pipeImg.fitHeightProperty().bind(this.heightProperty());
        pipeImg.setPreserveRatio(true); // Maintain aspect ratio

        pipeImg.setCursor(Cursor.DEFAULT);

        this.getChildren().add(pipeImg);
    }
}

