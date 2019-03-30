package com.checkout.kata;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Checkout {
    private static final String CHECKOUT_STRING = "CHECKOUT";
    private SKURepository SKURepository;
    private CheckoutIO checkoutIO;
    private Map<SKU, Integer> basket = new HashMap<>();
    private Pricer pricer;

    public Checkout(SKURepository SKURepository, CheckoutIO checkoutIO, Pricer pricer) {
        this.SKURepository = SKURepository;
        this.checkoutIO = checkoutIO;
        this.pricer = pricer;
    }

    public void loadSKUFromFile(String uri) {
        File skuFile = new File(uri);
        try {
            SKURepository.loadSKUsFromFile(skuFile, true);
        } catch (IOException e) {
            checkoutIO.display("Could not load SKUs from the files. " + e);
        }
    }

    public void scanItems() {
        checkoutIO.display("Enter skus (type 'checkout' to finish and see total): ");
        while (checkoutIO.scannerHasNext()) {
            String input = checkoutIO.getScannerNextLine().toUpperCase().trim();
            if(input.isEmpty()) {
                continue;
            } else if (CHECKOUT_STRING.equals(input)) {
                pricer.price(basket);
                break;
            } else {

                SKU SKUByName = SKURepository.getItemByName(input);
                addItemToBasket(input, SKUByName);
            }
        }
    }

    private void addItemToBasket(String input, SKU SKUByName) {
        if (SKUByName == null) {
            display(input);
        } else if (basket.get(SKUByName) == null) {
            basket.put(SKUByName, 1);
        } else {
            int currentQuantity = basket.get(SKUByName);
            int newQuantity = currentQuantity + 1;
            basket.put(SKUByName, newQuantity);
        }
    }

    private void display(String input) {
        checkoutIO.display("Found no SKU with name '" + input + "' was found, please try again.");
    }

    public Map<SKU, Integer> getBasket() {
        return this.basket;
    }


}
