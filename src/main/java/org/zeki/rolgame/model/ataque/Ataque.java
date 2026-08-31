package org.zeki.rolgame.model.ataque;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.zeki.rolgame.model.personaje.Personaje;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class Ataque implements Serializable {
    private static final long serialVersionUID = 1L;
    protected String nombre;
    protected String descripcion;
    protected int mana;
    protected Personaje personaje;
    protected String mensaje;
    public Ataque(String nombre, String descripcion, int mana) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.mana = mana;
    }

    /** Calcula el efecto (daño/curación) y genera el mensaje de batalla */
    public abstract Ataque lanzarAtaque(Personaje actuante, Personaje actuado);

    /** Aplica el efecto al objetivo. Cada subclase sabe qué hacer. */
    public abstract void aplicarEfecto(Personaje actuado);

    /** El controller decide el flujo de UI basándose en esto, sin instanceof */
    public abstract TipoObjetivo getTipoObjetivo();
}
