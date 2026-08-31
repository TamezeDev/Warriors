package org.zeki.rolgame.model.personaje;

import org.zeki.rolgame.model.ataque.TipoObjetivo;
import org.zeki.rolgame.model.ataque.AtaqueCurativo;
import org.zeki.rolgame.model.ataque.AtaqueOfensivo;

import java.io.Serializable;

public class Mago extends Personaje implements Serializable {
    private final long SERIAL_VERSION_UID = 1L;
    // Constructor por defecto (por si lo necesitas en otro sitio)
    public Mago() {
        this(Nombres.MAGO_DRUIDA);
    }

    // Constructor con nombre — esto es lo que faltaba
    public Mago(Nombres nombre) {
        super(nombre, 150, 200);
        ataques[0] = new AtaqueOfensivo("Volcán aleatorio",  "Daño leve, no consume PM",         0,  10, 20, TipoObjetivo.ENEMIGO_RANDOM);
        ataques[1] = new AtaqueOfensivo("Destrucción fatal", "Daño medio a un enemigo. 10 PM",   10,  30, 60, TipoObjetivo.ENEMIGO_ELEGIDO);
        ataques[2] = new AtaqueCurativo("Sanación",          "Cura a un aliado. 20 PM",          20,  60, 80);
        ataques[3] = new AtaqueOfensivo("Abismo infernal",   "AoE a todos los enemigos. 50 PM",  50,  50, 80, TipoObjetivo.TODOS_ENEMIGOS);
    }
}
