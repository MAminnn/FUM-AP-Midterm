package com.amin.waterpipe.view.services;

import com.amin.waterpipe.view.scenes.LevelOne;
import com.amin.waterpipe.view.scenes.LevelTwo;
import com.amin.waterpipe.view.scenes.Menu;
import com.amin.waterpipe.view.scenes.SceneType;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.HashMap;
import java.util.Map;

public class SceneManager {
    private static SceneManager _sceneManager;
    private final Stage _stage;

    private final Map<SceneType, Scene> _scenes;

    private SceneManager(Stage stage) {
        this._stage = stage;
        this._scenes = new HashMap<>();
    }

    public static void initialize(Stage stage) throws Exception {
        if (_sceneManager != null) {
            throw new Exception("Scene Manager is already initialized and can't initialize again");
        }
        _sceneManager = new SceneManager(stage);
    }

    public static SceneManager getInstance() {
        return _sceneManager;
    }

    public void switchScene(Scene origin, SceneType sceneType, boolean centerOnScreen, boolean brandNew, boolean clearScene) {
        if (clearScene) {
            ((Pane) origin.getRoot()).getChildren().clear();
        }
        Scene scene;
        if (brandNew) {
            switch (sceneType) {
                case SceneType.MENU -> {
                    scene = Menu.initScene();
                    this._scenes.put(SceneType.MENU, scene);
                }
                case SceneType.LEVEL_ONE -> {
                    scene = LevelOne.initScene();
                    this._scenes.put(SceneType.LEVEL_ONE, scene);

                }
                case SceneType.LEVEL_TWO -> {
                    scene = LevelTwo.initScene();
                    this._scenes.put(SceneType.LEVEL_TWO, scene);

                }
                default -> scene = null;
            }
        } else {
            scene = _scenes.get(sceneType);
        }

        var fadeOutTL = getFadeOutTimeLine();

        // Fade out cut scene
        Pane blankWindow = new Pane();
        blankWindow.setBackground(new Background(new BackgroundFill(Color.WHITESMOKE, CornerRadii.EMPTY, Insets.EMPTY)));
        Scene fadeOutCutScene = new Scene(blankWindow, _stage.getScene().getWidth(), _stage.getScene().getHeight());
        fadeOutCutScene.setFill(Color.WHITESMOKE);
        _stage.setScene(fadeOutCutScene);

        fadeOutTL.setOnFinished(e -> {

            // Display a blank white smoke window before the new scene

            Pane blankWindow2 = new Pane();
            blankWindow2.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
            Scene fadeInCutScene = new Scene(blankWindow2, scene.getWidth(), scene.getHeight());
            fadeInCutScene.setFill(Color.TRANSPARENT);
            _stage.setScene(fadeInCutScene);
            _stage.sizeToScene();

            if (centerOnScreen) {
                _stage.centerOnScreen();
            }

            var fadeInTL = getFadeInTimeLine(scene);

            fadeInTL.setOnFinished(ev -> {
                _stage.setScene(scene);
            });
            fadeInTL.playFromStart();

        });
        fadeOutTL.playFromStart();
    }

    private void switchingAnimation(Scene newScene, boolean centerOnScreen) {

    }

    private Timeline getFadeOutTimeLine() {
        var stageWidth = _stage.getWidth();
        var stageHeight = _stage.getHeight();
        double posX = _stage.getX();
        double posY = _stage.getY();
        var shrinkRatio = 0.15;

        int frames = 20;

        Timeline fadeOutTL = new Timeline();
        var fadeOutDuration = Duration.millis(180);

        for (int i = 0; i <= frames; i++) {
            var frac = (double) i / frames;
            fadeOutTL.getKeyFrames().add(new KeyFrame(
                    fadeOutDuration.multiply(frac),
                    e -> {

                        // To keep the _stage centered both horizontally and vertically centered while shrinking
                        _stage.setX(posX + frac * shrinkRatio * stageWidth / 2);
                        _stage.setY(posY + frac * shrinkRatio * stageHeight / 2);

                        _stage.setWidth((1 - frac * shrinkRatio) * stageWidth);

                        _stage.setHeight((1 - frac * shrinkRatio) * stageHeight);

//                        _stage.getScene().getRoot().setScaleX(1 - frac * shrinkRatio);
//                        _stage.getScene().getRoot().setScaleY(1 - frac * shrinkRatio);


                        // Fading effect
                        _stage.setOpacity(1 - frac);
                    }
            ));
        }

        return fadeOutTL;
    }

    private Timeline getFadeInTimeLine(Scene newScene) {

        Timeline fadeInTL = new Timeline();
        var fadeInDuration = Duration.millis(180);

        var shrinkRatio = 0.15;

        int frames = 15;


        var newStageWidth = _stage.getWidth();
        var newStageHeight = _stage.getHeight();

        double newPosX = _stage.getX();
        double newPosY = _stage.getY();

        newScene.getRoot().setScaleX(1);
        newScene.getRoot().setScaleY(1);

        for (int i = 0; i <= frames; i++) {
            double frac = (double) i / frames;
            fadeInTL.getKeyFrames().add(
                    new KeyFrame(fadeInDuration.multiply(frac),
                            ev -> {
                                _stage.setWidth((frac * shrinkRatio + (1 - shrinkRatio)) * newStageWidth);
                                _stage.setHeight((frac * shrinkRatio + (1 - shrinkRatio)) * newStageHeight);

                                // To keep the _stage centered both horizontally and vertically centered while shrinking
                                _stage.setX(newPosX + (-frac * shrinkRatio + shrinkRatio) * newStageWidth / 2);
                                _stage.setY(newPosY + (-frac * shrinkRatio + shrinkRatio) * newStageHeight / 2);

                                _stage.getScene().getRoot().setScaleX(1);
//                                _stage.getScene().getRoot().setScaleX(frac * shrinkRatio + (1 - shrinkRatio));
//                                _stage.getScene().getRoot().setScaleY(frac * shrinkRatio + (1 - shrinkRatio));

                                // Fading effect
                                _stage.setOpacity(frac);
                            })
            );
        }
        return fadeInTL;
    }
}
