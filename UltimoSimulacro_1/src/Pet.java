import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class Pet {
    private String name;
    private String age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void saveToMongoDB(MongoDatabase mongodb) {
        BasicDBObject dbObject = new BasicDBObject();

        dbObject.append("name", this.name);
        dbObject.append("age", this.age);

        MongoCollection<BasicDBObject> collection = mongodb.getCollection("pets", BasicDBObject.class);

        collection.insertOne(dbObject);
    }

    public BasicDBObject toBasicDBObject() {
        BasicDBObject dbObject = new BasicDBObject();
        
        dbObject.append("name", this.name);
        dbObject.append("age", this.age);

        return dbObject;
    }
}
