package test.task.service;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import test.task.config.AppProperties;
import test.task.entity.CryptoDataPrice;
import test.task.exeption.NonexistentCurrencyName;
import test.task.repository.CryptocurrencyInfoRepository;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static test.task.utils.JsonInfoUtils.getCryptoInfoFromJsonCEX;
import static test.task.utils.JsonInfoUtils.getJsonFromUrl;

@Service
public class CryptocurrencyService {
//    static final Logger logger = Logger.getLogger(CryptocurrencyService.class);

    private final AppProperties appProperties;
    private final CryptocurrencyInfoRepository cryptoInfoRepo;

    public CryptocurrencyService(
            AppProperties appProperties, CryptocurrencyInfoRepository cryptoInfoRepo
    ) {
        this.appProperties = appProperties;
        this.cryptoInfoRepo = cryptoInfoRepo;
    }

    public String getMinCryptoPrice(String currencyName) throws NonexistentCurrencyName {
        if (isCurrencyExists(currencyName)) {
            CryptoDataPrice currencyInfo = cryptoInfoRepo
                    .findTopByBaseCurrencyOrderByPriceAsc(currencyName);
            return currencyInfo.getPrice();
        } else {
            throw new NonexistentCurrencyName(currencyName);
        }
    }

    public String getMaxCryptoPrice(String currencyName) throws NonexistentCurrencyName {
        if (isCurrencyExists(currencyName)) {
            CryptoDataPrice currencyInfo = cryptoInfoRepo
                    .findTopByBaseCurrencyOrderByPriceDesc(currencyName);
            return currencyInfo.getPrice();
        } else {
            throw new NonexistentCurrencyName(currencyName);
        }
    }

    private boolean isCurrencyExists(String currencyName) {
        for (String baseCurrency : appProperties.currencyPairs().keySet()) {
            if (baseCurrency.equals(currencyName)) {
                return true;
            }
        }
        return false;
    }

    public List<CryptoDataPrice> getSelectedPageFromDB(
            String currencyName, int pageNumber, int pageSize
    ) throws NonexistentCurrencyName {

        if (isCurrencyExists(currencyName)) {
            Pageable pageable = PageRequest.of(pageNumber, pageSize);
            return cryptoInfoRepo.findAllByBaseCurrencyOrderByPriceAsc(currencyName, pageable);
        } else {
            throw new NonexistentCurrencyName(currencyName);
        }
    }
}
