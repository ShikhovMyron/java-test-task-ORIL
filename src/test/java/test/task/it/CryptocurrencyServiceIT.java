package test.task.it;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.TestPropertySource;
import test.task.config.AppProperties;
import test.task.entity.CryptoDataPrice;
import test.task.exeption.NonexistentCurrencyName;
import test.task.repository.CryptocurrencyInfoRepository;
import test.task.service.CryptocurrencyService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static test.task.testUtils.DefaultTestUtils.*;

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
        // Arrange
        CryptoDataPrice expected = getTestCryptoDataPrice(1231, BTC, USD);
        doReturn(currencyPairs).when(appProperties).getCurrencyPairs();
        doReturn(expected)
                .when(cryptoInfoRepo).findTopByBaseCurrencyOrderByPriceAsc(BTC);

        // Action
        BigDecimal actual = cryptocurrencyService.getMinCryptoPrice(BTC);

        // Assert
        verify(cryptoInfoRepo, times(1)).findTopByBaseCurrencyOrderByPriceAsc(BTC);
        assertEquals(expected.getPrice(), actual);
    }

    @Test
    void getMinCryptoPriceFailsIOnInvalidCurrencyName() {
        // Arrange
        doReturn(currencyPairs).when(appProperties).getCurrencyPairs();
        // Assert
        assertThrows(
                NonexistentCurrencyName.class
                , () -> cryptocurrencyService.getMinCryptoPrice("LDKASJaa")
        );
    }

    @Test
    void getMinCryptoPriceFailsIOnEmptyCurrencyName() {
        // Arrange
        doReturn(currencyPairs).when(appProperties).getCurrencyPairs();
        // Assert
        verify(cryptoInfoRepo, times(0)).findTopByBaseCurrencyOrderByPriceAsc(any());
        assertThrows(
                NonexistentCurrencyName.class
                , () -> cryptocurrencyService.getMinCryptoPrice("")
        );
    }

    @Test
    void getMinCryptoPriceFailsIOnNullCurrencyName() {
        // Arrange
        doReturn(currencyPairs).when(appProperties).getCurrencyPairs();
        // Assert
        verify(cryptoInfoRepo, times(0)).findTopByBaseCurrencyOrderByPriceAsc(any());
        assertThrows(
                NonexistentCurrencyName.class
                , () -> cryptocurrencyService.getMinCryptoPrice(null)
        );
    }


    @Test
    void getMaxCryptoPricePositiveTest() throws NonexistentCurrencyName {
        // Arrange
        CryptoDataPrice expected = getTestCryptoDataPrice(1231, ETH, USD);
        doReturn(currencyPairs).when(appProperties).getCurrencyPairs();
        doReturn(expected)
                .when(cryptoInfoRepo).findTopByBaseCurrencyOrderByPriceDesc(ETH);

        // Action
        BigDecimal actual = cryptocurrencyService.getMaxCryptoPrice(ETH);

        // Assert
        verify(cryptoInfoRepo, times(1)).findTopByBaseCurrencyOrderByPriceDesc(ETH);
        assertEquals(expected.getPrice(), actual);
    }

    @Test
    void getMaxCryptoPriceFailsIOnInvalidCurrencyName() {
        // Assert
        doReturn(currencyPairs).when(appProperties).getCurrencyPairs();
        // Arrange
        verify(cryptoInfoRepo, times(0)).findTopByBaseCurrencyOrderByPriceAsc(any());
        assertThrows(
                NonexistentCurrencyName.class
                , () -> cryptocurrencyService.getMaxCryptoPrice("LDaJaa")
        );
    }

    @Test
    void getMaxCryptoPriceFailsIOnEmptyCurrencyName() {
        // Arrange
        doReturn(currencyPairs).when(appProperties).getCurrencyPairs();
        // Assert
        verify(cryptoInfoRepo, times(0)).findTopByBaseCurrencyOrderByPriceAsc(any());
        assertThrows(
                NonexistentCurrencyName.class
                , () -> cryptocurrencyService.getMaxCryptoPrice("")
        );
    }

    @Test
    void getMaxCryptoPriceFailsIOnNullCurrencyName() {
        // Arrange
        doReturn(currencyPairs).when(appProperties).getCurrencyPairs();
        // Assert
        verify(cryptoInfoRepo, times(0)).findTopByBaseCurrencyOrderByPriceAsc(any());
        assertThrows(
                NonexistentCurrencyName.class
                , () -> cryptocurrencyService.getMaxCryptoPrice(null)
        );
    }


    @Test
    void getWithPaginationPositiveTest() throws NonexistentCurrencyName {
        // Assert
        List<CryptoDataPrice> expected = new ArrayList<>() {{
            add(getTestCryptoDataPrice(55, BTC, USD));
            add(getTestCryptoDataPrice(1241, BTC, USD));
            add(getTestCryptoDataPrice(4, ETH, USD));
            add(getTestCryptoDataPrice(99999, ETH, USD));
            add(getTestCryptoDataPrice(1204111, XRP, USD));
        }};
        doReturn(currencyPairs).when(appProperties).getCurrencyPairs();
        doReturn(expected).when(cryptoInfoRepo)
                .findAllByBaseCurrencyOrderByPriceAsc(ETH, PageRequest.of(0, 5));
        //Action
        List<CryptoDataPrice> actual = cryptocurrencyService.getWithPagination(ETH, 0, 5);
        // Assert
        verify(cryptoInfoRepo, times(1)).findAllByBaseCurrencyOrderByPriceAsc(ETH, PageRequest.of(0, 5));
        assertEquals(expected, actual);
    }

    @Test
    void getWithPaginationFailsIOnInvalidCurrencyName() {
        // Arrange
        doReturn(currencyPairs).when(appProperties).getCurrencyPairs();
        // Assert
        verify(cryptoInfoRepo, times(0)).findAllByBaseCurrencyOrderByPriceAsc(any(), any());
        assertThrows(
                NonexistentCurrencyName.class
                , () -> cryptocurrencyService.getWithPagination("", 0, 5)
        );
    }

    @Test
    void getWithPaginationFailsIOnNullCurrencyName() {
        // Arrange
        doReturn(currencyPairs).when(appProperties).getCurrencyPairs();
        // Assert
        verify(cryptoInfoRepo, times(0)).findAllByBaseCurrencyOrderByPriceAsc(any(), any());
        assertThrows(
                NonexistentCurrencyName.class
                , () -> cryptocurrencyService.getWithPagination("poadpw1", 0, 5)
        );
    }

    @Test
    void getWithPaginationFailsIOnEmptyCurrencyName() {
        // Arrange
        doReturn(currencyPairs).when(appProperties).getCurrencyPairs();
        // Assert
        verify(cryptoInfoRepo, times(0)).findAllByBaseCurrencyOrderByPriceAsc(any(), any());
        assertThrows(
                NonexistentCurrencyName.class
                , () -> cryptocurrencyService.getWithPagination(null, 0, 5)
        );
    }
}
