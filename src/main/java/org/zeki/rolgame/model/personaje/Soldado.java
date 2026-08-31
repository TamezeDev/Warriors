package org.zeki.rolgame.model.personaje;

import org.zeki.rolgame.model.ataque.AtaqueOfensivo;
import org.zeki.rolgame.model.ataque.TipoObjetivo;

import java.io.Serializable;

public class Soldado extends Personaje implements Serializable {
    private final long SERIAL_VERSION_UID = 1L;
    public Soldado() {
        this(Nombres.SOLDADO_LOCO);
    }

    public Soldado(Nombres nombre) {
        super(nombre, 280, 80);
        ataques[0] = new AtaqueOfensivo("Golpe de escudo", "Empuja al azar con el escudo. Sin coste", 0, 10, 22, TipoObjetivo.ENEMIGO_RANDOM);
        ataques[1] = new AtaqueOfensivo("Estocada", "Golpe directo a un enemigo. 10 PM", 10, 35, 55, TipoObjetivo.ENEMIGO_ELEGIDO);
        ataques[2] = new AtaqueOfensivo("Grito de guerra", "Aturde a todo el equipo rival. 30 PM", 30, 15, 30, TipoObjetivo.TODOS_ENEMIGOS);
        ataques[3] = new AtaqueOfensivo("Carga brutal", "Embiste con toda su armadura. 50 PM", 50, 80, 120, TipoObjetivo.ENEMIGO_ELEGIDO);
    }
}