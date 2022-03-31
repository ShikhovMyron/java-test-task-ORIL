package test.task.service;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import test.task.config.AppProperties;
import test.task.entity.CryptoDataPrice;
import test.task.repository.CryptocurrencyInfoRepository;
import test.task.utils.CryptoDataUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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


    @Scheduled(fixedDelay = 30000)
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

    private CryptoDataPrice getCryptoDataPrice(String pair) {
        try {
            return CryptoDataUtils.getCryptoDataFromCEX("https://cex.io/api/last_price/" + pair);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }
}
