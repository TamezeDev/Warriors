package org.zeki.rolgame.model.ataque;

import lombok.Getter;
import org.zeki.rolgame.model.personaje.Personaje;

import java.io.Serializable;

@Getter
public class AtaqueOfensivo extends Ataque implements Serializable {
    private final long SERIAL_VERSION_UID = 1L;
    private int danio;
    private int maxValue;
    private int minValue;
    private final TipoObjetivo tipoObjetivo;

    public AtaqueOfensivo(String nombre, String descripcion, int mana,
                          int minValue, int maxValue, TipoObjetivo tipoObjetivo) {
        super(nombre, descripcion, mana);
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.tipoObjetivo = tipoObjetivo;
    }

    @Override
    public Ataque lanzarAtaque(Personaje actuante, Personaje actuado) {
        super.personaje = actuado;
        danio = (int) (Math.random() * (maxValue - minValue + 1)) + minValue;
        if (tipoObjetivo == TipoObjetivo.TODOS_ENEMIGOS) {
            mensaje = String.format("%s usa %s y provoca daño grave a todos",
                    actuante.getNombre(), nombre).replace("_", " ");
        } else {
            mensaje = String.format("%s ataca a %s con %s y hace %d PS de daño",
                    actuante.getNombre(), actuado.getNombre(), nombre, danio).replace("_", " ");
        }
        return this;
    }

    @Override
    public void aplicarEfecto(Personaje actuado) {
        // Sin instanceof, sin casting. Cada ataque sabe qué hace.
        actuado.setSalud(actuado.getSalud() - danio);
    }

    @Override
    public TipoObjetivo getTipoObjetivo() { return tipoObjetivo; }
}
