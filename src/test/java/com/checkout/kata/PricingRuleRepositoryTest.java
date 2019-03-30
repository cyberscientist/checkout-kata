package com.checkout.kata;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class PricingRuleRepositoryTest {
    PricingRuleRepository repository = new PricingRuleRepository();
    @Test
    public void can_add_single_pricing_rule() {
        PricingRule prcingRule = new PricingRule("A", 3, 130);
        repository.add(prcingRule);
        assertEquals(prcingRule, repository.getPricingRuleBySKUName("A"));
    }

    @Test
    public void can_load_pricing_rules_from_file() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("pricingRules.csv").getFile());
        repository.loadPricingRulesFromFile(file, false);
        assertNotNull(repository.getPricingRuleBySKUName("A"));
        assertNotNull(repository.getPricingRuleBySKUName("B"));
    }

}