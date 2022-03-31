package test.task.it;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import test.task.config.AppProperties;
import test.task.config.AppTestProperties;
import test.task.entity.CryptoDataPrice;
import test.task.exeption.NonexistentCurrencyName;
import test.task.repository.CryptocurrencyInfoRepository;
import test.task.service.CryptocurrencyService;

import java.math.BigDecimal;

@SpringBootTest
@TestPropertySource("/application-test.yaml")
public class CryptocurrencyServiceIT {
    @Autowired
    private CryptocurrencyService cryptocurrencyService;

    @MockBean
    private AppProperties appProperties;
    @MockBean
    private CryptocurrencyInfoRepository cryptoInfoRepo;

    @Test
    void getMinCryptoPricePositiveTest() throws NonexistentCurrencyName {
        CryptoDataPrice testCryptoDataPrice = getTestCryptoDataPrice();
        String baseCurrency = getBaseCurrencyFromConfigOrEmpty();

        Mockito.doReturn(AppTestProperties.currencyPairs).when(appProperties).getCurrencyPairs();
        Mockito.doReturn(testCryptoDataPrice)
                .when(cryptoInfoRepo).findTopByBaseCurrencyOrderByPriceAsc(Mockito.anyString());

        Assertions.assertEquals(
                new BigDecimal(testCryptoDataPrice.getPrice()).toString()
                , cryptocurrencyService.getMinCryptoPrice(baseCurrency)
        );
    }

    @Test
    void getMinCryptoPriceExceptionTest() {
        Mockito.doReturn(AppTestProperties.currencyPairs).when(appProperties).getCurrencyPairs();
        Mockito.doReturn(getTestCryptoDataPrice())
                .when(cryptoInfoRepo).findTopByBaseCurrencyOrderByPriceAsc(Mockito.anyString());

        Assertions.assertThrows(
                NonexistentCurrencyName.class
                , () -> cryptocurrencyService.getMinCryptoPrice("")
        );
    }

    private CryptoDataPrice getTestCryptoDataPrice() {
        return new CryptoDataPrice(
                new BigDecimal("100"),
                getBaseCurrencyFromConfigOrEmpty(),
                getTargetCurrencyFromConfigOrEmpty());
    }

    private String getBaseCurrencyFromConfigOrEmpty() {
        return AppTestProperties.currencyPairs.keySet().stream().findFirst().orElse("");
    }

    private String getTargetCurrencyFromConfigOrEmpty() {
        return AppTestProperties.currencyPairs.keySet().stream().findFirst().orElse("");
    }
}
