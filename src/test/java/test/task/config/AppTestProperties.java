package test.task.config;

import java.util.HashMap;
import java.util.Map;

public class AppTestProperties {
    public static final Map<String, String> currencyPairs;

    static {
        currencyPairs = new HashMap<>();
        currencyPairs.put("BTC", "USD");
        currencyPairs.put("ETH", "USD");
        currencyPairs.put("XRP", "USD");
    }
}
