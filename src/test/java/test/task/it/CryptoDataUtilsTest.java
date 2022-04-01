package test.task.it;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import test.task.config.AppProperties;
import test.task.entity.CryptoDataPrice;
import test.task.repository.CryptocurrencyInfoRepository;
import test.task.service.CurrencyDataFetcher;
import test.task.utils.CryptoDataUtils;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static test.task.testUtils.DefaultTestUtils.*;

@SpringBootTest
@TestPropertySource("/application-test.yaml")
class CryptoDataUtilsTest {

    @Autowired
    private CurrencyDataFetcher currencyDataFetcher;

    @MockBean
    private AppProperties appProperties;
    @MockBean
    private CryptocurrencyInfoRepository cryptoInfoRepo;

    @Test
    void saveCryptocurrencyInfoTest() throws JSONException {
        // Arrange
        int price = 1000;
        String url1 = String.format("https://cex.io/api/last_price/%s/%s", BTC, USD);
        String url2 = String.format("https://cex.io/api/last_price/%s/%s", ETH, USD);
        String url3 = String.format("https://cex.io/api/last_price/%s/%s", XRP, USD);
        String json1 = String.format(
                "{\"lprice\":\"%s\",\"curr1\":\"%s\",\"curr2\":\"%s\"}"
                , price, BTC, USD);
        String json2 = String.format(
                "{\"lprice\":\"%s\",\"curr1\":\"%s\",\"curr2\":\"%s\"}"
                , price, ETH, USD);
        String json3 = "{\"error\":\"Invalid Symbols Pair\"}";
        doReturn(currencyPairs).when(appProperties).getCurrencyPairs();
        try (MockedStatic<CryptoDataUtils> utilities = Mockito.mockStatic(CryptoDataUtils.class)) {
            utilities.when(() -> CryptoDataUtils.getJsonFromUrl(url1)).thenReturn(new JSONObject(json1));
            utilities.when(() -> CryptoDataUtils.getJsonFromUrl(url2)).thenReturn(new JSONObject(json2));
            utilities.when(() -> CryptoDataUtils.getJsonFromUrl(url3)).thenReturn(new JSONObject(json3));
            //Action
            List<CryptoDataPrice> expected = List.of(
                    getTestCryptoDataPrice(price, BTC, USD),
                    getTestCryptoDataPrice(price, ETH, USD)
            );
            currencyDataFetcher.saveCryptocurrencyInfo();
            //Assert
            Mockito.verify(cryptoInfoRepo, Mockito.times(1)).saveAll(expected);
        }
    }

    @Test
    void getCryptoDataFromCEXTestValid() throws IOException, JSONException {
        // Arrange
        int price = 1000;
        String url = String.format("https://cex.io/api/last_price/%s/%s", BTC, USD);
        String json = String.format(
                "{\"lprice\":\"%s\",\"curr1\":\"%s\",\"curr2\":\"%s\"}"
                , price, BTC, USD);
        try (MockedStatic<CryptoDataUtils> utilities = Mockito.mockStatic(CryptoDataUtils.class)) {
            utilities.when(() -> CryptoDataUtils.getJsonFromUrl(url)).thenReturn(new JSONObject(json));
            //Action
            CryptoDataPrice expected = getTestCryptoDataPrice(price, BTC, USD);
            CryptoDataPrice actual = currencyDataFetcher.getCryptoDataFromCEX(url);
            //Assert
            assertEquals(expected, actual);
        }
    }
}