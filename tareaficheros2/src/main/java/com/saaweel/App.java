package com.saaweel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class App {
    private static double getStudentsAverage(LinkedList<Student> students) {
        double total = 0;

        for (Student s : students) {
            total += s.getScore();
        }

        return total / students.size();
    }

    private static int getStudentsMode(LinkedList<Student> students) {
        Map <Integer, Integer> map = new HashMap<Integer, Integer>();

        for (Student s : students) {
            if (map.containsKey(s.getScore())) {
                map.put(s.getScore(), map.get(s.getScore()) + 1);
            } else {
                map.put(s.getScore(), 1);
            }
        }

        int mode = 0;
        int max = 0;

        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            if (entry.getValue() > max) {
                mode = entry.getKey();
                max = entry.getValue();
            }
        }

        return mode;
    }

    private static double getStudentsMedian(LinkedList<Student> students) {
        
    }

    public static void main(String [] args) throws IOException {
        System.out.println("----------------------------------");
        System.out.println("     LECTURA DE ESTUDIANTES       ");
        System.out.println("----------------------------------");
        LinkedList<Student> students = new LinkedList<Student>();

        File csv = new File("alumnos.csv");

        if (csv.exists() && csv.isFile()) {
            BufferedReader reader = new BufferedReader(new FileReader(csv));

            String line = reader.readLine();
            
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(";");

                students.add(new Student(data[0], data[1], Integer.parseInt(data[2])));
            }

            System.out.println("Nº Total: " + students.size());
            System.out.println("Promedio: " + getStudentsAverage(students));
            System.out.println("Moda: " + getStudentsMode(students));
            System.out.println("Mediana: " + getStudentsMedian(students));

            reader.close();
        } else {
            System.out.println("El archivo no existe");
        }
    }
}