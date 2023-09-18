package utils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ListManager <T> {
    // slower... but more beautiful...
    public static <T> List<List<T>> partitions_copy(List <T> listToSplit, int n) {
        return IntStream.range(0, listToSplit.size())
                .filter(i -> i % n == 0)
                .mapToObj(i -> listToSplit.subList(i, Math.min(i + n, listToSplit.size())))
                .collect(Collectors.toList());
    }

    // faster... but less beautiful...
    public static <T> List<List<T>> partitions(List <T> listToSplit, int n) {
        List<List<T>> partitions = new ArrayList<>();

        for (int i = 0; i < listToSplit.size(); i += n) {
            partitions.add(listToSplit.subList(i, Math.min(i + n, listToSplit.size())));
        }

        return partitions;
    }
}
