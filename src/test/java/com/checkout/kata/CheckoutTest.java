package com.checkout.kata;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CheckoutTest {

    @Mock
    CheckoutIO checkoutIO = mock(CheckoutIO.class);
    @Mock
    SKURepository itemRepository = mock(SKURepository.class);

    Checkout checkout;
    private Pricer pricer = mock(Pricer.class);

    @BeforeEach
    public void setup() {
        checkout = new Checkout(itemRepository, checkoutIO, pricer);
    }

    @Test
    public void gets_item_from_item_repo_using_user_input() {
        when(checkoutIO.scannerHasNext()).thenReturn(true, false);
        when(checkoutIO.getScannerNextLine()).thenReturn("B");
        checkout.scanItems();
        verify(itemRepository).getItemByName("B");
    }

    @Test
    public void add_user_items_to_basket() {
        fakeUserInput("B", 50);
        checkout.scanItems();
        assertEquals(1, checkout.getBasket().size());
    }

    @Test
    public void add_multiple_of_the_same_items() {
        fakeUserInput("B", 50);
        checkout.scanItems();
        assertEquals(1, checkout.getBasket().size());
        fakeUserInput("B", 50);
        checkout.scanItems();
        assertEquals(1, checkout.getBasket().size());
        assertEquals(2, checkout.getBasket().get(new SKU("B", 50)));
        fakeUserInput("A", 100);
        checkout.scanItems();
        assertEquals(2, checkout.getBasket().size());
        assertEquals(1, checkout.getBasket().get(new SKU("A", 100)));
        fakeUserInput("B", 50);
        checkout.scanItems();
        assertEquals(2, checkout.getBasket().size());
        assertEquals(3, checkout.getBasket().get(new SKU("B", 50)));
    }

    @Test
    public void error_on_missing_items_in_repo() {
        when(checkoutIO.scannerHasNext()).thenReturn(true, false);
        when(checkoutIO.getScannerNextLine()).thenReturn("B");
        checkout.scanItems();
        verify(checkoutIO).display("Enter skus (type 'checkout' to finish and see total): ");
    }

    @Test
    public void call_pricer_when_check_out_is_typed() {
        when(checkoutIO.scannerHasNext()).thenReturn(true, true);
        when(checkoutIO.getScannerNextLine()).thenReturn("B");
        when(checkoutIO.getScannerNextLine()).thenReturn("checkout");
        checkout.scanItems();
        verify(pricer).price(checkout.getBasket());
    }

    @Test
    public void load_sku_file() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        String testFile = classLoader.getResource("sku.csv").getPath();
        checkout.loadSKUFromFile(testFile);
        File file = new File(testFile);
        verify(itemRepository).loadSKUsFromFile(file,true);
    }

    private void fakeUserInput(String itemInput, int price) {
        when(checkoutIO.scannerHasNext()).thenReturn(true, false);
        when(checkoutIO.getScannerNextLine()).thenReturn(itemInput);
        SKU returnItem = new SKU(itemInput, price);
        when(itemRepository.getItemByName(itemInput)).thenReturn(returnItem);
    }
}
