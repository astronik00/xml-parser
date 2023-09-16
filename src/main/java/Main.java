import com.fasterxml.jackson.databind.ObjectMapper;
import configurations.AppConfig;
import dto.ProductRepository;
import dto.ProductJdbcRepository;
import parsers.StaxProductParser;
import parsers.XmlProductParser;
import services.ProductParserService;
import services.ProductParserServiceImpl;
import utils.FileManager;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        File file = FileManager.getResourceFile("properties.json");
        ObjectMapper mapper = new ObjectMapper();

        AppConfig appConfig = mapper.readValue(file, AppConfig.class);

        ProductRepository productRepository = new ProductJdbcRepository(
                appConfig.getUrl(),
                appConfig.getUser(),
                appConfig.getPassword());

        XmlProductParser staxParser = new StaxProductParser();

        ProductParserService productParserService = new ProductParserServiceImpl(
                productRepository,
                staxParser,
                appConfig.getBatchSize()
        );

        String filepath = FileManager.getAbsoluteFilePath("products.xml");

        productParserService.parseFile(filepath);
    }
}
