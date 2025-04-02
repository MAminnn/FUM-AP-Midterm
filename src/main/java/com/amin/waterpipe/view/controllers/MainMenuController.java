package com.amin.waterpipe.view.controllers;

import com.amin.waterpipe.view.scenes.SceneType;
import com.amin.waterpipe.view.services.SceneManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;

public class MainMenuController {

    @FXML
    private Label welcomeText;

    @FXML
    protected void onEnterTheGameClick(ActionEvent event) throws IOException {
        SceneManager.getInstance().switchScene(welcomeText.getScene(), SceneType.MENU, true);
    }
}
