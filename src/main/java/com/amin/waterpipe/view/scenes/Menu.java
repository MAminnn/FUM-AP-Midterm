package com.amin.waterpipe.view.scenes;

import com.amin.waterpipe.view.services.SceneManager;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

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
            SceneManager.getInstance().switchScene(instance, SceneType.LEVEL_ONE, true, true, false);
        });
        var btn2 = new Button("Enter Level 2");
        btn2.setBackground(new Background(new BackgroundFill(
                Color.DARKGREY, CornerRadii.EMPTY, Insets.EMPTY
        )));
        btn2.setOnAction(e -> {
            SceneManager.getInstance().switchScene(instance, SceneType.LEVEL_TWO, true, true, false);
        });

        var btn3 = new Button("Enter Level 3");
        btn3.setBackground(new Background(new BackgroundFill(
                Color.DARKGREY, CornerRadii.EMPTY, Insets.EMPTY
        )));
        btn3.setOnAction(e -> {
            SceneManager.getInstance().switchScene(instance, SceneType.LEVEL_THREE, true, true, false);
        });

        buttonList.getChildren().add(btn);
        buttonList.getChildren().add(btn2);
        buttonList.getChildren().add(btn3);

        root.getChildren().add(buttonList);
        root.setScaleX(0.5);
        root.setScaleY(0.5);
        return instance;

    }
}
