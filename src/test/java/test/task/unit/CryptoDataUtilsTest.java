package test.task.unit;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static test.task.utils.CryptoDataUtils.getCryptoDataFromCEX;

class CryptoDataUtilsTest {

    @ParameterizedTest
    @CsvSource({
            "BTC,USD",
            "ETH,USD",
            "XRP,USD"
    })
    void getCryptoDataFromCEXTestValid(String baseCurrency, String targetCurrency) throws IOException {
        String path = String.format("https://cex.io/api/last_price/%s/%s", baseCurrency, targetCurrency);
        assertNotNull(getCryptoDataFromCEX(path));
    }

    @ParameterizedTest
    @CsvSource({
            ",USD",
            "ETH,",
    })
    void getCryptoDataFromCEXTestException(String baseCurrency, String targetCurrency) {
        String path = String.format("https://cex.io/api/last_price/%s/%s", baseCurrency, targetCurrency);
        assertThrows(Exception.class, () -> getCryptoDataFromCEX(path));

    }
}