import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import com.mongodb.client.FindIterable;

public class App {
    public static void main(String[] args) throws Exception {
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase database = mongoClient.getDatabase("Pokedex");

        // Seleccionar los 10 primeros pokemon que tengan 2 evoluciones siguientes. Ordenar la salida por nombre del pokemon Ascendente (Z a la A)
        Bson filter = Filters.size("next_evolution", 2);
        FindIterable<Document> query1 = database.getCollection("pokemons").find(filter).limit(10).sort(Filters.eq("name", -1));
        for (Document doc : query1) {
            System.out.println(doc.toJson());
        }

        // Seleccionar el segundo pokemon cuyo huevo eclosione a los 2 km.
        Bson filter2 = Filters.eq("egg", "2 km");
        FindIterable<Document> query2 = database.getCollection("pokemons").find(filter2).limit(1).skip(1);
        for (Document doc : query2) {
            System.out.println(doc.toJson());
        }

        // Mostrar todos los pokemon cuyo id sea mayor a 50 y menor que 60.
        Bson filter3 = Filters.and(Filters.gt("_id", 50), Filters.lt("_id", 60));
        FindIterable<Document> query3 = database.getCollection("pokemons").find(filter3);
        for (Document doc : query3) {
            System.out.println(doc.toJson());
        }
        
        // Mostrar todos los pokemon que sean una evolución intermedia (es decir, que tenga evolución previa y evolución siguiente). Se deberá mostrar sólo el nombre de ese pokemon, de la evolución previa y de la evolución siguiente.
        Bson filter4 = Filters.and(Filters.exists("next_evolution"), Filters.exists("prev_evolution"));
        Bson projection = Projections.fields(Projections.include("name"), Projections.include("next_evolution.name"), Projections.include("prev_evolution.name"));
        FindIterable<Document> query4 = database.getCollection("pokemons").find(filter4).projection(projection);
        for (Document doc : query4) {
            System.out.println(doc.toJson());
        }

        // Mostrar los pokemon que tengan el campo candy_count y que "Grass" NO esté entre sus debilidades.
        Bson filter5 = Filters.and(Filters.exists("candy_count"), Filters.not(Filters.in("weaknesses", "Grass")));
        FindIterable<Document> query5 = database.getCollection("pokemons").find(filter5);
        for (Document doc : query5) {
            System.out.println(doc.toJson());
        }

        mongoClient.close();
    }
}