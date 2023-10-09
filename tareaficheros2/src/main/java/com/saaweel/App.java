package com.saaweel;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
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
        LinkedList<Student> temp = new LinkedList<Student>(students);

        temp.sort((s1, s2) -> s1.getScore() - s2.getScore());

        int size = temp.size();

        if (size % 2 == 0) {
            return (temp.get(size / 2).getScore() + temp.get((size / 2) - 1).getScore()) / 2;
        } else {
            return temp.get(size / 2).getScore();
        }
    }

    private static double getStudentsApproved(LinkedList<Student> students) {
        double approved = 0;

        for (Student s : students) {
            if (s.getScore() >= 5) {
                approved++;
            }
        }

        return (approved * 100) / students.size();
    }

    private static double getStudentsFiled(LinkedList<Student> students) {
        double filed = 0;

        for (Student s : students) {
            if (s.getScore() < 5) {
                filed++;
            }
        }

        return (filed * 100) / students.size();
    }

    private static LinkedList<String> getSubjects(LinkedList<Student> students) {
        LinkedList<String> subjects = new LinkedList<String>();

        for (Student s : students) {
            if (!subjects.contains(s.getSubject())) {
                subjects.add(s.getSubject());
            }
        }

        return subjects;
    }

    public static void main(String [] args) throws IOException {
        LinkedList<Student> students = new LinkedList<Student>();

        File csv = new File("alumnos.csv");

        if (csv.exists() && csv.isFile()) {
            BufferedReader reader = new BufferedReader(new FileReader(csv));
            BufferedWriter writer = new BufferedWriter(new FileWriter("salida.txt"));

            String line = reader.readLine();
            
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(";");

                students.add(new Student(data[0], data[1], Integer.parseInt(data[2])));
            }

            writer.write("N Total: " + students.size() + "\n");
            writer.write("Promedio: " + getStudentsAverage(students) + "\n");
            writer.write("Moda: " + getStudentsMode(students) + "\n");
            writer.write("Mediana: " + getStudentsMedian(students) + "\n");
            writer.write("N Aprobados (%): " + getStudentsApproved(students) + "%\n");
            writer.write("N Supensos (%): " + getStudentsFiled(students) + "%\n");

            LinkedList<String> subjects = getSubjects(students);

            writer.write("Listado Especialidades: " + subjects + "\n");

            for (String subject : subjects) {
                LinkedList<Student> studentsBySubject = new LinkedList<Student>();

                for (Student s : students) {
                    if (s.getSubject().equals(subject)) {
                        studentsBySubject.add(s);
                    }
                }

                writer.write("Para " + subject + ":\n");
                writer.write("    Promedio: " + getStudentsAverage(studentsBySubject) + "\n");
                writer.write("    N Aprobados (%): " + getStudentsApproved(studentsBySubject) + "%\n");
                writer.write("    N Supensos (%): " + getStudentsFiled(studentsBySubject) + "%\n");
                /* OPCIONAL 1 (Todas las estadisticas restantes para las especialidades) */
                writer.write("    N Total: " + studentsBySubject.size() + "\n");
                writer.write("    Moda: " + getStudentsMode(studentsBySubject) + "\n");
                writer.write("    Mediana: " + getStudentsMedian(studentsBySubject) + "\n");
            }

            /* OPCIONAL 2 (Numero de veces que se repite un apeliido) */
            Map <String, Integer> studentsLastNames = new HashMap<String, Integer>();

            for (Student s : students) {
                String lastname = s.getName().split(" ")[1];

                if (lastname.equals("del") || lastname.equals("de"))
                    lastname += " " + s.getName().split(" ")[2];

                if (studentsLastNames.containsKey(lastname)) {
                    studentsLastNames.put(lastname, studentsLastNames.get(lastname) + 1);
                } else {
                    studentsLastNames.put(lastname, 1);
                }
            }

            writer.write("\nNumero de veces que se repite un apellido:\n");
            studentsLastNames.forEach((lastname, times) -> {
                try {
                    writer.write("    " + lastname + ": " + times + "\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            writer.close();
            reader.close();
        } else {
            System.out.println("El archivo no existe");
        }
    }
}