package com.saaweel;

public class Operario extends Empleado {
    public Operario(String name) {
        super(name);
    }

    @Override
    public String toString() {
        return super.toString() + " -> Operario";
    }
    
}