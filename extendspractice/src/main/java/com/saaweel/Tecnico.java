package com.saaweel;

public class Tecnico extends Operario {
    public Tecnico(String name) {
        super(name);
    }

    @Override
    public String toString() {
        return super.toString() + " -> Técnico";
    }
}
