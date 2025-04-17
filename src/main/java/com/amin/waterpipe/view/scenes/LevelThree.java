package com.amin.waterpipe.view.scenes;


import com.amin.waterpipe.controller.LevelOneController;
import com.amin.waterpipe.controller.LevelThreeController;
import com.amin.waterpipe.view.components.ImageButtonComponent;
import com.amin.waterpipe.view.components.MapComponent;
import com.amin.waterpipe.view.services.SceneManager;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

public class LevelThree extends Scene {

    private static Image bgImage;
    private final LevelThreeController _controller;

    private LevelThree(Parent parent, double width, double height) {
        super(parent, width, height);
        this._controller = new LevelThreeController();
    }

    public static LevelThree initScene() {
        var root = new StackPane();
        var instance = new LevelThree(root, 1280, 768);
        new Thread(() -> {
            bgImage = new Image(Objects.requireNonNull(LevelOne.class.getResource("/gifs/background.gif")).toString(),
                    0, 0, false, false, true);
            var background = new BackgroundImage(bgImage, BackgroundRepeat.NO_REPEAT,
                    BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                    new BackgroundSize(root.getWidth(), root.getHeight(), true, true, true, true));
            root.setBackground(new Background(background));

        }).start();

        var blocks = instance._controller.getBlocks();
        var map = new MapComponent(new ArrayList<>(Arrays.asList(blocks)), instance.widthProperty().multiply(0.7),
                instance.heightProperty().multiply(0.7));
        map.setRotationEventHandler(() -> {
            var isPositionWinning = instance._controller.isPositionWinning();
            if (isPositionWinning) {

                instance.playerWin();

                System.out.println("You Won!");
                map.stopAllAnimations();
            }

        });
        root.setAlignment(Pos.CENTER);

        root.getChildren().add(map);
        return instance;
    }


    private void playerWin() {
        var winningPopup = new WinningPopup((Stage) this.getWindow());
        winningPopup.show();
    }

    public static class WinningPopup extends Stage {

        private Stage _primaryStage;

        public WinningPopup(Stage primaryStage) {
            super();

            this._primaryStage = primaryStage;

            this.initModality(Modality.APPLICATION_MODAL);
            this.initOwner(primaryStage);
            this.initStyle(StageStyle.TRANSPARENT);

            var light = new Light.Distant();
            light.setAzimuth(0);
            light.setElevation(20);
            var lighting = new Lighting(light);
            lighting.setSurfaceScale(1);
            primaryStage.getScene().getRoot().setEffect(lighting);

            var root = new Pane();
            root.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, new Insets(0))));

            var bgImg = new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/images/UI/you_win/bg.png")).toString()
            ));
            var header = new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/images/UI/you_win/header.png")).toString()
            ));
            var banner = new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/images/UI/you_win/table.png")).toString()
            ));
            var closeButton = new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/images/UI/btn/close_2.png")).toString()
            ));
            var backToMenuButton = new ImageButtonComponent(100, Objects.requireNonNull(getClass().getResource("/images/UI/btn/menu.png")).toString(),
                    root.widthProperty(), root.heightProperty(), backToMenuEventHandler);
            var nextLevelButton = new ImageButtonComponent(100, Objects.requireNonNull(getClass().getResource("/images/UI/btn/next.png")).toString(),
                    root.widthProperty(), root.heightProperty(), nextLevelEventHandler);


            bgImg.fitHeightProperty().bind(root.heightProperty());
            bgImg.fitWidthProperty().bind(root.widthProperty());
            bgImg.setPreserveRatio(true);
            header.fitHeightProperty().bind(root.heightProperty());
            header.fitWidthProperty().bind(root.widthProperty());
            header.setPreserveRatio(true);
            banner.fitHeightProperty().bind(root.heightProperty());
            banner.fitWidthProperty().bind(root.widthProperty());
            banner.setPreserveRatio(true);

            closeButton.setFitWidth(50);
            closeButton.setFitHeight(50);

            var menuBox = new StackPane();
            menuBox.getChildren().add(bgImg);

//            var closeButtonRow = new HBox();
//            closeButtonRow.getChildren().add(closeButton);
//            closeButtonRow.setAlignment(Pos.TOP_LEFT);
//            menuBox.getChildren().add(closeButtonRow);

            banner.setScaleX(0.8);
            banner.setScaleX(0.8);
            menuBox.getChildren().add(banner);
            var menuItems = new AnchorPane();
            AnchorPane.setTopAnchor(header, -20.0);
            menuItems.getChildren().add(header);

            var actionsBox = new HBox();
            actionsBox.setSpacing(30);

            actionsBox.getChildren().addAll(backToMenuButton, nextLevelButton);
            actionsBox.setAlignment(Pos.CENTER);

            AnchorPane.setBottomAnchor(actionsBox, 100.0);
            actionsBox.prefWidthProperty().bind(menuItems.widthProperty());
            menuItems.getChildren().add(actionsBox);


            menuBox.getChildren().add(menuItems);

            root.getChildren().add(menuBox);
            var scene = new Scene(root, 450, 604);
            scene.setFill(Color.TRANSPARENT);
            this.setResizable(false);
            this.setTitle("Water Pipe");
            this.setScene(scene);

            double initScale = 0.1;

            root.setScaleX(initScale);
            root.setScaleY(initScale);

            var scalingTimeLine = new Timeline();
            int frames = 30; //ms
            int totalDuration = 300; //ms
            for (int i = 1; i <= frames; i++) {
                double frac = (double) i / frames;

                scalingTimeLine.getKeyFrames().add(new KeyFrame(Duration.millis(frac * totalDuration), (e) -> {
                    root.setScaleX(initScale + frac * (1 - initScale));
                    root.setScaleY(initScale + frac * (1 - initScale));
                }));
            }
            scalingTimeLine.playFromStart();

            this.setOnCloseRequest(e -> {
                primaryStage.close();
            });
        }

        private final javafx.event.EventHandler<? super MouseEvent> backToMenuEventHandler = (e) -> {
            if (e.getButton() == MouseButton.PRIMARY) {
                this.close();
                bgImage.cancel();
                bgImage = null;
                ((Pane) _primaryStage.getScene().getRoot()).getBackground().getImages().getFirst().getImage().cancel();
                ((Pane) _primaryStage.getScene().getRoot()).setBackground(Background.EMPTY);
                System.gc();
                SceneManager.getInstance().switchScene(_primaryStage.getScene(), SceneType.MENU, true, false, true);
            }
        };
        private final javafx.event.EventHandler<? super MouseEvent> nextLevelEventHandler = (e) -> {
            if (e.getButton() == MouseButton.PRIMARY) {
                this.close();
                bgImage.cancel();
                bgImage = null;
                ((Pane) _primaryStage.getScene().getRoot()).getBackground().getImages().getFirst().getImage().cancel();
                ((Pane) _primaryStage.getScene().getRoot()).setBackground(Background.EMPTY);
                System.gc();
                SceneManager.getInstance().switchScene(_primaryStage.getScene(), SceneType.LEVEL_TWO, true, true, true);
            }
        };

    }
}


