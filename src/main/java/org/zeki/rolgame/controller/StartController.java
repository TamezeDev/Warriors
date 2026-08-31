package org.zeki.rolgame.controller;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import org.zeki.rolgame.util.AlertHelper;

import java.io.IOException;

public class StartController extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StartController.class.getResource("/fxml/main-menu-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 768);
        stage.setTitle("WARRIORS");
        stage.setScene(scene);
        AlertHelper.closeAlert(stage);
        stage.show();

    }
}
