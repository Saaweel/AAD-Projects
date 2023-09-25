package com.saaweel;

public class Console {
	public void ls(){

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

		if (args.length > 0) {
			switch (args[0]) {
			case "ls":
				console.ls();
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
	}
}