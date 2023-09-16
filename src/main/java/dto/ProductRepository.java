package dto;

import models.Product;

import java.util.List;

public interface ProductRepository {
    void insertByOne(List<Product> productList);
    void insertByBatches(List<List<Product>> productBatchesList);
}
