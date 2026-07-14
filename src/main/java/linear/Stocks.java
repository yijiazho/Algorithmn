package linear;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
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
     * @param longTermTax  0 < longTermTax < shortTermTax
     * @return
     */
    public double maxProfit(double[] prices, int X, double shortTermTax, double longTermTax) {
        Map<State, Double> memo = new HashMap<>();
        double[] max = new double[1];
        search(0, new ArrayList<>(), prices, X, shortTermTax, longTermTax, max, memo);
        return max[0];
    }

    /**
     * Traverse the days and try all possible actions, memorize the max profit for
     * each state.
     * 
     * @param day          the current day
     * @param holdings     the list of days when stocks were bought
     * @param prices       the array of stock prices
     * @param X            the number of days to apply long term tax
     * @param shortTermTax the short term tax rate
     * @param longTermTax  the long term tax rate
     * @param max          the max profit found so far
     * @param memo         max profit of each state before making a decision. The
     *                     state is represented by the current day and the list
     *                     of
     *                     days when stocks were bought.
     */
    private double search(int day, List<Integer> holdings, double[] prices,
            int X, double shortTermTax, double longTermTax, double[] max,
            Map<State, Double> memo) {
        State state = new State(day, holdings);
        if (memo.containsKey(state)) {
            return memo.get(state);
        }

        if (day == prices.length) {
            return 0.0;
        }

        double profit = 0.0;
        if (day == prices.length - 1) {
            // sell all holdings on the last day
            for (int buyDay : holdings) {
                profit += profitAfterTax(prices[buyDay], prices[day], buyDay, day, X, shortTermTax, longTermTax);
            }
            max[0] = Math.max(max[0], profit);
            memo.put(state, profit);
            return profit;
        }

        // do nothing
        search(day + 1, holdings, prices, X, shortTermTax, longTermTax, max, memo);

        // buy 1 unit
        List<Integer> afterBuy = new ArrayList<>(holdings);
        afterBuy.add(day);
        search(day + 1, afterBuy, prices, X, shortTermTax, longTermTax, max, memo);

        // sell any amount of holdings
        int n = holdings.size();
        double sellProfit = 0.0;
        double totalProfit = 0.0;
        List<Integer> remain = new LinkedList<>(holdings);
        for (int i = 0; i < n; i++) {
            // try to sell the first holding at a time
            int buyDay = remain.remove(0);
            sellProfit += profitAfterTax(prices[buyDay], prices[day], buyDay, day, X, shortTermTax, longTermTax);

            // memorize this state and continue searching
            double futureProfit = search(day + 1, remain, prices, X, shortTermTax, longTermTax, max, memo);
            totalProfit = Math.max(totalProfit, sellProfit + futureProfit);
        }

        max[0] = Math.max(max[0], totalProfit);
        if (totalProfit > memo.getOrDefault(state, 0.0)) {
            memo.put(state, totalProfit);
        }
        return totalProfit;
    }

    private double profitAfterTax(double buyPrice, double sellPrice, int buyDay, int sellDay,
            int X, double shortTermTax, double longTermTax) {
        double tax = (sellDay - buyDay) >= X ? longTermTax : shortTermTax;
        if (sellPrice > buyPrice) {
            return (sellPrice - buyPrice) * (1 - tax);
        } else {
            return sellPrice - buyPrice;
        }
    }

    private class State {
        int currentDay;
        List<Integer> holdings;

        State(int currentDay, List<Integer> holdings) {
            this.currentDay = currentDay;
            this.holdings = new ArrayList<>(holdings);
        }

        @Override
        public int hashCode() {
            int result = Integer.hashCode(currentDay);
            for (int day : holdings) {
                result = 31 * result + Integer.hashCode(day);
            }
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (!(obj instanceof State))
                return false;
            State other = (State) obj;
            return this.currentDay == other.currentDay && this.holdings.equals(other.holdings);
        }
    }
}
