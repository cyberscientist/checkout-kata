package com.checkout.kata;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class CheckoutIO {
    private Scanner scanner;
    private PrintStream out;

    public CheckoutIO(InputStream inputStream, PrintStream outStream) {
        this.scanner = new Scanner(inputStream);
        this.out = outStream;
    }

    public void display(String message) {
        out.println(message);
    }

    public boolean scannerHasNext() {
        return scanner.hasNext();
    }

    public String getScannerNextLine() {
        return this.scanner.nextLine();
    }
}
