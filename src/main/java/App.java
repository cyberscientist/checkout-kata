import com.checkout.kata.*;

import java.io.File;
import java.io.IOException;

public class App {
    private CheckoutIO checkoutIO;
    private PricingRuleRepository pricingRuleRepository;
    private SKURepository SKURepository;

    public static void main(String[] args) {
        App app = new App();
        try {
            app.init();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void init() throws IOException {
        ClassLoader classLoader = this.getClass().getClassLoader();
        File skuFile = new File(classLoader.getResource("sku.csv").getFile());
        File prcingRule = new File(classLoader.getResource("pricingRules.csv").getFile());
        checkoutIO = new CheckoutIO(System.in,System.out);
        SKURepository = new SKURepository();
        SKURepository.loadSKUsFromFile(skuFile, false);
        pricingRuleRepository = new PricingRuleRepository();
        pricingRuleRepository.loadPricingRulesFromFile(prcingRule, true);
        Pricer pricer = new Pricer(checkoutIO, pricingRuleRepository);
        Checkout checkout = new Checkout(SKURepository,checkoutIO,pricer);
        checkout.scanItems();
    }


}
