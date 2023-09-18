import com.fasterxml.jackson.databind.ObjectMapper;
import configurations.AppConfig;
import dto.ProductJdbcRepository;
import dto.ProductRepository;
import models.MyRuntimeException;
import models.Product;
import parsers.StaxProductParser;
import parsers.XmlProductParser;
import services.ParserService;
import services.ProductParserServiceImpl;
import utils.FileManager;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        try {
            long start = System.currentTimeMillis();
            File file = FileManager.getResourceFile("properties.json");
            ObjectMapper mapper = new ObjectMapper();

            AppConfig appConfig = mapper.readValue(file, AppConfig.class);

            ProductRepository productRepository = new ProductJdbcRepository(
                    appConfig.getUrl(),
                    appConfig.getUser(),
                    appConfig.getPassword());

            XmlProductParser staxParser = new StaxProductParser();

            ParserService parserService = new ProductParserServiceImpl(
                    productRepository,
                    staxParser,
                    appConfig.getBatchSize()
            );

            String filepath = FileManager.getAbsoluteFilePath("products.xml");

            parserService.parseFile(filepath);
            long end = System.currentTimeMillis();
            System.out.println(end - start);

        } catch (MyRuntimeException e) {
            switch (e.getNumber()) {
                case "23000":
                    System.out.print("SqlException: Duplicate primary keys were found. ");
                    break;
                case "08S01" :
                case "08001" :
                    System.out.print("SqlException: Wrong url credentials. ");
                    break;
                case "28000":
                    System.out.print("SqlException: Wrong username or password. ");
                    break;
                case "42000":
                    System.out.print("SqlException: Wrong db name. ");
                    break;
            }
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("Exception: Unknown exception. " + e.getMessage());
        }
    }
}
