package parsers;

import models.MyRuntimeException;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class StaxProductParser implements XmlProductParser{
    @Override
    public Map<String, Map<String, String>> parseXml(String inputFile, List<String> fieldNames) {
        Map<String, Map<String, String>> productMap = new HashMap<>();

        Map<String, String> res = fieldNames
                .stream()
                .collect(Collectors.toMap(x -> x, x -> ""));

        try {
            XMLInputFactory inputFactory = XMLInputFactory.newInstance();
            InputStream in = Files.newInputStream(Paths.get(inputFile));
            XMLStreamReader streamReader = inputFactory.createXMLStreamReader(in);

            while (streamReader.hasNext()) {
                if (streamReader.isStartElement()) {
                    String name = streamReader.getLocalName();

                    if (res.containsKey(name)) {
                        String value = streamReader.getElementText();
                        res.put(name, value);
                    }
                }

                if (res.values().stream().noneMatch(String::isEmpty)) {
                    productMap.put(res.get("id"), res);

                    res = fieldNames
                            .stream()
                            .collect(Collectors.toMap(x -> x, x -> ""));
                }

                streamReader.next();
            }

            return productMap;

        } catch (XMLStreamException e) {
            throw new MyRuntimeException("1", "Can't parse " + inputFile);
        } catch (IOException e) {
            throw new MyRuntimeException("2", "Can't open " + inputFile);
        }
    }
}

