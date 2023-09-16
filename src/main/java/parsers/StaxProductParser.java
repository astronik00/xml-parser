package parsers;

import models.Product;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StaxProductParser implements XmlProductParser {
    @Override
    public List<Product> parseXml(String inputFile) {
        Product newProduct = new Product();
        Map<Integer, Product> productMap = new HashMap<>();

        try {
            XMLInputFactory inputFactory = XMLInputFactory.newInstance();
            InputStream in = Files.newInputStream(Paths.get(inputFile));
            XMLStreamReader streamReader = inputFactory.createXMLStreamReader(in);

            boolean id = false, name = false, type = false;

            while (streamReader.hasNext()) {
                if (streamReader.isStartElement()) {
                    switch (streamReader.getLocalName()) {
                        case "id": {
                            id = true;
                            newProduct.setId(Integer.parseInt(streamReader.getElementText()));
                            break;
                        }
                        case "name": {
                            name = true;
                            newProduct.setName(streamReader.getElementText());
                            break;
                        }
                        case "type": {
                            type = true;
                            newProduct.setType(streamReader.getElementText());
                            break;
                        }
                    }
                }

                if (type && id && name) {
                    id = false;
                    name = false;
                    type = false;
                    productMap.put(newProduct.getId(), newProduct);
                    newProduct = new Product();
                }

                streamReader.next();

            }
            return new ArrayList<>(productMap.values());

        } catch (XMLStreamException | IOException e) {
            System.out.println("error during parsing xml file");
            return null;
        }
    }
}
