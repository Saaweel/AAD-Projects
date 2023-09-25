package com.saaweel;

import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Console {
	public static Scanner readS = new Scanner(System.in);

	public void ls(String path){
		if (path == null) {
			path = System.getProperty("user.dir");
		}

		Path dir = Paths.get(path);

		if (Files.isDirectory(dir)) {
			System.out.println(Color.GREEN + "Directory of " + Color.ORANGE + path + Color.RESET);
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
			System.out.println(Color.RED + "Error: " + path + " is not a directory" + Color.RESET);
		}
	}

	public void cp(){

	}

	public void mv(){

	}

	public void rm(){

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
						if (args.length > 1)
							console.ls(args[1]);
						else
							console.ls(null);
						break;
					case "cp":
						console.cp();
						break;
					case "mv":
						console.mv();
						break;
					case "rm":
						console.rm();
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