package com.amin.waterpipe.view.scenes;

import com.amin.waterpipe.view.services.SceneManager;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.ArcType;

public class Menu extends Scene {
    private Menu(Parent parent, double width, double height, Paint fill) {
        super(parent, width, height, fill);
    }

    public static Menu initScene() {
        VBox root = new VBox();
        var instance = new Menu(root, 800, 600, Color.DARKGREY);
        var buttonList = new VBox();
        buttonList.setAlignment(Pos.TOP_CENTER);
        var btn = new Button("Enter Level 1");
        btn.setBackground(new Background(new BackgroundFill(
                Color.DARKGREY, CornerRadii.EMPTY, Insets.EMPTY
        )));
        btn.setOnAction(e -> {
            SceneManager.getInstance().switchScene(instance, SceneType.LEVEL_ONE, true, true);
        });

        buttonList.getChildren().add(btn);

        root.getChildren().add(buttonList);
        root.setScaleX(0.5);
        root.setScaleY(0.5);
        return instance;

    }
}
