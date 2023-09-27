package com.saaweel;

import java.io.File;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Console {
	public static Scanner readS = new Scanner(System.in);

	private void recursiveDelete(Path path) throws Exception {
		DirectoryStream<Path> stream = Files.newDirectoryStream(path);

		for (Path file : stream) {
			if (Files.isDirectory(file)) {
				recursiveDelete(file);
			} else {
				Files.delete(file);
			}
		}

		Files.delete(path);
	}

	public void ls(String path){
		if (path == null) {
			path = System.getProperty("user.dir");
		}

		Path dir = Paths.get(path);

		if (Files.isDirectory(dir)) {
			System.out.println(Color.GREEN + "Directorio " + Color.ORANGE + path + Color.RESET);
			System.out.println("--------------------------------------------------");
			
			try {
				DirectoryStream<Path> stream = Files.newDirectoryStream(dir);

				for (Path file : stream) {
					if (Files.isDirectory(file)) {
						System.out.println(Color.BLUE + file.getFileName() + Color.RESET);
					} else {
						System.out.println(file.getFileName());
					}
				}
			} catch (Exception e) {
				System.out.println(Color.RED + "Error: " + e.getMessage() + Color.RESET);
			}
		} else {
			System.out.println(Color.RED + "Error: " + path + " no es un directorio" + Color.RESET);
		}
	}

	public void cp(String fromFile, String toDirectory){
		Path from = Paths.get(fromFile);

		if (Files.exists(from)) {
			Path to = Paths.get(toDirectory);

			if (Files.isDirectory(to)) {
				try {
					Files.copy(from, to.resolve(from.getFileName()));
					System.out.println(Color.GREEN + "Archivo copiado" + Color.RESET);
				} catch (Exception e) {
					System.out.println(Color.RED + "Error: " + e.getMessage() + Color.RESET);
				}
			} else {
				System.out.println(Color.RED + "Error: " + toDirectory + " no es un directorio" + Color.RESET);
			}
		} else {
			System.out.println(Color.RED + "Error: " + fromFile + " no existe" + Color.RESET);
		}
	}

	public void mv(String fromFile, String toDirectory){
		Path from = Paths.get(fromFile);

		if (Files.exists(from)) {
			Path to = Paths.get(toDirectory);

			if (Files.isDirectory(to)) {
				try {
					Files.move(from, to.resolve(from.getFileName()));
					System.out.println(Color.GREEN + "Archivo copiado" + Color.RESET);
				} catch (Exception e) {
					System.out.println(Color.RED + "Error: " + e.getMessage() + Color.RESET);
				}
			} else {
				try {
					from.toFile().renameTo(new File(from.getParent().toString() + "/" + toDirectory));
					System.out.println(Color.GREEN + "Archivo renombrado" + Color.RESET);
				} catch (Exception e) {
					System.out.println(Color.RED + "Error: " + e.getMessage() + Color.RESET);
				}
			}
		} else {
			System.out.println(Color.RED + "Error: " + fromFile + " no existe" + Color.RESET);
		}
	}

	public void rm(String path, String option){
		Path file = Paths.get(path);

		if (Files.exists(file)) {
			if (Files.isDirectory(file)) {
				if (option.equals("-r")) {
					try {
						recursiveDelete(file);
						System.out.println(Color.GREEN + "Directorio eliminado" + Color.RESET);
					} catch (Exception e) {
						System.out.println(Color.RED + "Error: " + e.getMessage() + Color.RESET);
					}
				} else {
					System.out.println(Color.RED + "Error: " + path + " es un directorio, para borrarlo utilice " + Color.BLUE + "-r" + Color.RED + " como opción" + Color.RESET);
				}
			} else {
				try {
					Files.delete(file);
					System.out.println(Color.GREEN + "Archivo eliminado" + Color.RESET);
				} catch (Exception e) {
					System.out.println(Color.RED + "Error: " + e.getMessage() + Color.RESET);
				}
			}
		} else {
			System.out.println(Color.RED + "Error: " + path + " no existe" + Color.RESET);
		}
	}

	public void mkdir(){

	}

	public void touch(){

	}

	public void grep(){

	} 

	public static void main(String[] args) {
		Console console = new Console();

		String msg = "";

		System.out.println("");

		do {
			System.out.print(Color.GREEN + "saaweel@saaweel: " + Color.CYAN + System.getProperty("user.dir") + Color.RESET + "$ ");
			msg = readS.nextLine();
			args = msg.split(" ");

			System.out.println("");

			if (args.length > 0) {
				switch (args[0]) {
					case "ls":
						if (args.length == 1)
							console.ls(null);
						else
							if (args.length == 2)
								console.ls(args[1]);
							else
								System.out.println(Color.RED + "Error de formato: ls <ruta (opcional)>" + Color.RESET);
						break;
					case "cp":
					System.out.println(args.length);
						if (args.length == 3)
							console.cp(args[1], args[2]);
						else
							System.out.println(Color.RED + "Error de formato: cp <archivo> <directorio>" + Color.RESET);
						break;
					case "mv":
						if (args.length == 3)
							console.mv(args[1], args[2]);
						else
							System.out.println(Color.RED + "Error de formato: mv <archivo> <directorio/nombre>" + Color.RESET);
						break;
					case "rm":
						if (args.length == 2)
							console.rm(args[1], null);
						else
							if (args.length == 3)
								console.rm(args[1], args[2]);
							else
								System.out.println(Color.RED + "Error de formato: rm <ruta> <opcion (opcional)>" + Color.RESET);
						break;
					case "mkdir":
						console.mkdir();
						break;
					case "touch":
						console.touch();
						break;
					case "grep":
						console.grep();
						break;
				}
			}

			System.out.println("");
		} while (msg != "exit");
	}
}