package services;

import com.fasterxml.jackson.databind.ObjectMapper;
import dto.ProductRepository;
import lombok.AllArgsConstructor;
import models.MyRuntimeException;
import models.Product;
import parsers.XmlProductParser;
import utils.ListManager;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@AllArgsConstructor
public class ProductParserServiceImpl implements ProductParserService {
    private ProductRepository productRepository;
    private XmlProductParser xmlProductParser;
    private Integer batchSize;

    @Override
    public void parseFile(String filepath, Class classToParse) {

        Field[] res = classToParse.getDeclaredFields();

        List<String> names = Arrays.stream(res)
                .map(Field::getName)
                .collect(Collectors.toList());

        Map<String, Map<String, String>> productMap = xmlProductParser.parseXml(filepath, names);

        ObjectMapper objectMapper = new ObjectMapper();
        List<Product> productList = productMap.values().stream()
                .map(stringStringMap -> objectMapper.convertValue(stringStringMap, Product.class))
                .collect(Collectors.toList());

        List<List<Product>> batchesList = ListManager.partitions(productList, batchSize);
        productRepository.insertByBatches(batchesList);
    }
}
