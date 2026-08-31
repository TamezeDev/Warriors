package org.zeki.rolgame.service;

import org.zeki.rolgame.model.Partida;
import org.zeki.rolgame.util.PathsHelper;

import java.io.*;

public class FileService {

    public boolean guardarJuego(Partida partida) {
        // CREAR RUTA DE GUARDADO
        File file = new File(PathsHelper.GAME_DATA);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.err.println("ERROR creando la ruta de guardado, " + e.getMessage());
                return false;
            }
        }
        // GUARDAR OBJETOS SERIALIZADOS
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(partida);
        } catch (FileNotFoundException e) {
            System.err.println("Error leyendo ruta de guardado " + e.getMessage());
            return false;
        } catch (IOException e) {
            System.err.println("Error en guardado");
            return false;
        }
        return true;

    }

    public ResultFileService cargarPartida() {
        File file = new File(PathsHelper.GAME_DATA);
        if (!file.exists()) {
            return new ResultFileService(false, null, "No hay partidas guardadas");
        }
        Partida partida;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            partida = (Partida) ois.readObject();
            return new ResultFileService(true, partida, "Partida cargada con éxito");
        } catch (ClassNotFoundException e) {
            System.err.println("Error de casteo de clase " + e.getMessage());
            return new ResultFileService(false, null, "No se pudo cargar la partida");
        } catch (FileNotFoundException e) {
            System.err.println("Error leyendo ruta de guardado " + e.getMessage());
            return new ResultFileService(false, null, "Datos de partida inaccesibles");
        } catch (IOException e) {
            System.err.println("Error cargando partida");
            return new ResultFileService(false, null, "Erro en durante la carga de la partida");
        }
    }
}
