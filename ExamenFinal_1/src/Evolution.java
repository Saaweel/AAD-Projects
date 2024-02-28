import com.mongodb.BasicDBObject;

public class Evolution {
    private String name;
    private String method;

    public Evolution(String name, String method) {
        this.name = name;
        this.method = method;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public BasicDBObject toBasicDBObject() {
        BasicDBObject dbObject = new BasicDBObject();
        
        dbObject.append("name", this.name);
        dbObject.append("method", this.method);

        return dbObject;
    }

    @Override
    public String toString() {
        return "Evolution [name=" + name + ", method=" + method + "]";
    }
}
