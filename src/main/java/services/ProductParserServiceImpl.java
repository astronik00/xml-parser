package services;

import dto.ProductRepository;
import lombok.AllArgsConstructor;
import models.Product;
import parsers.XmlProductParser;
import utils.ListManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class ProductParserServiceImpl implements ProductParserService {
    private ProductRepository productRepository;
    private XmlProductParser xmlProductParser;
    private Integer batchSize;

    @Override
    public void parseFile(String filepath) {
        List<Product> productList;
        Optional<List<Product>> result = xmlProductParser.parseXml(filepath);

        if (!result.isPresent())
            return;

        productList = result.get();

        List<List<Product>> batchesList = ListManager.partitions(productList, batchSize);
        productRepository.insertByBatches(batchesList);
    }
}
