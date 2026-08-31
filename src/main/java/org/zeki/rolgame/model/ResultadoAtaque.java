package org.zeki.rolgame.model;

import java.util.List;

public record ResultadoAtaque(
        String mensaje,
        List<EfectoPersonaje> efectos
) {
}
