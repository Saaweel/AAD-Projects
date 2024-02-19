import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

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

            Type listOfPersona = new TypeToken<List<Persona>>() {}.getType();

            List<Persona> personas = gson.fromJson(reader, listOfPersona);

            for (Persona persona : personas) {
                System.out.println(persona);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
