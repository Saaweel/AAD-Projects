import org.bson.Document;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class App {
    public static void main(String[] args) throws Exception {
        // Connection to db
        String uri = "mongodb://localhost:27017";
        MongoClient mongoClient = MongoClients.create(uri);
        System.out.println("Connected successfully to server");

        // Testing with db and collections
        MongoDatabase database = mongoClient.getDatabase("Pokedex");
        database.createCollection("test");
        MongoCollection<Document> collectionTest = database.getCollection("test");
        System.out.println("-------COLLECTIONS-------");
        for (String name : database.listCollectionNames()) {
            System.out.println(name);
        }
        collectionTest.drop();
        System.out.println("-------DELETE test-------");
        for (String name : database.listCollectionNames()) {
            System.out.println(name);
        }
    }
}