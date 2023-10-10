package com.saaweel;

import java.io.RandomAccessFile;
import java.util.Scanner;

public class App {
    public void generarIndice(String src, String dst) {
        try {
            RandomAccessFile br = new RandomAccessFile(src, "r");
            RandomAccessFile bw = new RandomAccessFile(dst, "rw");
            
            String line = br.readLine();
            
            int pos = (int) br.getFilePointer();

            while ((line = br.readLine()) != null) {
                String [] lineSplit = line.split(";");
                
                bw.writeBytes(lineSplit[0] + ";" + pos + "\n");

                pos = (int) br.getFilePointer();
            }

            bw.close();
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void buscarInfo(String dni, String indexFile) {
        try {
            RandomAccessFile raf = new RandomAccessFile(indexFile, "r");

            String line = raf.readLine();
            String [] lineSplit = line.split(";");

            while (line != null && !lineSplit[0].equals(dni)) {
                line = raf.readLine();
                lineSplit = line.split(";");
            }

            if (line != null) {
                RandomAccessFile br = new RandomAccessFile("db.csv", "r");
                br.seek(Integer.parseInt(lineSplit[1]));
                System.out.println(br.readLine());
                br.close();
            } else {
                System.out.println("No se ha encontrado el dni");
            }

            raf.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        Scanner readS = new Scanner(System.in);
        App app = new App();

        String msg = "";

        do {
            System.out.println("Introduce la operación a realizar: ");
            System.out.println("  -g <fichero_entrada> <fichero_salida>");
            System.out.println("  -f <dni> <fichero_índice>");
            System.out.println("  exit");
			msg = readS.nextLine();
			args = msg.split(" ");

			System.out.println("");

            if (args.length == 3) {
                switch(args[0]){
                    case "-g":
                        app.generarIndice(args[1], args[2]);
                        break;
                    case "-f":
                        app.buscarInfo(args[1], args[2]);
                        break;
                    default:
                        System.err.println("Operación no implementada");    
                        break;
                }
            } else {
                System.err.println("El programa funciona con 3 argumentos");
            }
        } while (!msg.equals("exit"));

        readS.close();
    }
}
