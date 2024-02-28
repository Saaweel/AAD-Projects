import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.*;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class App {
    private List<Pokemon> pokemons;

    public App() {
        this.pokemons = new ArrayList<Pokemon>();
    }

    public void readPokemonsFromXMLNode(Node node) throws Exception {
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            switch (node.getNodeName()) {
                case "pokemon":
                    Pokemon pokemon = new Pokemon();

                    this.readPokemonDataFromXMLNode(node, pokemon);

                    this.pokemons.add(pokemon);
                    break;
                default:
                    NodeList children = node.getChildNodes();
                    for (int i = 0; i < children.getLength(); i++) {
                        this.readPokemonsFromXMLNode(children.item(i));
                    }
                    break;
            }
        } else {
            NodeList children = node.getChildNodes();
            for (int i = 0; i < children.getLength(); i++) {
                this.readPokemonsFromXMLNode(children.item(i));
            }
        }
    }

    public void readPokemonDataFromXMLNode(Node node, Pokemon pokemon) throws Exception {
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            switch (node.getNodeName()) {
                case "name":
                    pokemon.setName(node.getTextContent());
                    break;
                case "type":
                    pokemon.setType(node.getTextContent());
                    break;
                case "description":
                    pokemon.setDescription(node.getTextContent());
                    break;
                case "evolution":
                    pokemon.addEvolution(new Evolution(node.getAttributes().getNamedItem("name").getNodeValue(), node.getAttributes().getNamedItem("method").getNodeValue()));
                    break;
                default:
                    NodeList childNodes = node.getChildNodes();
                    for (int i = 0; i < childNodes.getLength(); i++) {
                        this.readPokemonDataFromXMLNode(childNodes.item(i), pokemon);
                    }
                    break;
            }
        } 
    }

    // private void showPokemons() {
    //     // TODO Remove debug method
    //     for (Pokemon pokemon : this.pokemons) {
    //         System.out.println(pokemon);
    //     }
    // }

    private void saveToMongoDB() {
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase database = mongoClient.getDatabase("Pokedex");

        for (Pokemon pokemon : this.pokemons) {
            pokemon.saveToMongoDB(database);
        }
    }

    public static void main(String[] args) throws Exception {
        App app = new App();

        File file = new File("pokemons.xml");

        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);

            app.readPokemonsFromXMLNode(doc);

            // app.showPokemons();

            app.saveToMongoDB();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}