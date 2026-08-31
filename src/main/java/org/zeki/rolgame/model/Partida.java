package org.zeki.rolgame.model;

import lombok.Getter;
import lombok.Setter;
import org.zeki.rolgame.model.personaje.Nombres;
import org.zeki.rolgame.model.personaje.Personaje;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Getter
public class Partida implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private final Equipo[] equipos;
    private int turno;
    private boolean terminada;
    private int ganadorIdx; // -1 si no hay ganador aún

    public Partida() {
        equipos = new Equipo[]{new Equipo("Jugador 1"), new Equipo("Jugador 2")};
        turno = -1; // sin iniciar
        terminada = false;
        ganadorIdx = -1;
    }

    // ── Inicialización ────────────────────────────────────────

    public void iniciar() {
        turno = (int) (Math.random() * 2);
    }

    public void añadirPersonaje(int equipoIdx, Personaje personaje) {
        equipos[equipoIdx].añadirPersonaje(personaje);
    }

    // ── Gestión de turno ──────────────────────────────────────

    public void siguienteTurno() {
        turno = (turno == 0) ? 1 : 0;
    }

    public Equipo equipoActual() {
        return equipos[turno];
    }

    public Equipo equipoEnemigo() {
        return equipos[idxEnemigo()];
    }

    public int idxEnemigo() {
        return turno == 0 ? 1 : 0;
    }

    public List<Personaje> enemigosVivos() {
        return equipoEnemigo().personajesVivos();
    }

    public List<Personaje> aliadosVivos() {
        return equipoActual().personajesVivos();
    }

    // ── Estado de la partida ──────────────────────────────────

    /**
     * Elimina el personaje muerto de su equipo y comprueba victoria.
     * Devuelve true si la partida ha terminado tras esta muerte.
     */
    public boolean registrarMuerte(int equipoIdx, Nombres nombre) {
        equipos[equipoIdx].eliminarPersonaje(nombre);
        if (equipos[equipoIdx].estaEliminado()) {
            terminada = true;
            ganadorIdx = equipoIdx == 0 ? 1 : 0; // gana el contrario
            return true;
        }
        return false;
    }

    public String nombreGanador() {
        if (!terminada) return null;
        return equipos[ganadorIdx].getNombre();
    }

    public String nombreTurnoActual() {
        return equipos[turno].getNombre();
    }
}