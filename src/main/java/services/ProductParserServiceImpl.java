package services;

import dto.ProductRepository;
import lombok.AllArgsConstructor;
import models.MyRuntimeException;
import models.Product;
import parsers.XmlProductParser;
import utils.ListManager;

import java.util.List;

@AllArgsConstructor
public class ProductParserServiceImpl implements ProductParserService {
    private ProductRepository productRepository;
    private XmlProductParser xmlProductParser;
    private Integer batchSize;

    @Override
    public void parseFile(String filepath) {
        List<Product> productList = xmlProductParser.parseXml(filepath);
        List<List<Product>> batchesList = ListManager.partitions(productList, batchSize);
        productRepository.insertByBatches(batchesList);
    }
}
