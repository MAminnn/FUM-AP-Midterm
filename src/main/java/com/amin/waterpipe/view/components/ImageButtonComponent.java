package com.amin.waterpipe.view.components;

import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.effect.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;

public class ImageButtonComponent extends ImageView {

    public ImageButtonComponent(double size, String imagePath, ObservableValue<? extends Number> widthProperty,
                                ObservableValue<? extends Number> heightProperty,
                                EventHandler<? super MouseEvent> onMouseClicked) {
        super(new Image(imagePath));


        if (size == -1.0) {
            fitWidthProperty().bind(widthProperty);
            fitHeightProperty().bind(heightProperty);

        } else {
            setFitWidth(size);
            setFitHeight(size);
        }

        setPreserveRatio(true);

        setCursor(Cursor.HAND);

        setOnMouseClicked(onMouseClicked);

        setOnMouseEntered((e) -> {
            Rectangle gradient = new Rectangle(this.getFitWidth(), this.getFitHeight());
            gradient.setFill(new RadialGradient(
                    0, 0,
                    0.5, 0.5,   // Center (50%, 50%)
                    0.5,        // Radius (50%)
                    true,
                    CycleMethod.NO_CYCLE,
                    new Stop(0, Color.color(1, 1, 1, 0.5)), // Brighter center
                    new Stop(1, Color.color(1, 1, 1, 0))    // Transparent edges
            ));

            // Use Blend effect to overlay gradient onto the image
            Blend blend = new Blend(
                    BlendMode.ADD,
                    null,
                    new ColorInput(0, 0, this.getFitWidth(), this.getFitHeight(), gradient.getFill())
            );
            this.setEffect(blend);
        });
        setOnMouseExited((e) -> {
            this.setEffect(null);
        });
    }
}
