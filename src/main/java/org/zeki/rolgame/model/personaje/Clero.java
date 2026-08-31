package org.zeki.rolgame.model.personaje;

import org.zeki.rolgame.model.ataque.AtaqueCurativo;
import org.zeki.rolgame.model.ataque.AtaqueOfensivo;
import org.zeki.rolgame.model.ataque.TipoObjetivo;

import java.io.Serializable;

public class Clero extends Personaje implements Serializable {
    private final long SERIAL_VERSION_UID = 1L;
    public Clero() {
        this(Nombres.CLERO_DIVINO);
    }

    public Clero(Nombres nombre) {
        super(nombre, 160, 180);
        ataques[0] = new AtaqueOfensivo("Golpe sagrado", "Impacto de luz básico sin coste", 0, 10, 18, TipoObjetivo.ENEMIGO_RANDOM);
        ataques[1] = new AtaqueCurativo("Bendición", "Cura moderada a un aliado. 20 PM", 20, 55, 85);
        ataques[2] = new AtaqueCurativo("Milagro divino", "Curación potente a un aliado. 50 PM", 50, 120, 170);
        ataques[3] = new AtaqueOfensivo("Luz cegadora", "Ráfaga sagrada que daña a todos. 60 PM", 60, 30, 50, TipoObjetivo.TODOS_ENEMIGOS);
    }
}