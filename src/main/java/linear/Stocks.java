package linear;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Stocks {

    /**
     * Given an array of prices of each day, calculate the max profits during these
     * days. During each day, you can purchase at most 1 quantity, or sell any
     * amount of quantities. If the we sell at least X days after purchase, we apply
     * longTermTax, otherwise shortTermTax. If we hold multiple quantities purchased
     * on different days, we can assume that we always sell the quantity we bought
     * first if we do not sell all quantities.
     * 
     * @param prices       double array, non empty and each element is positive
     * @param X            number of days to apply long term tax
     * @param shortTermTax 0 < shortTermTax < 1
     * @param longTermTax  0 < longTermTax < shorTermTax
     * @return
     */
    public double maxProfit(double[] prices, int X, double shortTermTax, double longTermTax) {
        Map<String, Double> memo = new HashMap<>();
        return dfs(0, new ArrayList<>(), prices, X, shortTermTax, longTermTax, memo);
    }

    private double dfs(int day, List<Stock> holdings, double[] prices, int X,
            double shortTermTax, double longTermTax, Map<String, Double> memo) {
        if (day == prices.length) {
            return 0.0;
        }

        String key = day + "#" + encode(holdings);
        if (memo.containsKey(key)) {
            return memo.get(key);
        }

        double max = 0.0;

        // Option 1: Do nothing
        max = Math.max(max, dfs(day + 1, holdings, prices, X, shortTermTax, longTermTax, memo));

        // Option 2: Buy 1 unit
        List<Stock> afterBuy = new ArrayList<>(holdings);
        afterBuy.add(new Stock(prices[day], day));
        max = Math.max(max, dfs(day + 1, afterBuy, prices, X, shortTermTax, longTermTax, memo));

        // Option 3: Try selling any subset (generate all subsets)
        int n = holdings.size();
        for (int mask = 1; mask < (1 << n); mask++) {
            List<Stock> remain = new ArrayList<>();
            double profit = 0.0;
            for (int i = 0; i < n; i++) {
                if ((mask & (1 << i)) != 0) {
                    Stock stock = holdings.get(i);
                    double tax = (day - stock.day) >= X ? longTermTax : shortTermTax;
                    profit += (prices[day] - stock.price) * (1 - tax);
                } else {
                    remain.add(holdings.get(i));
                }
            }
            double total = profit + dfs(day + 1, remain, prices, X, shortTermTax, longTermTax, memo);
            max = Math.max(max, total);
        }

        memo.put(key, max);
        return max;
    }

    private String encode(List<Stock> holdings) {
        StringBuilder sb = new StringBuilder();
        for (Stock s : holdings) {
            sb.append("BOUGHT_ON").append(s.day).append(",");
        }
        return sb.toString();
    }

    private class Stock {
        double price;
        int day;

        Stock(double price, int day) {
            this.price = price;
            this.day = day;
        }
    }
}
