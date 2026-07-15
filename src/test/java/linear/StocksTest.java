package linear;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        double[] prices = new double[] { 1.0, 101.0, 99.0 };
        double shortTermTax = 0.2;
        double longTermTax = 0.1;
        // buy on day 0, sell on day 2
        double expected = 88.2;
        double result = stocks.maxProfit(prices, 2, shortTermTax, longTermTax);
        assertEquals(expected, result, 0.0001);

        double resultReverse = stocks.maxProfitReverse(prices, 2, shortTermTax, longTermTax);
        assertEquals(expected, resultReverse, 0.0001);
    }

    @Test
    public void testMaxProfitMultiplePurchases() {
        double[] prices = new double[] { 8.0, 12.0, 20.0, 18.0 };
        double shortTermTax = 0.2;
        double longTermTax = 0.1;
        // buy on day 0 and 1, sell on day 2
        double expected = 16.0;
        double result = stocks.maxProfit(prices, 3, shortTermTax, longTermTax);
        assertEquals(expected, result, 0.0001);

        double resultReverse = stocks.maxProfitReverse(prices, 3, shortTermTax, longTermTax);
        assertEquals(expected, resultReverse, 0.0001);
    }

    @Test
    public void testMaxProfitMultiplePurchasesLongTerm() {
        double[] prices = new double[] { 8.0, 12.0, 20.0, 14.0, 17.0 };
        double shortTermTax = 0.5;
        double longTermTax = 0.1;
        // buy on day 0, 1 and 3, sell on day 4
        double expected = 14.1;
        double result = stocks.maxProfit(prices, 3, shortTermTax, longTermTax);
        assertEquals(expected, result, 0.0001);

        double resultReverse = stocks.maxProfitReverse(prices, 3, shortTermTax, longTermTax);
        assertEquals(expected, resultReverse, 0.0001);
    }

    @Test
    public void testMaxProfitNoPurchases() {
        double[] prices = new double[] { 10.0, 9.0, 8.0, 7.0 };
        double shortTermTax = 0.2;
        double longTermTax = 0.1;
        double expected = 0.0;
        double result = stocks.maxProfit(prices, 2, shortTermTax, longTermTax);
        assertEquals(expected, result, 0.0001);

        double resultReverse = stocks.maxProfitReverse(prices, 2, shortTermTax, longTermTax);
        assertEquals(expected, resultReverse, 0.0001);
    }

    @Test
    public void testMaxProfitMultipleSells() {
        double[] prices = new double[] { 5.0, 10.0, 20.0, 18.0 };
        double shortTermTax = 0.4;
        double longTermTax = 0.1;
        // buy on day 0 and 1, sell on day 2 and 3
        double expected = 20.7;
        double result = stocks.maxProfit(prices, 2, shortTermTax, longTermTax);
        assertEquals(expected, result, 0.0001);

        double resultReverse = stocks.maxProfitReverse(prices, 2, shortTermTax, longTermTax);
        assertEquals(expected, resultReverse, 0.0001);
    }

}
