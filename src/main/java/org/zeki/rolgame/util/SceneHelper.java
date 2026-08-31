package org.zeki.rolgame.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.zeki.rolgame.Main;
import org.zeki.rolgame.controller.FightController;
import org.zeki.rolgame.model.Partida;

import java.io.IOException;

public class SceneHelper {

    public static void changeScene(Node node, String path) {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource(path));
        Stage stage = (Stage) node.getScene().getWindow();
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.err.println("Error obteniendo ruta de la nueva escena - " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void changeSceneWithData(Node node, String path, Partida partida) {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource(path));
        Stage stage = (Stage) node.getScene().getWindow();
        try {
            Parent root = loader.load();
            FightController controller = loader.getController();
            controller.cargarPartida(partida);
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.err.println("Error obteniendo ruta de la nueva escena - " + e.getMessage());
            e.printStackTrace();
        }
    }
}
