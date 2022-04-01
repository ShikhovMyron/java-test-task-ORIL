package test.task.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import test.task.config.AppProperties;
import test.task.entity.CryptoDataPrice;
import test.task.exeption.NonexistentCurrencyName;
import test.task.repository.CryptocurrencyInfoRepository;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CryptocurrencyService {
    private final AppProperties appProperties;
    private final CryptocurrencyInfoRepository cryptoInfoRepo;

    public CryptocurrencyService(
            AppProperties appProperties, CryptocurrencyInfoRepository cryptoInfoRepo
    ) {
        this.appProperties = appProperties;
        this.cryptoInfoRepo = cryptoInfoRepo;
    }

    public BigDecimal getMinCryptoPrice(String currencyName) throws NonexistentCurrencyName {
        if (isCurrencyExists(currencyName)) {
            CryptoDataPrice currencyInfo = cryptoInfoRepo
                    .findTopByBaseCurrencyOrderByPriceAsc(currencyName);
            return currencyInfo.getPrice();
        } else {
            throw new NonexistentCurrencyName(currencyName);
        }
    }

    public BigDecimal getMaxCryptoPrice(String currencyName) throws NonexistentCurrencyName {
        if (isCurrencyExists(currencyName)) {
            CryptoDataPrice currencyInfo = cryptoInfoRepo
                    .findTopByBaseCurrencyOrderByPriceDesc(currencyName);
            return currencyInfo.getPrice();
        } else {
            throw new NonexistentCurrencyName(currencyName);
        }
    }

    public List<CryptoDataPrice> getWithPagination(
            String currencyName, int pageNumber, int pageSize
    ) throws NonexistentCurrencyName {

        if (isCurrencyExists(currencyName)) {
            Pageable pageable = PageRequest.of(pageNumber, pageSize);
            return cryptoInfoRepo.findAllByBaseCurrencyOrderByPriceAsc(currencyName, pageable);
        } else {
            throw new NonexistentCurrencyName(currencyName);
        }
    }

    private boolean isCurrencyExists(String currencyName) {
        for (String baseCurrency : appProperties.getCurrencyPairs().keySet()) {
            if (baseCurrency.equals(currencyName)) {
                return true;
            }
        }
        return false;
    }
}
