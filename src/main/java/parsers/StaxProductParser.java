package parsers;

import models.Product;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class StaxProductParser implements XmlProductParser {
    @Override
    public Optional<List<Product>> parseXml(String inputFile) {
        Product newProduct = new Product();
        Map<Integer, Product> productMap = new HashMap<>();

        try {
            XMLInputFactory inputFactory = XMLInputFactory.newInstance();
            InputStream in = Files.newInputStream(Paths.get(inputFile));
            XMLStreamReader streamReader = inputFactory.createXMLStreamReader(in);

            while (streamReader.hasNext()) {
                if (streamReader.isStartElement()) {
                    switch (streamReader.getLocalName()) {
                        case "id": {
                            newProduct.setId(Integer.parseInt(streamReader.getElementText()));
                            break;
                        }
                        case "name": {
                            newProduct.setName(streamReader.getElementText());
                            break;
                        }
                        case "type": {
                            newProduct.setType(streamReader.getElementText());
                            break;
                        }
                    }
                }

                if (newProduct.getId() != null
                        && newProduct.getName() != null
                        && newProduct.getType() != null) {
                    productMap.put(newProduct.getId(), newProduct);
                    newProduct = new Product();
                }

                streamReader.next();

            }
            return Optional.of(new ArrayList<>(productMap.values()));

        } catch (XMLStreamException e) {
            System.out.println("can't parse xml file");
            return Optional.empty();
        } catch (IOException e) {
            System.out.println("can't open file " + inputFile);
            return Optional.empty();
        }
    }
}

