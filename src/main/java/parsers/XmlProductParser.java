package parsers;

import models.Product;

import java.util.List;
import java.util.Map;

public interface XmlProductParser {
    Map<String, Map<String, String>> parseXml(String filepath, List<String> fieldNames);
}
