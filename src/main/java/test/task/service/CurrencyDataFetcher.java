package test.task.service;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import test.task.config.AppProperties;
import test.task.entity.CryptoDataPrice;
import test.task.repository.CryptocurrencyInfoRepository;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static test.task.utils.CryptoDataUtils.getJsonFromUrl;

@Service
public class CurrencyDataFetcher {
    private static final Logger logger = Logger.getLogger(CurrencyDataFetcher.class);
    private final AppProperties appProperties;
    private final CryptocurrencyInfoRepository cryptoInfoRepo;

    public CurrencyDataFetcher(AppProperties appProperties,
                               CryptocurrencyInfoRepository cryptoInfoRepo) {
        this.appProperties = appProperties;
        this.cryptoInfoRepo = cryptoInfoRepo;
    }

    @Scheduled(initialDelay = 30000, fixedDelay = 30000)
    public void saveCryptocurrencyInfo() {
        List<CryptoDataPrice> cryptocurrencyInfo = appProperties.getCurrencyPairs()
                .entrySet()
                .stream()
                .map(entry -> entry.getKey() + "/" + entry.getValue())
                .map(this::getCryptoDataPrice)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        cryptoInfoRepo.saveAll(cryptocurrencyInfo);
    }

    public CryptoDataPrice getCryptoDataPrice(String pair) {
        try {
            return getCryptoDataFromCEX("https://cex.io/api/last_price/" + pair);
        } catch (IOException | JSONException e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    public CryptoDataPrice getCryptoDataFromCEX(String url) throws JSONException, IOException {
        JSONObject json = getJsonFromUrl(url);
        BigDecimal price = new BigDecimal(json.getString("lprice"));
        String baseCurrency = json.getString("curr1");
        String targetCurrency = json.getString("curr2");
        return new CryptoDataPrice(price, baseCurrency, targetCurrency);
    }
}
