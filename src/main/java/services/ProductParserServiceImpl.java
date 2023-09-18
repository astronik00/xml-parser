package services;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import models.Product;
import parsers.XmlProductParser;
import repositories.ProductRepository;
import utils.ListManager;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
public class ProductParserServiceImpl implements ParserService {
    private ProductRepository productRepository;
    private XmlProductParser xmlProductParser;
    private Integer batchSize;

    @Override
    public void parseFile(String filepath) {
        List<String> productFields = Arrays.stream(Product.class.getDeclaredFields())
                .map(Field::getName)
                .collect(Collectors.toList());

        Map<String, Map<String, String>> productMap = xmlProductParser.parseXml(filepath, productFields);

        List<Product> productList = productMap.values().stream()
                .map(stringStringMap -> new ObjectMapper().convertValue(stringStringMap, Product.class))
                .collect(Collectors.toList());

        List<List<Product>> batchesList = ListManager.partitions(productList, batchSize);
        productRepository.insertByBatches(batchesList);
    }
}
