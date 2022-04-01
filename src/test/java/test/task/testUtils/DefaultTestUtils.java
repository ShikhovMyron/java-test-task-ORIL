package test.task.testUtils;

import test.task.entity.CryptoDataPrice;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;


public class DefaultTestUtils {
    public static final Map<String, String> currencyPairs;
    public static final String BTC = "BTC";
    public static final String ETH = "ETH";
    public static final String XRP = "XRP";
    public static final String USD = "USD";

    static {
        currencyPairs = new HashMap<>();
        currencyPairs.put(BTC, USD);
        currencyPairs.put(ETH, USD);
        currencyPairs.put(XRP, USD);
    }

    public static CryptoDataPrice getTestCryptoDataPrice(int price, String curr1, String curr2) {
        return new CryptoDataPrice(
                new BigDecimal(price),
                curr1,
                curr2);
    }

}
