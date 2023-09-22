package com.saaweel;

public class Oficial extends Operario {
    public Oficial(String name) {
        super(name);
    }

    @Override
    public String toString() {
        return super.toString() + " -> Oficial";
    }
}
