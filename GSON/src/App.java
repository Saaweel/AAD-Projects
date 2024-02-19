import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class App {
    // Usando VSCode, se puede pulsar F5 y ejecutará el programa con el argumento directamente

    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            System.out.println("Debes ingresar la URL del JSON como argumento");
            return;
        }

        Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File(args[0])));
            Employee employee = gson.fromJson(reader, Employee.class);

            System.out.println(employee);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
