package parsers;

import models.Product;

import java.util.List;
import java.util.Optional;

public interface XmlProductParser {
    Optional<List<Product>> parseXml(String filepath);
}
