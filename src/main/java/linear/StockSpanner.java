package linear;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

public class StockSpanner {

    Map<Integer, Integer> dayToPrice;
    int currentDay;
    Deque<Integer> decresingDays = new ArrayDeque<>();

    public StockSpanner() {
        dayToPrice = new HashMap<>();
        currentDay = 0;
    }

    /**
     * Proceed a day with the given stock price, and return the span of that stock's
     * price. A span is defined as the consecutive days (including today) the price
     * of the stock has been less than or equal to today's price.
     * 
     * @param price the stock price of today
     * @return the span of the stock's price
     */
    public int next(int price) {
        while (!decresingDays.isEmpty() && dayToPrice.get(decresingDays.peekLast()) <= price) {
            int day = decresingDays.pollLast();
            dayToPrice.remove(day);
        }
        int diff = decresingDays.isEmpty() ? currentDay + 1 : currentDay - decresingDays.peekLast();
        decresingDays.offerLast(currentDay);
        dayToPrice.put(currentDay, price);
        currentDay++;
        return diff;
    }
}
