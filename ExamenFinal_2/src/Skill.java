import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class Skill {
    private String name;
    private String level;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLevel() {
        return level;
    }
    
    public void setLevel(String level) {
        this.level = level;
    }

    public Node toDomNode(Document doc) {
        Element xmlSkill = doc.createElement("skill");

        xmlSkill.setAttribute("name", this.name);
        xmlSkill.setAttribute("level", this.level);

        return xmlSkill;
    }

    @Override
    public String toString() {
        return "Skills [name=" + name + ", level=" + level + "]";
    }
}
