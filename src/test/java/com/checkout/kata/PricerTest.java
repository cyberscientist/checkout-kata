package com.checkout.kata;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.HashMap;

import static org.mockito.Mockito.*;

class PricerTest {


    private Pricer pricer;
    private @Mock
    CheckoutIO checkoutIO = mock(CheckoutIO.class);
    private @Mock
    PricingRuleRepository PricingRuleRepository = mock(PricingRuleRepository.class);

    @BeforeEach
    private void init() {
        pricer = new Pricer(checkoutIO, PricingRuleRepository);
    }

    @Test
    public void price_individual_SKUs() {
        HashMap<SKU,Integer> SKUs = new HashMap<SKU,Integer>()
        {{put(new SKU("A",50),1);}};
        pricer.price(SKUs);
        verify(checkoutIO).display("Total: 50");
    }

    @Test
    public void price_multiple_of_same_SKU() {
        HashMap<SKU,Integer> SKUs = new HashMap<SKU,Integer>()
        {{
            put(new SKU("A",50),2);
        }};
        pricer.price(SKUs);
        verify(checkoutIO).display("Total: 100");
    }

    @Test
    public void price_multiple_SKUs() {
        HashMap<SKU,Integer> SKUs = new HashMap<SKU,Integer>()
        {{
            put(new SKU("A",50),2);
            put(new SKU("B",30),1);
        }};
        pricer.price(SKUs);
        verify(checkoutIO).display("Total: 130");
    }

    @Test
    public void pricer_gets_correct_PricingRule() {
        PricingRule PricingRule = new PricingRule("A", 3, 130);
        when(PricingRuleRepository.getPricingRuleBySKUName("A")).thenReturn(PricingRule);
        HashMap<SKU,Integer> SKUs = new HashMap<SKU,Integer>()
        {{
            put(new SKU("A",50),3);
        }};
        pricer.price(SKUs);
        verify(PricingRuleRepository).getPricingRuleBySKUName("A");
    }

    @Test
    public void price_SKUs_in_PricingRule() {
        PricingRule PricingRule = new PricingRule("A", 3, 130);
        when(PricingRuleRepository.getPricingRuleBySKUName("A")).thenReturn(PricingRule);
        HashMap<SKU,Integer> SKUs = new HashMap<SKU,Integer>()
        {{
            put(new SKU("A",50),3);
        }};
        pricer.price(SKUs);
        verify(checkoutIO).display("Total: 130");

    }

    @Test
    public void price_purchased_SKUs_in_PricingRule() {
        PricingRule PricingRule = new PricingRule("A", 3, 130);
        when(PricingRuleRepository.getPricingRuleBySKUName("A")).thenReturn(PricingRule);
        HashMap<SKU,Integer> SKUs = new HashMap<SKU,Integer>()
        {{
            put(new SKU("A",50),6);
        }};
        pricer.price(SKUs);
        verify(checkoutIO).display("Total: 260");

        SKUs.clear();
        SKUs.put(new SKU("A",50),7);
        pricer.price(SKUs);
        verify(checkoutIO).display("Total: 310");
    }

    @Test
    public void price_mixed_SKUs() {
        PricingRule aPricingRule = new PricingRule("A", 3, 130);
        PricingRule bPricingRule = new PricingRule("B", 2, 45);


        when(PricingRuleRepository.getPricingRuleBySKUName("A")).thenReturn(aPricingRule);
        when(PricingRuleRepository.getPricingRuleBySKUName("B")).thenReturn(bPricingRule);

        HashMap<SKU,Integer> SKUs = new HashMap<SKU,Integer>()
        {{
            put(new SKU("A",50),6);
            put(new SKU("B",30),2);
            put(new SKU("C",20),1);
        }};
        pricer.price(SKUs);
        verify(checkoutIO).display("Total: 325");

    }


}