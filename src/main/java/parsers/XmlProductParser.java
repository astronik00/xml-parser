package parsers;

import models.Product;

import java.util.List;

public interface XmlProductParser {
    List<Product> parseXml(String filepath);
}
