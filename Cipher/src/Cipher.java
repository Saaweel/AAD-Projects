import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class Cipher {
	private int[] swaps;
	
	public Cipher(int password) {
		this.swaps = new int[256];
		for (int i=0; i<this.swaps.length; i++) {
			this.swaps[i]=-1;
		}
		shuffle(password);
	}

	public void encrypt(File src, File dst) {
		if (src.exists() && src.isFile()) {
			BufferedReader br = null;
			BufferedWriter bw = null;
			try {
				br = new BufferedReader(new FileReader(src));
				bw = new BufferedWriter(new FileWriter(dst));
				String line;
				while ((line = br.readLine()) != null) {
					line = this.doEncrypt(line);
					bw.write(line);
					bw.newLine();
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				close(br);
				close(bw);
			}
		} else {
			System.err.println("El fichero origen no existe");
		}
	}
	
	public void decrypt(File src, File dst) {
		if (src.exists() && src.isFile()) {
			BufferedReader br = null;
			BufferedWriter bw = null;
			try {
				br = new BufferedReader(new FileReader(src));
				bw = new BufferedWriter(new FileWriter(dst));
				String line;
				while ((line = br.readLine()) != null) {
					line = this.doDecrypt(line);
					bw.write(line);
					bw.newLine();
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				close(br);
				close(bw);
			}
		} else {
			System.err.println("El fichero origen no existe");
		}
	}

	private String doEncrypt(String s) {
		String result = "";
		
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			int pos = (int) c;
			int newPos = this.swaps[pos];
			char newChar = (char) newPos;
			result += newChar;
		}

		return result;
	}

	private String doDecrypt(String s) {
		String result = "";

		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			int pos = (int) c;
			int newPos = 0;
			for (int j = 0; j < this.swaps.length; j++) {
				if (this.swaps[j] == pos) {
					newPos = j;
					break;
				}
			}
			char newChar = (char) newPos;
			result += newChar;
		}

		return result;
	}
	
	private void shuffle(int password) {
		Random r = new Random(password);

		for (int i = 0; i < this.swaps.length; i++) {
			if (i >= 32 && i <= 126) {
				int random = r.nextInt(127 - 32) + 32;
				while (find(random)) {
					random = r.nextInt(127 - 32) + 32;
				}
				this.swaps[i] = random;
			} else {
				int random = r.nextInt(this.swaps.length);
				while (find(random) || (random >= 32 && random <= 126)) {
					random = r.nextInt(this.swaps.length);
				}
				this.swaps[i] = random;
			}
		}
	}
	
	private boolean find(int value) {
		for (int i = 0; i < this.swaps.length; i++) {
			if (this.swaps[i] == value) {
				return true;
			}
		}
		return false;
	}
	
	public static void close(Closeable c) {
	    if (c == null) return; 

	    try {
	        c.close();
	    } catch (IOException e) {
	        e.fillInStackTrace();
	    }
	}
	
	public static void main(String[] args) {
		Scanner readS = new Scanner(System.in);

		String msg = "";

		System.out.println("");

		do {
			System.out.print("Cypher [args]: ");
			msg = readS.nextLine();
			args = msg.split(" ");

			if(args.length==4){
				Cipher cipher = new Cipher(Integer.parseInt(args[1]));
				switch(args[0]){
					case "-c":
						System.out.println("Cifrando fichero '"+args[2]+"' con password: "+args[1]);
						cipher.encrypt(new File(args[2]), new File(args[3]));
						System.out.println("Fichero cifrado: "+args[3]);
					break;
					case "-d":
						System.out.println("Descifrando fichero '"+args[2]+"' con password: "+args[1]);
						cipher.decrypt(new File(args[2]), new File(args[3]));
						System.out.println("Fichero descifrado: "+args[3]);
					break;
					default:
						System.out.println("Operación no implementada");
					break;
				}
			}
			else{
				System.err.println("El programa sólo admite 4 argumentos");
				System.out.println("Para cifrar: -c password srcFile secretFile");
				System.out.println("Para descifrar: -d password secretFile dstFile");
			}
		} while (!msg.equals("exit"));

		readS.close();
	}
}
