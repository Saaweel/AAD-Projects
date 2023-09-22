package com.saaweel;

import java.util.LinkedList;
import java.util.Locale;
import java.util.Scanner;

import com.comparators.*;

public class App {
    public static Scanner readN = new Scanner(System.in);
    public static Scanner readS = new Scanner(System.in);

    public static final LinkedList<File> files = new LinkedList<File>();

    private static int getFileFromList(String id) {
        for (int i = 0; i < files.size(); i++) {
            if (String.format("%02d", files.get(i).getId()).equals(id)) {
                return i;
            }
        }

        return -1;
    }

    private static void printFiles(LinkedList<File> files) {
        float totalSize = 0;

        for (int i = 0; i < files.size(); i++) {
            System.out.println(files.get(i).toString());
        }

        System.out.println(Color.GREEN + "Total size: " + Color.CYAN + totalSize + " KB" + Color.RESET);
    }

    public static void main(String [] args) {
        readN.useLocale(Locale.US); // El scanner no coge los floats con . sino con , (1,5 en vez de 1.5) si no se pone
        System.out.println("---------------------------");
        System.out.println("------- File System -------");
        System.out.println("---------------------------");
        
        int op;

        do {
            LinkedList <File> foundFiles = new LinkedList<File>();

            System.out.println("");
            System.out.println(Color.ORANGE + "What do you want to do?" + Color.RESET);
            System.out.println("1. Add a file");
            System.out.println("2. Modify a file");
            System.out.println("3. Delete a file");
            System.out.println("4. Look for a file");
            System.out.println("5. Look for a file by extension");
            System.out.println("6. List all files");
            System.out.println("7. List all files by name");
            System.out.println("8. List all files by size");
            System.out.println("9. List all files by extension");
            System.out.println("10. List all extensions");
            System.out.println("11. Exit");

            System.out.print(Color.CYAN + "Select an option: " + Color.RESET);
            
            try {
                op = readN.nextInt();
            } catch (Exception e) {
                op = 0;
                readN.nextLine();
            }

            System.out.println("");

            switch (op) {
                case 1:
                    System.out.print(Color.CYAN + "Full name: " + Color.RESET);
                    String name = readS.nextLine();
                    float size = -1;

                    do {
                        try {
                            System.out.print(Color.CYAN + "Size: " + Color.RESET);
                            size = readN.nextFloat();
                        } catch (Exception e) {
                            System.out.println(Color.RED + "Invalid size" + Color.RESET);
                            readN.nextLine();
                        }
                    } while (size == -1);

                    System.out.print(Color.CYAN + "Author DNI: " + Color.RESET);
                    String authorDni = readS.nextLine();
                    System.out.print(Color.CYAN + "Author full name: " + Color.RESET);
                    String authorName = readS.nextLine();

                    files.add(new File(name, size, new Author(authorDni, authorName)));

                    break;
                case 2:
                    System.out.print(Color.CYAN + "File to modify: " + Color.RESET);
                    
                    int fileposition = getFileFromList(readS.nextLine());

                    if(fileposition != -1) {
                        System.out.println(Color.GREEN + "File found" + Color.RESET);
                        System.out.print(Color.CYAN + "New name: " + Color.RESET);
                        String newName = readS.nextLine();
                        System.out.print(Color.CYAN + "New size: " + Color.RESET);
                        float newSize = -1;

                        do {
                            try {
                                System.out.print(Color.CYAN + "New size: " + Color.RESET);
                                newSize = readN.nextFloat();
                            } catch (Exception e) {
                                System.out.println(Color.RED + "Invalid size" + Color.RESET);
                                readN.nextLine();
                            }
                        } while (newSize == -1);

                        files.get(fileposition).setName(newName);
                        files.get(fileposition).setSize(newSize);
                    } else
                        System.out.println(Color.RED + "File not found" + Color.RESET);

                    break;
                case 3:
                    System.out.print(Color.CYAN + "File to delete: " + Color.RESET);

                    int filetodelete = getFileFromList(readS.nextLine());

                    if(filetodelete != -1) {
                        System.out.println(Color.GREEN + "File deleted" + Color.RESET);

                        files.remove(filetodelete);
                    } else
                        System.out.println(Color.RED + "File not found" + Color.RESET);

                    break;
                case 4:
                    System.out.print(Color.CYAN + "File to search: " + Color.RESET);
                    String query = readS.nextLine();

                    for (int i = 0; i < files.size(); i++) {
                        File file = files.get(i);

                        if ((file.getName()+"."+file.getExtension()).contains(query)) {
                            foundFiles.add(file);
                        }
                    }

                    if (foundFiles.size() > 0) {
                        System.out.println(Color.GREEN + "Found: " + Color.CYAN + foundFiles.size() + " files" + Color.RESET);
                        printFiles(foundFiles);
                    } else {
                        System.out.println(Color.RED + "No files found" + Color.RESET);
                    }

                    break;
                case 5:
                    System.out.print(Color.CYAN + "Extension to search: " + Color.RESET);
                    String query2 = readS.nextLine();

                    for (int i = 0; i < files.size(); i++) {
                        File file = files.get(i);

                        if (file.getExtension().equals(query2)) {
                            foundFiles.add(file);
                        }
                    }

                    if (foundFiles.size() > 0) {
                        System.out.println(Color.GREEN + "Found: " + Color.CYAN + foundFiles.size() + " files ." + query2 + Color.RESET);
                        printFiles(foundFiles);
                    } else {
                        System.out.println(Color.RED + "No files found" + Color.RESET);
                    }

                    break;
                case 6:
                    System.out.println(Color.GREEN + "Listing all files:" + Color.RESET);

                    printFiles(files);

                    break;
                case 7:
                    System.out.println(Color.GREEN + "Listing all files by name:" + Color.RESET);

                    files.sort(new CompareFilesByName());

                    printFiles(files);

                    files.sort(new CompareFilesById());


                    break;
                case 8:
                    System.out.println(Color.GREEN + "Listing all files by size:" + Color.RESET);

                    files.sort(new CompareFilesBySize());

                    printFiles(files);

                    files.sort(new CompareFilesById());

                    break;
                case 9:
                    System.out.println(Color.GREEN + "Listing all files by extension:" + Color.RESET);

                    files.sort(new CompareFilesByExtension());

                    printFiles(files);

                    files.sort(new CompareFilesById());

                    break;
                case 10:
                    System.out.println(Color.GREEN + "Listing all extensions:" + Color.RESET);
                    LinkedList<String> extensions = new LinkedList<String>();

                    for (int i = 0; i < files.size(); i++) {
                        String extension = files.get(i).getExtension();

                        if (!extensions.contains(extension)) {
                            extensions.add(extension);
                            System.out.println(Color.YELLOW + " · " + extension + Color.RESET);
                        }
                    }

                    break;
                case 11:
                    System.out.println(Color.RED + "Exiting" + Color.RESET);
                    break;
                default:
                    System.out.println(Color.RED + "Invalid option" + Color.RESET);
                    break;
            }
        } while (op != 11);
    }
}
