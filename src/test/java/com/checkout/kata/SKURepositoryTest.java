package com.checkout.kata;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class SKURepositoryTest {
    SKURepository repository = new SKURepository();
    @Test
    public void load_sku_from_file() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("sku.csv").getFile());
        repository.loadSKUsFromFile(file, false);
        assertNotNull(repository.getItemByName("A"));
        assertNotNull(repository.getItemByName("B"));
        assertNotNull(repository.getItemByName("C"));
    }


}