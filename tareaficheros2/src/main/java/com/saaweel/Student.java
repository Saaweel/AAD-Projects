package com.saaweel;

public class Student {
    private String name;
    private String subject;
    private Integer score;
    
    public Student(String name, String subject, int score) {
        this.name = name;
        this.subject = subject;
        this.score = score;
    }

    public String getName() {
        return this.name;
    }

    public String getSubject() {
        return this.subject;
    }

    public int getScore() {
        return this.score;
    }
}
