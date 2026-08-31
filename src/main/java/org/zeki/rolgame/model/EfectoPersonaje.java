package org.zeki.rolgame.model;

import org.zeki.rolgame.model.personaje.Personaje;

public record EfectoPersonaje(
        Personaje personaje,
        int deltaHP,        // negativo = daño, positivo = curación
        boolean muerto
) {

}
