package com.amin.waterpipe.view.components;

import com.amin.waterpipe.model.entities.pipe.BasePipe;
import javafx.scene.Cursor;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.Objects;

public class IOPipeComponent extends Pane {

    public IOPipeComponent(BasePipe pipeEntity) {
        super();
        var path = "/images/pipes/" + pipeEntity.getPipeType() + ".png";
        var pipeImg = new ImageView(Objects.requireNonNull(MapComponent.class.getResource(path)).toString());

        pipeImg.fitWidthProperty().bind(this.widthProperty());
        pipeImg.fitHeightProperty().bind(this.heightProperty());
        pipeImg.setPreserveRatio(false); // Maintain aspect ratio

        pipeImg.setCursor(Cursor.DEFAULT);

        this.getChildren().add(pipeImg);
    }
}

