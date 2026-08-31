package org.zeki.rolgame.model.ataque;

import lombok.Getter;
import org.zeki.rolgame.model.personaje.Personaje;

import java.io.Serializable;

@Getter
public class AtaqueCurativo extends Ataque implements Serializable {
    private final long SERIAL_VERSION_UID = 1L;
    private int curacion;
    private int maxValue;
    private int minValue;

    public AtaqueCurativo(String nombre, String descripcion, int mana, int minValue, int maxValue) {
        super(nombre, descripcion, mana);
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    @Override
    public Ataque lanzarAtaque(Personaje actuante, Personaje actuado) {
        super.personaje = actuado;
        curacion = (int) (Math.random() * (maxValue - minValue + 1)) + minValue;
        mensaje = String.format("%s cura a %s con %s y recupera %d PS",
                actuante.getNombre(), actuado.getNombre(), nombre, curacion).replace("_", " ");
        return this;
    }

    @Override
    public void aplicarEfecto(Personaje actuado) {
        int nuevaSalud = Math.min(actuado.getSalud() + curacion, actuado.getSaludMaxima());
        actuado.setSalud(nuevaSalud);
    }

    @Override
    public TipoObjetivo getTipoObjetivo() { return TipoObjetivo.ALIADO_ELEGIDO; }
}
