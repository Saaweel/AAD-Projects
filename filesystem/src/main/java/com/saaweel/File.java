package com.saaweel;

public class File {
    private static int idCounter = 0;

    private int id;
    private String name;
    private String extension;
    private float size;
    private Author author;

    private String[] splitNameExtension(String name) {
        String[] nameextension = new String[2];
        int lastdot = name.lastIndexOf('.');

        if (lastdot == -1) {
            nameextension[0] = name;
            nameextension[1] = "";
        } else {
            nameextension[0] = name.substring(0, lastdot);
            nameextension[1] = name.substring(lastdot + 1);
        }

        return nameextension;
    }

    public File(String name, float size, Author author) {
        String [] nameextension = splitNameExtension(name);
        
        this.id = idCounter;
        this.name = nameextension[0];
        this.extension = nameextension[1];
        this.size = size;
        this.author = author;

        idCounter++;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getExtension() {
        return extension;
    }

    public float getSize() {
        return size;
    }

    public Author getAuthor() {
        return author;
    }

    public void setName(String name) {
        String [] nameextension = splitNameExtension(name);
        this.name = nameextension[0];
        this.extension = nameextension[1];
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public void setSize(float size) {
        this.size = size;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return Color.ORANGE + "+ File " + String.format("%02d", this.id) + ": " + this.name + "." + this.extension + "\n   - Size: " + this.size + " KB" + "\n   - Author: " + this.author.toString() + Color.RESET;
    }
}