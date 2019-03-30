package com.checkout.kata.integration;

import com.checkout.kata.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.*;

public class CheckOutFeature {
    private Checkout checkout;
    private @Mock
    CheckoutIO checkoutIO = mock(CheckoutIO.class);
    private SKURepository itemRepository = new SKURepository();
    private PricingRuleRepository discountRepository = new PricingRuleRepository();
    private Pricer pricer;

    @BeforeEach
    public void setup() {
        itemRepository.addItem(new SKU("A", 50));
        itemRepository.addItem(new SKU("B", 30));

        discountRepository.add(new PricingRule("A", 3, 130));
        discountRepository.add(new PricingRule("B", 2, 45));

        pricer = new Pricer(checkoutIO, discountRepository);
        checkout = new Checkout(itemRepository, checkoutIO, pricer);
    }

    @Test
    void scan_A_then_B_and_A() {

        when(checkoutIO.scannerHasNext()).thenReturn(true);
        when(checkoutIO.getScannerNextLine()).thenReturn("A", "A", "B", "checkout");
        checkout.scanItems();

        verify(checkoutIO).display("Total: 130");
    }

    @Test
    void scan_B_then_A_and_B() {
        when(checkoutIO.scannerHasNext()).thenReturn(true);
        when(checkoutIO.getScannerNextLine()).thenReturn("B", "A", "B", "checkout");
        checkout.scanItems();
        verify(checkoutIO).display("Total: 95");
    }

}
