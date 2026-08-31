package org.zeki.rolgame.service;

import org.zeki.rolgame.model.EfectoPersonaje;
import org.zeki.rolgame.model.personaje.Personaje;
import org.zeki.rolgame.model.ResultadoAtaque;
import org.zeki.rolgame.model.ataque.Ataque;

import java.util.ArrayList;
import java.util.List;

public class BatallaService {
    /**
     * Ejecuta un ataque y devuelve el resultado puro.
     * El controller resuelve la lista de objetivos basándose en TipoObjetivo.
     * En Spring esto será un @Service con @PostMapping("/turno").
     */
    public ResultadoAtaque ejecutarAtaque(Personaje actuante, int ataqueIndex,
                                          List<Personaje> objetivos) {
        Ataque ataque = actuante.getAtaques()[ataqueIndex];
        actuante.quitarMana(ataque); // descuenta mana una sola vez

        String mensajeFinal = null;
        List<EfectoPersonaje> efectos = new ArrayList<>();

        for (Personaje objetivo : objetivos) {
            ataque.lanzarAtaque(actuante, objetivo); // calcula daño + genera mensaje
            if (mensajeFinal == null) mensajeFinal = ataque.getMensaje();

            int saludAntes = objetivo.getSalud();
            ataque.aplicarEfecto(objetivo);          // aplica sin instanceof
            int delta = objetivo.getSalud() - saludAntes;
            efectos.add(new EfectoPersonaje(objetivo, delta, objetivo.estaMuerto()));
        }

        return new ResultadoAtaque(mensajeFinal, efectos);
    }
}
