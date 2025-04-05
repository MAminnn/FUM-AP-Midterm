package com.amin.waterpipe.view.components;

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

public class NormalPipeComponent extends Pane {

    private final RotateTransition rotationAnimation;
    private boolean isReverting = false;

    private int _clockwiseRotationsPending = 0;
    private int _counterClockwiseRotationsPending = 0;

    private final NormalPipe _pipe;
    private Runnable _pipeRotationEventHandler;

    public NormalPipeComponent(String path, NormalPipe pipeEntity) {
        super();
        this._pipe = pipeEntity;

        var pipeImg = new ImageView(Objects.requireNonNull(MapComponent.class.getResource(path)).toString());

        pipeImg.fitWidthProperty().bind(this.widthProperty());
        pipeImg.fitHeightProperty().bind(this.heightProperty());
        pipeImg.setPreserveRatio(true); // Maintain aspect ratio

        pipeImg.setCursor(Cursor.HAND);
        pipeImg.setOnMouseClicked(this::onMouseClicked);

        this.getChildren().add(pipeImg);


        rotationAnimation = new RotateTransition();
        rotationAnimation.setNode(this);
        rotationAnimation.setDuration(Duration.millis(300));
        rotationAnimation.setInterpolator(Interpolator.EASE_BOTH);
//        rotationAnimation.setOnFinished(e -> {
//            animationComplete(() -> {
//            });
//        });
//        rotationAnimation.setOnFinished(e -> {
//            this.isTransitionPlaying = false;
//        });
    }

    public void setRotationEventHandler(Runnable pipeRotationEventHandler) {
        this._pipeRotationEventHandler = pipeRotationEventHandler;
    }

    public void stopAnimation() {
        this.rotationAnimation.setOnFinished((e) -> {
        });
        this.rotationAnimation.stop();

    }

    private void onMouseClicked(MouseEvent e) {
        if (e.getButton().equals(MouseButton.PRIMARY)) {
            onPrimaryClick();
        } else if (e.getButton().equals(MouseButton.SECONDARY)) {
            onSecondaryClick();
        }

    }


    private void animationComplete(Runnable action) {
        action.run();
        _pipeRotationEventHandler.run();
    }


    private void onPrimaryClick() {
        if (isReverting) {
            return;
        }
        rotationAnimation.setByAngle(90);
        _clockwiseRotationsPending += 1;
        if (_counterClockwiseRotationsPending > 0) {
            rotationAnimation.stop();
            rotationAnimation.setOnFinished(e -> {
                animationComplete(() -> {
                    isReverting = false;
                    _clockwiseRotationsPending = 0;
                    _counterClockwiseRotationsPending = 0;
                });
            });
            var rotatedAmount = this.getRotate();
            // Java mod operation % behaves wrongly so we should use other statements
            var correctModOnNegativeValues = -1 * (rotatedAmount % -90 + -90) % -90;
            rotationAnimation.setByAngle(correctModOnNegativeValues);
            rotationAnimation.play();
            isReverting = true;
            return;
        }
        rotationAnimation.setOnFinished(e -> {
            animationComplete(() -> {
                _clockwiseRotationsPending -= 1;
                this._pipe.rotateClockWise();
                if (_clockwiseRotationsPending > 0) {
                    rotationAnimation.play();
                } else {
                    rotationAnimation.setOnFinished(ev -> {
                    });
                }
            });
        });
        if (_clockwiseRotationsPending == 1) {
            rotationAnimation.play();
        }
    }

    private void onSecondaryClick() {
        if (isReverting) {
            return;
        }
        rotationAnimation.setByAngle(-90);
        _counterClockwiseRotationsPending += 1;
        if (_clockwiseRotationsPending > 0) {
            rotationAnimation.stop();
            rotationAnimation.setOnFinished(e -> {
                animationComplete(() -> {
                    _clockwiseRotationsPending = 0;
                    _counterClockwiseRotationsPending = 0;
                    isReverting = false;
                });
            });
            var rotatedAmount = this.getRotate();
            // Java mod operation % behaves wrongly on negative numbers so we should use other statements
            var correctModOnNegativeValues = -1 * (rotatedAmount % 90 + 90) % 90;
            rotationAnimation.setByAngle(correctModOnNegativeValues);
            rotationAnimation.play();
            isReverting = true;
            return;
        }
        rotationAnimation.setOnFinished(e -> {
            animationComplete(() -> {
                _counterClockwiseRotationsPending -= 1;
                this._pipe.rotateCounterClockWise();
                if (_counterClockwiseRotationsPending > 0) {
                    rotationAnimation.play();
                } else {
                    rotationAnimation.setOnFinished(ev -> {

                    });
                }
            });
        });
        if (_counterClockwiseRotationsPending == 1) {
            rotationAnimation.play();
        }
    }
}
