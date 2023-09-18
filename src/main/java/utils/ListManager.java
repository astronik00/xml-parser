package utils;

import models.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ListManager {
    public static <T> List<List<T>> partitions(List <T> listToSplit, int n) {
        List<List<T>> partitions = new ArrayList<>();

        for (int i = 0; i < listToSplit.size(); i += n) {
            partitions.add(listToSplit.subList(i, Math.min(i + n, listToSplit.size())));
        }

        return partitions;
    }

    public static List<Product> generateProductsData(int size) {
        return  IntStream.range(1, size)
                .mapToObj(x -> new Product(x, "Name" + x, "Type" + x, (double)x))
                .collect(Collectors.toList());
    }
}
