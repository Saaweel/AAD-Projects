package com.saaweel;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

public class App {
    private static Scanner readS = new Scanner(System.in);
    public static void main(String [] args) {
        String path;

        do {
            System.out.print("Introduce el fichero a leer (exit para salir): ");
            path = readS.nextLine();

            if (!path.equals("exit")) {
                File file = new File(path);

                if (file.exists() && file.isFile()) {  
                    System.out.print("Introduce el carácter a buscar: ");
                    String character = readS.nextLine();

                    if (!character.isEmpty()) {
                        char c = character.charAt(0);

                        try (Scanner readF = new Scanner(file)) {
                            BufferedWriter writeF = new BufferedWriter(new FileWriter("result.txt", true));

                            int line = 1;

                            writeF.write("Resultados para la busqueda de " + character + " en " + path + "\n");

                            while (readF.hasNextLine()) {
                                String lineS = readF.nextLine();
                                int column = lineS.indexOf(c);

                                if (column != -1) {
                                    writeF.write("Linea: " + line + " Columna: " + column + "\n");
                                }

                                line++;
                            }

                            writeF.write("\n");

                            writeF.close();
                            System.out.println("Resultados guardados en " + new File("result.txt").getAbsolutePath());
                        } catch (Exception e) {
                            System.out.println("Error al leer el fichero");
                        }
                    } else {
                        System.out.println("El carácter no puede estar vacío");
                    }
                } else {
                    System.out.println("El fichero no existe o no es un fichero");
                }
            }
        } while (!path.equals("exit"));
    }
}
