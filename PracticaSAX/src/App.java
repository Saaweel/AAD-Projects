import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

public class App extends DefaultHandler {
    private int indentLevel = 0;

    private void printIndentation() {
        for (int i = 0; i < indentLevel; i++) {
            System.out.print("\t");
        }
    }

    @Override
    public void startDocument() {
        System.out.println("<Begin>");
    }

    @Override
    public void startElement(String uri, String s, String qName, Attributes attributes) {
        printIndentation();
        System.out.print("<" + qName);
        for (int i = 0; i < attributes.getLength(); i++) {
            System.out.print(" " + attributes.getLocalName(i) + "=\"" + attributes.getValue(i) + "\"");
        }
        System.out.println(">");
        indentLevel++;
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        String text = new String(ch, start, length);
        if (text.trim().length() > 0) {
            printIndentation();
            System.out.println(text);
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        indentLevel--;
        printIndentation();
        System.out.println("</" + qName + ">");
    }

    @Override
    public void endDocument() {
        System.out.println("<End>");
    }

    public static void main(String[] args) {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();

            App app = new App();
            parser.parse("books.xml", app);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}