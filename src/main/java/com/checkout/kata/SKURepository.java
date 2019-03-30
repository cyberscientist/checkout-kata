package com.checkout.kata;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SKURepository {
    private List<SKU> skus = new ArrayList<>();

    public SKU getItemByName(String itemName) {
        return skus.stream()
                .filter(item -> item.getName().equals(itemName))
                .findAny()
                .orElse(null);
    }

    public void addItem(SKU SKU) {
        skus.add(SKU);
    }

    public void loadSKUsFromFile(File file, boolean hasheader) throws IOException {
        int linesToSkip = hasheader ? 1: 0;
        InputStream inputFS = new FileInputStream(file);
        BufferedReader br = new BufferedReader(new InputStreamReader(inputFS));
        skus = br.lines()
                .skip(linesToSkip)
                .map(mapToItem).collect(Collectors.toList());
        br.close();

    }

    private Function<String, SKU> mapToItem = (line) -> {
        String[] p = line.split(",");
        return new SKU(p[0], Integer.valueOf(p[1]));
    };
}
