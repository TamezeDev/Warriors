package org.zeki.rolgame.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.zeki.rolgame.Main;
import org.zeki.rolgame.model.*;
import org.zeki.rolgame.model.ataque.Ataque;
import org.zeki.rolgame.model.personaje.*;
import org.zeki.rolgame.service.BatallaService;
import org.zeki.rolgame.service.FileService;
import org.zeki.rolgame.util.AlertHelper;
import org.zeki.rolgame.util.PathsHelper;
import org.zeki.rolgame.util.SceneHelper;
import org.zeki.rolgame.controller.PartidaUI;

import java.net.URL;
import java.util.*;

public class FightController implements Initializable {

    @FXML
    private VBox arquera1;

    @FXML
    private VBox arquera2;

    @FXML
    private VBox powerBox;

    @FXML
    private VBox clero1;

    @FXML
    private VBox clero2;

    @FXML
    private Button goBackBtn;

    @FXML
    private VBox guerrero1;

    @FXML
    private VBox guerrero2;

    @FXML
    private Label infoGameLabel;

    @FXML
    private VBox mago1;

    @FXML
    private VBox mago2;

    @FXML
    private Label nameLabel;

    @FXML
    private Label pmLabel;

    @FXML
    private Label psLabel;

    @FXML
    private Button saveGameBtn;

    @FXML
    private VBox soldado1;

    @FXML
    private VBox menuCombateBox;

    @FXML
    private VBox soldado2;

    @FXML
    private Label spLabel;

    @FXML
    private Button continueBtn;

    @FXML
    private StackPane equipo1Box;

    @FXML
    private StackPane equipo2Box;

    // ─── PARTIDA ─────────────────────────────────────────────
    private Partida partida;
    private PartidaUI ui;

    // ─── SERVICIO ─────────────────────────────────────────────
    private BatallaService batallaService;
    private FileService fIleService;
    // ══════════════════════════════════════════════════════════
    //  INICIALIZACIÓN
    // ══════════════════════════════════════════════════════════

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        instances();
        initGUI();
        actions();
    }

    private void instances() {
        batallaService = new BatallaService();
        fIleService = new FileService();
        partida = new Partida();
        ui = new PartidaUI(partida);
        cargarPersonajes();
        partida.iniciar();
    }

    private void cargarPersonajes() {
        ui.registrar(0, arquera1, new Arquera());
        ui.registrar(0, soldado1, new Soldado());
        ui.registrar(0, mago1, new Mago());
        ui.registrar(0, clero1, new Clero());
        ui.registrar(0, guerrero1, new Guerrero());

        ui.registrar(1, arquera2, new Arquera());
        ui.registrar(1, soldado2, new Soldado());
        ui.registrar(1, mago2, new Mago());
        ui.registrar(1, clero2, new Clero());
        ui.registrar(1, guerrero2, new Guerrero());
    }

    private void initGUI() {
        bloquearMenus(false, true);
        infoGameLabel.setText("Turno del " + partida.nombreTurnoActual());
        eventoOpciones();
    }

    private void actions() {
        goBackBtn.setOnAction(e -> {
            if (AlertHelper.endGameAlert()) SceneHelper.changeScene(goBackBtn, PathsHelper.MAIN_MENU_VIEW);
        });
        continueBtn.setOnAction(e -> siguienteTurno());
        saveGameBtn.setOnAction(e -> guardarPartida());
    }

    // ── Turno ─────────────────────────────────────────────────

    private void siguienteTurno() {
        partida.siguienteTurno();
        infoGameLabel.setText("Turno del " + partida.nombreTurnoActual());
        menuCombateBox.setVisible(false);
        menuCombateBox.setDisable(false);
        continueBtn.setVisible(false);
        bloquearMenus(false, true);
        eventoOpciones();
    }

    private void bloquearMenus(boolean propio, boolean ajeno) {
        int turno = partida.getTurno();
        equipo1Box.setDisable(turno == 0 ? propio : ajeno);
        equipo2Box.setDisable(turno == 0 ? ajeno : propio);
    }

    // ── Selección de personaje ────────────────────────────────

    private void eventoOpciones() {
        ui.vistaActual().forEach((vbox, nombre) -> vbox.setOnMouseClicked(e -> mostrarPanelAccion(partida.equipoActual().buscar(nombre).orElseThrow())));
    }

    private void mostrarPanelAccion(Personaje personaje) {
        nameLabel.setText(personaje.getNombre().toString().replace("_", " "));
        psLabel.setText(String.valueOf(personaje.getSalud()));
        pmLabel.setText(String.valueOf(personaje.getMana()));
        spLabel.setText(String.valueOf(personaje.getEspecial()));

        for (int i = 0; i < powerBox.getChildren().size(); i++) {
            Label label = (Label) powerBox.getChildren().get(i);
            Ataque ataque = personaje.getAtaques()[i];
            label.setText(ataque.getNombre());
            label.setAccessibleHelp(ataque.getDescripcion());
            int finalI = i;
            label.setOnMouseClicked(e -> seleccionarAtaque(personaje, finalI));
        }
        menuCombateBox.setVisible(true);
    }

    // ── Selección y ejecución de ataque ───────────────────────

    private void seleccionarAtaque(Personaje actuante, int ataqueIndex) {
        switch (actuante.getAtaques()[ataqueIndex].getTipoObjetivo()) {
            case ENEMIGO_ELEGIDO -> esperarSeleccionEnemigo(actuante, ataqueIndex);
            case ENEMIGO_RANDOM -> ejecutarConObjetivoRandom(actuante, ataqueIndex);
            case TODOS_ENEMIGOS -> ejecutarConTodosEnemigos(actuante, ataqueIndex);
            case ALIADO_ELEGIDO -> esperarSeleccionAliado(actuante, ataqueIndex);
        }
    }

    private void esperarSeleccionEnemigo(Personaje actuante, int ataqueIndex) {
        infoGameLabel.setText("Selecciona un enemigo");
        bloquearMenus(true, false);
        ui.vistaEnemiga().forEach((vbox, nombre) -> vbox.setOnMouseClicked(e -> {
            Personaje objetivo = partida.equipoEnemigo().buscar(nombre).orElseThrow();
            ejecutarAtaque(actuante, ataqueIndex, List.of(objetivo));
        }));
    }

    private void esperarSeleccionAliado(Personaje actuante, int ataqueIndex) {
        infoGameLabel.setText("Selecciona un aliado");
        ui.vistaActual().forEach((vbox, nombre) -> vbox.setOnMouseClicked(e -> {
            Personaje objetivo = partida.equipoActual().buscar(nombre).orElseThrow();
            ejecutarAtaque(actuante, ataqueIndex, List.of(objetivo));
        }));
    }

    private void ejecutarConObjetivoRandom(Personaje actuante, int ataqueIndex) {
        List<Personaje> vivos = partida.enemigosVivos();
        Personaje objetivo = vivos.get((int) (Math.random() * vivos.size()));
        ejecutarAtaque(actuante, ataqueIndex, List.of(objetivo));
    }

    private void ejecutarConTodosEnemigos(Personaje actuante, int ataqueIndex) {
        ejecutarAtaque(actuante, ataqueIndex, partida.enemigosVivos());
    }

    private void ejecutarAtaque(Personaje actuante, int ataqueIndex, List<Personaje> objetivos) {
        ResultadoAtaque resultado = batallaService.ejecutarAtaque(actuante, ataqueIndex, objetivos);

        infoGameLabel.setText(resultado.mensaje());
        resultado.efectos().forEach(efecto -> {
            VBox vbox = ui.buscarVBox(efecto.personaje());
            actualizarBarraSalud(vbox, efecto.personaje());
            if (efecto.muerto()) mostrarMuerto(vbox, efecto.personaje());
        });

        terminarAccion();
    }

    // ── Pintado ───────────────────────────────────────────────

    private void actualizarBarraSalud(VBox character, Personaje personaje) {
        ProgressBar barra = (ProgressBar) character.getChildren().getFirst();
        barra.getStyleClass().removeAll("barra-verde", "barra-amarilla", "barra-roja");
        double vida = (double) personaje.getSalud() / personaje.getSaludMaxima();
        barra.setProgress(Math.max(vida, 0));
        if (vida <= 0.0) barra.setProgress(0);
        else if (vida <= 0.33) barra.getStyleClass().add("barra-roja");
        else if (vida <= 0.66) barra.getStyleClass().add("barra-amarilla");
        else barra.getStyleClass().add("barra-verde");
    }

    // Separar en dos métodos
    private void mostrarMuerto(VBox character, Personaje personaje) {
        mostrarCruzMuerto(character); // solo UI
        int eIdx = ui.equipoDeVBox(character);
        boolean finPartida = partida.registrarMuerte(eIdx, personaje.getNombre());
        if (finPartida) mostrarVictoria();
    }

    private void mostrarCruzMuerto(VBox character) {
        ImageView img = new ImageView(new Image(Objects.requireNonNull(Main.class.getResourceAsStream(PathsHelper.DIED_CHARACTER))));
        img.setFitWidth(character.getWidth());
        img.setFitHeight(character.getHeight());
        StackPane parent = (StackPane) character.getParent();
        parent.getChildren().add(img);
        parent.setDisable(true);
    }

    private void terminarAccion() {
        equipo1Box.setDisable(true);
        equipo2Box.setDisable(true);
        menuCombateBox.setDisable(true);
        continueBtn.setVisible(true);
    }

    private void mostrarVictoria() {
        infoGameLabel.setText("¡" + partida.nombreGanador() + " ha ganado!");
        equipo1Box.setDisable(true);
        equipo2Box.setDisable(true);
        menuCombateBox.setVisible(false);
        continueBtn.setVisible(false);
        saveGameBtn.setVisible(true);
    }

    // ══════════════════════════════════════════════════════════
    //  GUARDADO DE PARTIDA
    // ══════════════════════════════════════════════════════════
    private void guardarPartida() {
        String mensaje = fIleService.guardarJuego(partida) ? "Partida guardada con éxito" : "Error en guardado de partida";
        infoGameLabel.setText(mensaje);
    }

    // ══════════════════════════════════════════════════════════
    //  CARGAR DE PARTIDA
    // ══════════════════════════════════════════════════════════
    public void cargarPartida(Partida partida) {
        this.partida = partida;
        this.ui = new PartidaUI(partida); // PartidaUI vacío, listo para vincular

        // Vincular VBox a los Nombres que ya están en el modelo cargado
        // No llamamos a registrar() porque el modelo ya tiene los personajes
        ui.vincularVBox(0, arquera1, Nombres.ARQUERA_MAESTRA);
        ui.vincularVBox(0, soldado1, Nombres.SOLDADO_LOCO);
        ui.vincularVBox(0, mago1, Nombres.MAGO_DRUIDA);
        ui.vincularVBox(0, clero1, Nombres.CLERO_DIVINO);
        ui.vincularVBox(0, guerrero1, Nombres.GUERRERO_DESPIADADO);

        ui.vincularVBox(1, arquera2, Nombres.ARQUERA_MAESTRA);
        ui.vincularVBox(1, soldado2, Nombres.SOLDADO_LOCO);
        ui.vincularVBox(1, mago2, Nombres.MAGO_DRUIDA);
        ui.vincularVBox(1, clero2, Nombres.CLERO_DIVINO);
        ui.vincularVBox(1, guerrero2, Nombres.GUERRERO_DESPIADADO);

        // Restaurar estado visual de todas las barras de vida
        restaurarEstadoVisual();

        // Reanudar desde el turno guardado
        bloquearMenus(false, true);
        infoGameLabel.setText("Turno del " + partida.nombreTurnoActual());
        eventoOpciones();
    }

    private void restaurarEstadoVisual() {
        for (int eIdx = 0; eIdx <= 1; eIdx++) {
            Map<VBox, Nombres> mapa = eIdx == 0 ? ui.getVistaEquipo1() : ui.getVistaEquipo2(); // necesitas getter en PartidaUI

            for (Map.Entry<VBox, Nombres> entry : mapa.entrySet()) {
                VBox vbox = entry.getKey();
                Nombres nombre = entry.getValue();

                Optional<Personaje> personajeOpt = partida.getEquipos()[eIdx].buscar(nombre);

                if (personajeOpt.isPresent()) {
                    // Personaje vivo — actualizar barra
                    actualizarBarraSalud(vbox, personajeOpt.get());
                } else {
                    // Personaje muerto en la partida guardada — mostrar cruz
                    mostrarCruzMuerto(vbox);
                }
            }
        }
    }
}


