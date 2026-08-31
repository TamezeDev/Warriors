package org.zeki.rolgame.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import org.zeki.rolgame.service.FileService;
import org.zeki.rolgame.service.ResultFileService;
import org.zeki.rolgame.util.PathsHelper;
import org.zeki.rolgame.util.SceneHelper;

import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {

    @FXML
    private Label infoGameBtn;

    @FXML
    private Label loadGameBtn;

    @FXML
    private Label newGameBtn;

    @FXML
    private Label feedbackLabel;

    private FileService fileservice;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        instances();
        actions();
    }

    private void instances() {
        fileservice = new FileService();
    }

    private void actions() {
        newGameBtn.setOnMouseClicked(event -> SceneHelper.changeScene(newGameBtn, PathsHelper.FIGHT_VIEW));
        loadGameBtn.setOnMouseClicked(event -> loadGame());
    }

    private void loadGame() {
        ResultFileService result = fileservice.cargarPartida();

        if (result.estadoCarga())
            SceneHelper.changeSceneWithData(loadGameBtn, PathsHelper.FIGHT_VIEW, result.partida());
        else {
            feedbackLabel.setText(result.mensaje());
        }
    }

}
