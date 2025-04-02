package com.amin.waterpipe.view;

import com.amin.waterpipe.view.services.SceneManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PipeWaterApplication extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        SceneManager.initialize(primaryStage);

        FXMLLoader fxmlLoader = new FXMLLoader(PipeWaterApplication.class.getResource("main-menu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);

        primaryStage.setResizable(false);
        primaryStage.setTitle("Water Pipe");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }


}