package org.zeki.rolgame.model.personaje;

import org.zeki.rolgame.model.ataque.AtaqueOfensivo;
import org.zeki.rolgame.model.ataque.TipoObjetivo;

import java.io.Serializable;

public class Arquera extends Personaje implements Serializable {
    private final long SERIAL_VERSION_UID = 1L;
    public Arquera() {
        this(Nombres.ARQUERA_MAESTRA);
    }

    public Arquera(Nombres nombre) {
        super(nombre, 170, 120);
        //            nombre               descripcion                          mana  min  max  tipo
        ataques[0] = new AtaqueOfensivo("Flecha rápida", "Disparo rápido sin coste, objetivo aleatorio", 0, 15, 25, TipoObjetivo.ENEMIGO_RANDOM);
        ataques[1] = new AtaqueOfensivo("Disparo certero", "Apunta con calma a un enemigo. 15 PM", 15, 50, 80, TipoObjetivo.ENEMIGO_ELEGIDO);
        ataques[2] = new AtaqueOfensivo("Lluvia de flechas", "Granizo de flechas a todos. 40 PM", 40, 20, 35, TipoObjetivo.TODOS_ENEMIGOS);
        ataques[3] = new AtaqueOfensivo("Flecha mortal", "Disparo definitivo a un objetivo. 60 PM", 60, 90, 130, TipoObjetivo.ENEMIGO_ELEGIDO);
    }
}