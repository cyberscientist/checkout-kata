package com.checkout.kata;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PricingRuleRepository {
    private static final String COMMA ="," ;
    private List<PricingRule> pricingRules = new ArrayList<>();
    public PricingRule getPricingRuleBySKUName(String itemName) {
        return pricingRules.stream()
                .filter(pricingRule -> pricingRule.getItemName().equals(itemName))
                .findAny().orElse(null);
    }

    public void add(PricingRule pricingRule) {
        pricingRules.add(pricingRule);
    }

    public void loadPricingRulesFromFile(File pricingRuleFile, boolean hasHeader) throws IOException {
        int linesToSkip = hasHeader ? 1: 0;
        InputStream inputFS = new FileInputStream(pricingRuleFile);
        BufferedReader br = new BufferedReader(new InputStreamReader(inputFS));
        pricingRules = br.lines().skip(linesToSkip).map(mapToItem).collect(Collectors.toList());
        br.close();
    }

    private Function<String, PricingRule> mapToItem = (line) -> {
        String[] p = line.split(COMMA);
        return  new PricingRule(p[0].toUpperCase(), Integer.valueOf(p[1]), Integer.valueOf(p[2]));
    };

    public boolean addNewPricingRule(String userInput) {

        return false;
    }
}
