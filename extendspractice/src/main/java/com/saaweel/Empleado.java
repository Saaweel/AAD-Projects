package com.saaweel;

public class Empleado {
    String name;

    public Empleado(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String newName) {
        this.name = newName;
    }

    @Override
    public String toString() {
        return "Empleado " + this.name;
    }
}