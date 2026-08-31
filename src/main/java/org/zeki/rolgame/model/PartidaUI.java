package org.zeki.rolgame.controller;

import javafx.scene.layout.VBox;
import org.zeki.rolgame.model.Partida;
import org.zeki.rolgame.model.personaje.Nombres;
import org.zeki.rolgame.model.personaje.Personaje;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * Capa de vista de la partida.
 * Relaciona cada VBox con su Personaje. No es serializable — vive solo en el controller.
 */
public class PartidaUI {

    private final Map<VBox, Nombres> vistaEquipo1 = new LinkedHashMap<>();
    private final Map<VBox, Nombres> vistaEquipo2 = new LinkedHashMap<>();
    private final Partida partida;

    public PartidaUI(Partida partida) {
        this.partida = partida;
    }

    // ── Registro ──────────────────────────────────────────────

    public void registrar(int equipoIdx, VBox vbox, Personaje personaje) {
        partida.añadirPersonaje(equipoIdx, personaje);
        mapaVista(equipoIdx).put(vbox, personaje.getNombre());
    }

    // ── Consultas ─────────────────────────────────────────────

    public Map<VBox, Nombres> vistaActual() {
        return mapaVista(partida.getTurno());
    }

    public Map<VBox, Nombres> vistaEnemiga() {
        return mapaVista(partida.idxEnemigo());
    }

    public int equipoDeVBox(VBox vbox) {
        if (vistaEquipo1.containsKey(vbox)) return 0;
        if (vistaEquipo2.containsKey(vbox)) return 1;
        throw new NoSuchElementException("VBox no registrado");
    }

    public VBox buscarVBox(Personaje personaje) {
        for (int eIdx = 0; eIdx <= 1; eIdx++) {
            for (Map.Entry<VBox, Nombres> entry : mapaVista(eIdx).entrySet()) {
                Optional<Personaje> encontrado = partida.getEquipos()[eIdx].buscar(entry.getValue());
                if (encontrado.isPresent() && encontrado.get() == personaje) {
                    return entry.getKey();
                }
            }
        }
        throw new NoSuchElementException("VBox no encontrado: " + personaje.getNombre());
    }

    public void vincularVBox(int equipoIdx, VBox vbox, Nombres nombre) {
        mapaVista(equipoIdx).put(vbox, nombre);
    }

    // ── Privados ──────────────────────────────────────────────

    private Map<VBox, Nombres> mapaVista(int equipoIdx) {
        return equipoIdx == 0 ? vistaEquipo1 : vistaEquipo2;
    }

    // PartidaUI.java — añadir getters
    public Map<VBox, Nombres> getVistaEquipo1() { return vistaEquipo1; }
    public Map<VBox, Nombres> getVistaEquipo2() { return vistaEquipo2; }
}