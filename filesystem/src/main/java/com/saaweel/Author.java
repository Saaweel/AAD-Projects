package com.saaweel;

public class Author {
    private String dni;
    private String fullname;

    public Author(String dni, String fullname) {
        this.dni = dni;
        this.fullname = fullname;
    }

    public String getDni() {
        return this.dni;
    }

    public String getFullname() {
        return this.fullname;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String toString() {
        return this.fullname + " (" + this.dni + ")";
    }
}
