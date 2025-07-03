package linear;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StocksTest {

    private Stocks stocks;

    @BeforeEach
    public void setup() {
        stocks = new Stocks();
    }

    @Test
    public void testMaxProfitSinglePurchase() {
        double[] prices = new double[] { 1.0, 100.0, 99.0 };
        double shortTermTax = 0.2;
        double longTermTax = 0.1;
        double result = stocks.maxProfit(prices, 2, shortTermTax, longTermTax);
        System.out.println(result);
    }

    @Test
    public void testMaxProfit() {
        double[] prices = new double[] { 8.0, 12.0, 20.0, 17.0, 14.0 };
        double shortTermTax = 0.2;
        double longTermTax = 0.1;
        double result = stocks.maxProfit(prices, 3, shortTermTax, longTermTax);
        System.out.println(result);
    }

}
