package org.zeki.rolgame.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.zeki.rolgame.model.personaje.Nombres;
import org.zeki.rolgame.model.personaje.Personaje;

import java.io.Serializable;
import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Equipo implements Serializable {

    private String nombre;
    private Map<Nombres, Personaje> personajes = new LinkedHashMap<>();
    private final long SERIAL_VERSION_UID = 1L;

    public Equipo(String nombre) {
        this.nombre = nombre;
    }

    public void añadirPersonaje(Personaje personaje) {
        personajes.put(personaje.getNombre(), personaje);
    }

    public void eliminarPersonaje(Nombres nombre) {
        personajes.remove(nombre);
    }

    public boolean estaEliminado() {
        return personajes.isEmpty();
    }

    public Optional<Personaje> buscar(Nombres nombre) {
        return Optional.ofNullable(personajes.get(nombre));
    }

    public List<Personaje> personajesVivos() {
        return personajes.values().stream()
                .filter(p -> p.getSalud() > 0)
                .toList();
    }

}
