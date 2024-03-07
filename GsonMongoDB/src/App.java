import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class App {
    public static void main(String[] args) {
        List<Pokemon> pokemons = new ArrayList<Pokemon>();

        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase database = mongoClient.getDatabase("Pokemon");

        Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
        
        FindIterable<Document> query = database.getCollection("Pokemon").find();
        for (Document doc : query) {
            Pokemon pokemon = gson.fromJson(doc.toJson(), Pokemon.class);
            pokemons.add(pokemon);
        }

        List<String> tipos = new ArrayList<String>();
        for (Pokemon pokemon : pokemons) {
            for (String tipo : pokemon.getType()) {
                if (!tipos.contains(tipo)) {
                    tipos.add(tipo);
                }
            }
        }

        
        // Número total de pokemons.
        System.out.println("Número total de pokemons: " + pokemons.size());

        // Listado de los distintos tipos de pokemon que existen.
        System.out.println("Listado de los distintos tipos de pokemon que existen: " + tipos);

        // El tipo de pokemon que más se repite.
        int max = 0;
        String tipo = "";
        for (String t : tipos) {
            int count = 0;
            for (Pokemon pokemon : pokemons) {
                for (String p : pokemon.getType()) {
                    if (p.equals(t)) {
                        count++;
                    }
                }
            }
            if (count > max) {
                max = count;
                tipo = t;
            }
        }
        System.out.println("El tipo de pokemon que más se repite: " + tipo);
        
        // Las evoluciones del pokemon 133 (Eevee).
        for (Pokemon pokemon : pokemons) {
            if (pokemon.getNum().equals("133")) {
                System.out.println("Evolución previa del pokemon 133 (Eevee): " + pokemon.getPrev_evolution());
                System.out.println("Evolución siguiente del pokemon 133 (Eevee): " + pokemon.getNext_evolution());
            }
        }
    }
}