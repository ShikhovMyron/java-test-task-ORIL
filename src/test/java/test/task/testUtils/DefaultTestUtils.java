package test.task.testUtils;

import test.task.config.AppTestProperties;
import test.task.entity.CryptoDataPrice;

import java.math.BigDecimal;


public class DefaultTestUtils {

    public CryptoDataPrice getTestCryptoDataPrice() {
        return new CryptoDataPrice(
                new BigDecimal("100"),
                getBaseCurrencyFromConfig(),
                getTargetCurrencyFromConfig());
    }

    public CryptoDataPrice getTestCryptoDataPriceInvalidBaseCurrency() {
        return new CryptoDataPrice(
                new BigDecimal("100"),
                "",
                getTargetCurrencyFromConfig());
    }

    public String getBaseCurrencyFromConfig() {
        return AppTestProperties.currencyPairs.keySet().stream().findFirst().orElse(null);
    }

    public String getTargetCurrencyFromConfig() {
        return AppTestProperties.currencyPairs.keySet().stream().findFirst().orElse(null);
    }

}
