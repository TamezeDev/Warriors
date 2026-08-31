package org.zeki.rolgame.model.personaje;

import lombok.Getter;
import lombok.Setter;
import org.zeki.rolgame.model.ataque.Ataque;

import java.io.Serializable;

@Getter
@Setter
public abstract class Personaje implements Serializable {
    private final long SERIAL_VERSION_UID = 1L;
    protected Nombres nombre;
    protected int salud;
    protected int saludMaxima;
    protected int mana;
    protected int especial;
    protected Ataque[] ataques;

    public Personaje() {}

    public Personaje(Nombres nombre, int salud, int mana) {
        this.nombre = nombre;
        this.salud = salud;
        this.mana = mana;
        this.saludMaxima = salud;
        this.especial = 0;
        this.ataques = new Ataque[4];
    }

    /** Descuenta el coste de mana del ataque elegido. */
    public void quitarMana(Ataque ataque) {
        this.mana -= ataque.getMana();
    }

    /** true si el personaje no puede seguir combatiendo. */
    public boolean estaMuerto() {
        return this.salud <= 0;
    }
}