package com.saaweel;

public class Directivo extends Empleado {
    public Directivo(String name) {
        super(name);
    }

    @Override
    public String toString() {
        return super.toString() + " -> Directivo";
    }
    
}