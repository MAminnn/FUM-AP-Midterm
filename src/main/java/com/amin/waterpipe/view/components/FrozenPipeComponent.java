package com.amin.waterpipe.view.components;

import com.amin.waterpipe.model.entities.pipe.FrozenPipe;
import com.amin.waterpipe.model.entities.pipe.NormalPipe;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.scene.Cursor;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.util.Objects;

public class FrozenPipeComponent extends Pane {

    private final FrozenPipe _pipe;

    public FrozenPipeComponent(FrozenPipe pipeEntity) {
        super();
        this._pipe = pipeEntity;

        var path = "/images/pipes/" + pipeEntity.getPipeType() + ".png";
        var pipeImg = new ImageView(Objects.requireNonNull(this.getClass().getResource(path)).toString());

        pipeImg.fitWidthProperty().bind(this.widthProperty());
        pipeImg.fitHeightProperty().bind(this.heightProperty());
        pipeImg.setPreserveRatio(true); // Maintain aspect ratio

        pipeImg.setCursor(Cursor.DEFAULT);

        this.getChildren().add(pipeImg);

    }

}
