import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

public class App {
    public static void main(String[] args) {
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase database = mongoClient.getDatabase("Pokemon");


        // Seleccionar todos los podemos de tipo veneno (Poison).
        Bson filter = Filters.in("type", "Poison");
        FindIterable<Document> query1 = database.getCollection("Pokemon").find(filter);
        for (Document doc : query1) {
            System.out.println(doc.toJson());
        }

        // Seleccionar 1 pokemon cuyo huevo eclosione a los 2 km.
        Bson filter2 = Filters.eq("egg", "2 km");
        FindIterable<Document> query2 = database.getCollection("Pokemon").find(filter2).limit(1);
        for (Document doc : query2) {
            System.out.println(doc.toJson());
        }

        // Mostrar todos los pokemon de tipo Fuego cuyo id sea mayor que 70. Mostrar sólo nombre, id y tipo.
        Bson filter3 = Filters.and(Filters.gt("id", 70), Filters.eq("type", "Fire"));
        FindIterable<Document> query3 = database.getCollection("Pokemon").find(filter3).projection(new Document("name", 1).append("_id", 1).append("type", 1));
        for (Document doc : query3) {
            System.out.println(doc.toJson());
        }

        // Mostrar los pokemon cuyas debilidades contengan Poison, Ice o ambas.
        Bson filter4 = Filters.or(Filters.in("weaknesses", "Poison"), Filters.in("weaknesses", "Ice"));
        FindIterable<Document> query4 = database.getCollection("Pokemon").find(filter4);
        for (Document doc : query4) {
            System.out.println(doc.toJson());
        }

        // Actualizar los pokemon de tipo Poison para añadir un campo nuevo llamado: esPoison: true
        Bson filter5 = Filters.eq("type", "Poison");
        Bson update = new Document("$set", new Document("esPoison", true));
        database.getCollection("Pokemon").updateMany(filter5, update);

        // Eliminar todos los pokemon que no tengan huevo.
        Bson filter6 = Filters.eq("egg", "Not in Eggs");
        database.getCollection("Pokemon").deleteMany(filter6);

        // Además crea un método que haga lo siguiente:
        //     Crear una colección llamada test.
        //     Mostrar todas las colecciones de la db.
        //     Eliminar esa colección test.
        //     Volver a mostrar todas las colecciones de la db.
        testMethod(database);
    }

    public static void testMethod(MongoDatabase database) {
        database.createCollection("test");
        for (String name : database.listCollectionNames()) {
            System.out.println(name);
        }
        database.getCollection("test").drop();
        for (String name : database.listCollectionNames()) {
            System.out.println(name);
        }
    }
}