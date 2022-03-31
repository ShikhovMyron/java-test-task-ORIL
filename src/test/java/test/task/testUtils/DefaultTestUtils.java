package test.task.testUtils;

import test.task.config.AppTestProperties;
import test.task.entity.CryptoDataPrice;

import java.math.BigDecimal;


public class DefaultTestUtils {

    public static CryptoDataPrice getTestCryptoDataPrice() {
        return new CryptoDataPrice(
                new BigDecimal("100"),
                getBaseCurrencyFromConfig(),
                getTargetCurrencyFromConfig());
    }

    public static CryptoDataPrice getTestCryptoDataPriceInvalidBaseCurrency() {
        return new CryptoDataPrice(
                new BigDecimal("100"),
                "",
                getTargetCurrencyFromConfig());
    }

    public static String getBaseCurrencyFromConfig() {
        return AppTestProperties.currencyPairs.keySet().stream().findFirst().orElse(null);
    }

    public static String getTargetCurrencyFromConfig() {
        return AppTestProperties.currencyPairs.keySet().stream().findFirst().orElse(null);
    }

}
