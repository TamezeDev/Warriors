package org.zeki.rolgame.model.personaje;

import org.zeki.rolgame.model.ataque.AtaqueOfensivo;
import org.zeki.rolgame.model.ataque.TipoObjetivo;

import java.io.Serializable;

public class Guerrero extends Personaje implements Serializable {
    private final long SERIAL_VERSION_UID = 1L;
    public Guerrero() {
        this(Nombres.GUERRERO_DESPIADADO);
    }

    public Guerrero(Nombres nombre) {
        super(nombre, 230, 100);
        ataques[0] = new AtaqueOfensivo("Golpe salvaje", "Ataque bruto sin coste, objetivo aleatorio", 0, 20, 35, TipoObjetivo.ENEMIGO_RANDOM);
        ataques[1] = new AtaqueOfensivo("Tajo profundo", "Corte brutal a un enemigo. 15 PM", 15, 55, 85, TipoObjetivo.ENEMIGO_ELEGIDO);
        ataques[2] = new AtaqueOfensivo("Furia berserker", "Golpea a todos en un arrebato. 45 PM", 45, 30, 50, TipoObjetivo.TODOS_ENEMIGOS);
        ataques[3] = new AtaqueOfensivo("Ejecución", "Golpe letal contra el más débil. 70 PM", 70, 110, 160, TipoObjetivo.ENEMIGO_ELEGIDO);
    }
}