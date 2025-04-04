package com.amin.waterpipe.view.scenes;


import com.amin.waterpipe.model.entities.Map;
import com.amin.waterpipe.model.entities.pipe.Block;
import com.amin.waterpipe.model.entities.pipe.NormalPipe;
import com.amin.waterpipe.view.components.MapComponent;
import com.amin.waterpipe.view.services.SceneManager;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
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

import java.util.*;

public class LevelOne extends Scene {

    private Block[] _blocks;
    private Map _correctMap;
    private com.amin.waterpipe.model.entities.level.LevelOne _model;

    private LevelOne(Parent parent, double width, double height) {
        super(parent, width, height);
    }

    private Block[] loadBlocks() {
        this._model = new com.amin.waterpipe.model.entities.level.LevelOne();
        var map = this._model.getMap();
        var originalBlocks = new Block[map.getBlocks().length];
        for (int i = 0; i < originalBlocks.length; i++) {
            originalBlocks[i] = Block.copy(map.getBlocks()[i]);
        }
        this._correctMap = new Map(map.Width, map.Height, originalBlocks);
        this._blocks = map.getBlocks();
        this.randomInitialization();
        return map.getBlocks();
    }

    public static LevelOne initScene() {
        var root = new VBox();
        var instance = new LevelOne(root, 1280, 768);
        var background = new BackgroundImage(new Image(Objects.requireNonNull(LevelOne.class.getResource("/images/background.jpg")).toString()), BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                new BackgroundSize(root.getWidth(), root.getHeight(), true, true, true, true));
        root.setBackground(new Background(background));

        var blocks = instance.loadBlocks();
        var map = new MapComponent(new ArrayList<>(Arrays.asList(blocks)));
        map.setWinCheckHandler(() -> {
            var isPositionWinning = Arrays.stream(instance._blocks).allMatch(b ->
            {
                if (b.get_pipe() == null) {
                    return true;
                }
                return b.get_pipe().getPipeType().equals(instance._correctMap.getBlock(b.getCoordinate()).get_pipe().getPipeType());

            });
            if (isPositionWinning) {

                instance.playerWin();

                System.out.println("You Won!");
                map.stopAllAnimations();
            }

        });
        root.getChildren().add(map);
        root.setPadding(new Insets(50, 40, 50, 40));
        return instance;
    }

    private void randomInitialization() {
        Random random = new Random();
        for (Block block : this._blocks) {
            if (block.get_pipe() instanceof NormalPipe) {
                var rotationsCount = random.nextInt(4);
                for (int i = 0; i < rotationsCount; i++) {
                    ((NormalPipe) block.get_pipe()).rotateClockWise();
                }
            }
        }
    }

    private void playerWin() {
        var winningPopup = new WinningPopup((Stage) this.getWindow());

    }

    public static class WinningPopup extends Stage {
        public WinningPopup(Stage primaryStage) {
            super();


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
            var backToMenuButton = new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/images/UI/btn/menu.png")).toString()
            ));
            var nextLevelButton = new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/images/UI/btn/next.png")).toString()
            ));


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

            backToMenuButton.setFitWidth(100);
            backToMenuButton.setFitHeight(100);

            nextLevelButton.setFitWidth(100);
            nextLevelButton.setFitHeight(100);


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

            backToMenuButton.setCursor(Cursor.HAND);
            backToMenuButton.setOnMouseClicked(backToMenuEventHandler);

            nextLevelButton.setCursor(Cursor.HAND);
            nextLevelButton.setOnMouseClicked(nextLevelEventHandler);


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

            this.show();
        }

        private final javafx.event.EventHandler<? super MouseEvent> backToMenuEventHandler = (e) -> {
            if (e.getButton() == MouseButton.PRIMARY) {
                this.close();
                SceneManager.getInstance().switchScene(this.getScene(), SceneType.MENU, true, false);
            }
        };
        private final javafx.event.EventHandler<? super MouseEvent> nextLevelEventHandler = (e) -> {

        };

    }
}


