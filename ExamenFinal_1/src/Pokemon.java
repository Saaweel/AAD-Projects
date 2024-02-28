import java.util.ArrayList;
import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class Pokemon {
    private String name;
    private String type;
    private String description;
    private List<Evolution> evolutions;

    public Pokemon() {
        this.evolutions = new ArrayList<Evolution>();
    }

    public Pokemon(String name, String type, String description) {
        this.name = name;
        this.type = type;
        this.description = description;
        this.evolutions = new ArrayList<Evolution>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void addEvolution(Evolution evolution) {
        this.evolutions.add(evolution);
    }

    
    public void removeEvolution(Evolution evolution) {
        this.evolutions.remove(evolution);
    }

    public Evolution getEvolution(int index) {
        return this.evolutions.get(index);
    }

    public int getEvolutionCount() {
        return this.evolutions.size();
    }

    public void saveToMongoDB(MongoDatabase mongodb) {
        BasicDBObject dbObject = new BasicDBObject();

        dbObject.append("name", this.name);
        dbObject.append("type", this.type);
        dbObject.append("description", this.description);
        BasicDBObject[] evolutionsObject = new BasicDBObject[this.evolutions.size()];

        for (int i = 0; i < this.evolutions.size(); i++) {
            evolutionsObject[i] = this.evolutions.get(i).toBasicDBObject();
        }

        dbObject.append("evolutions", evolutionsObject);

        MongoCollection<BasicDBObject> collection = mongodb.getCollection("pokemons", BasicDBObject.class);

        collection.insertOne(dbObject);
    }

    @Override
    public String toString() {
        return "Pokemon [name=" + name + ", type=" + type + ", description=" + description + ", evolutions="
                + evolutions + "]";
    }
}
