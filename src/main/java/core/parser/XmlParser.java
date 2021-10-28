package core.parser;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class XmlParser implements Parser {

    @Override
    public List<Map<String, ?>> parseData(File file) {
        // Instantiate the Factory
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        final List<Map<String, ?>> items = new ArrayList<>();

        try {
            dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);

            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);
            doc.getDocumentElement().normalize();

            Element root = (Element) doc.getElementsByTagName("galaxy").item(0).getChildNodes();

            NodeList planets = root.getElementsByTagName("planet");
            NodeList asteroids = root.getElementsByTagName("asteroid");

            parseNodelist(planets, items);
            parseNodelist(asteroids, items);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        return items;
    }

    private void parseNodelist(NodeList list, List<Map<String, ?>> map ) {
        for (int i = 0; i < list.getLength(); ++i) {
            final Map<String, Object> lineData = new HashMap<>();
            Node node = list.item(i);
            if(!node.getParentNode().getNodeName().equalsIgnoreCase("galaxy")) continue;

            if (node.getNodeType() == Node.ELEMENT_NODE) {

                Element element = (Element) node;

                Node nameNode = element.getElementsByTagName("name").item(0);
                String name = null;
                if(nameNode != null){
                    name = element.getElementsByTagName("name").item(0).getTextContent();
                }

                Element position = (Element) element.getElementsByTagName("position").item(0).getChildNodes();
                String posX = position.getElementsByTagName("x").item(0).getTextContent();
                String posY = position.getElementsByTagName("y").item(0).getTextContent();
                String radius = position.getElementsByTagName("radius").item(0).getTextContent();

                Element speed = (Element) element.getElementsByTagName("speed").item(0).getChildNodes();
                String velX = speed.getElementsByTagName("x").item(0).getTextContent();
                String velY = speed.getElementsByTagName("y").item(0).getTextContent();

                Node neighbours = element.getElementsByTagName("neighbours").item(0);
                final List<String> neighbourNames = new ArrayList<>();
                if(neighbours != null){
                    for(int neighbour = 0; neighbour < neighbours.getChildNodes().getLength(); ++neighbour) {
                        Node current = neighbours.getChildNodes().item(neighbour);
                        if(current != null && current.getNodeType() == Node.ELEMENT_NODE && current.getNodeName().equals("planet")) {
                            neighbourNames.add(current.getTextContent());
                        }
                    }
                }

                String colour = element.getElementsByTagName("color").item(0).getTextContent();
                String onCollision = element.getElementsByTagName("oncollision").item(0).getTextContent();

                lineData.put("name", name);
                lineData.put("type", node.getNodeName());
                lineData.put("x", Float.parseFloat(posX));
                lineData.put("y", Float.parseFloat(posY));
                lineData.put("vx", Float.parseFloat(velX));
                lineData.put("vy", Float.parseFloat(velY));
                lineData.put("radius", Float.parseFloat(radius));
                lineData.put("color", colour);
                lineData.put("oncollision", onCollision);

                if(neighbourNames.size() > 0){
                    StringBuilder commaList = new StringBuilder();
                    String  lastNeighbour = neighbourNames.get(neighbourNames.size()-1);
                    for(String neighbour: neighbourNames){
                        commaList.append(neighbour);
                        if(!neighbour.equals(lastNeighbour)){
                            commaList.append(',');
                        }
                    }
                    lineData.put("neighbours", commaList.toString());
                } else {
                    lineData.put("neighbours", "");

                }

                map.add(lineData);
            }
        }
    }

}
