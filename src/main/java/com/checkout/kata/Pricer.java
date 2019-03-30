package com.checkout.kata;

import java.text.DecimalFormat;
import java.util.Map;
import java.util.Optional;

public class Pricer {
    private CheckoutIO io;
    private PricingRuleRepository pricingRuleRepository;
    private DecimalFormat decimalFormat = new DecimalFormat("0.##");

    public Pricer(CheckoutIO io, PricingRuleRepository pricingRuleRepository) {
        this.io = io;
        this.pricingRuleRepository = pricingRuleRepository;
    }

    private static double calculateSimpleTotal(double price, double total, int noInBasket) {
        return total + price * noInBasket;
    }

    private double applyPricer(Map.Entry<SKU, Integer> basketEntry) {
        double total = 0;
        int noInBasket = basketEntry.getValue();
        String name = basketEntry.getKey().getName();
        Optional<PricingRule> pricingRule = Optional.ofNullable(pricingRuleRepository.getPricingRuleBySKUName(name));
        int price = basketEntry.getKey().getPrice();
        if (pricingRule.isPresent()) {
            total = getTotalForDiscountedItems(noInBasket, pricingRule.get(), price);
        } else {
            total = calculateSimpleTotal(price, total, noInBasket);
        }

        return total;
    }

    private double getTotalForDiscountedItems(int noInBasket, PricingRule pricingRule, int price) {
        double total;
        int numberOfItemsToDiscount = noInBasket / pricingRule.getNumberOfItems();
        total = numberOfItemsToDiscount * pricingRule.getPrice();
        int remainingItems = noInBasket - (numberOfItemsToDiscount * pricingRule.getNumberOfItems());
        total = calculateSimpleTotal(price, total, remainingItems);
        return total;
    }

    public void price(Map<SKU, Integer> basket) {
        double totals = basket.entrySet().stream()
                .mapToDouble(this::applyPricer)
                .sum();

        display("Total: " + decimalFormat.format(totals));
    }

    private void display(String price) {
        io.display(String.valueOf(price));
    }

}
