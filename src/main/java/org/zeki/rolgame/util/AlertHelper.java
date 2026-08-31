package org.zeki.rolgame.util;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

public class AlertHelper {

    public static void closeAlert(Stage stage) {
        stage.setOnCloseRequest(event -> {
            event.consume();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Salida");
            alert.setHeaderText("¿Estas seguro que quieres salir?");
            if (alert.showAndWait().get() == ButtonType.OK) {
                Platform.exit();
                System.exit(1);
            }

        });
    }

    public static boolean endGameAlert(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Salir de la partida");
        alert.setHeaderText("¿Estas seguro que quieres finalizar la batalla?, los cambios no guardados se perderán.");
        if (alert.showAndWait().get() == ButtonType.OK) {
            return true;
        }
        return false;
    }
}
