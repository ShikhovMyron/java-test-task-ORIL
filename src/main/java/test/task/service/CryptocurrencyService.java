package test.task.service;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import test.task.config.AppProperties;
import test.task.entity.CryptocurrencyInfoEntity;
import test.task.exeption.NonexistentCurrencyName;
import test.task.repository.CryptocurrencyInfoRepository;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static test.task.utils.JsonInfoUtils.getCryptoInfoFromJsonCEX;
import static test.task.utils.JsonInfoUtils.getJsonFromUrl;

//TODO сделать логгер в файл
@Service
public class CryptocurrencyService {
    static final Logger logger = Logger.getLogger(CryptocurrencyService.class);

    private final AppProperties appProperties;
    private final CryptocurrencyInfoRepository cryptoInfoRepo;

    public CryptocurrencyService(
            AppProperties appProperties, CryptocurrencyInfoRepository cryptoInfoRepo
    ) {
        this.appProperties = appProperties;
        this.cryptoInfoRepo = cryptoInfoRepo;
    }


    public List<CryptocurrencyInfoEntity> getCryptocurrencyInfo() throws IOException {
        List<CryptocurrencyInfoEntity> cryptoInfoList = new ArrayList<>();

        for (String currencyPair : appProperties.currencyPairList()) {
            String url = String.format("https://cex.io/api/last_price/%s", currencyPair);
            JSONObject json = getJsonFromUrl(url);
            cryptoInfoList.add(setCurrentDate(getCryptoInfoFromJsonCEX(json)));
        }

        return cryptoInfoList;
    }

    private CryptocurrencyInfoEntity setCurrentDate(CryptocurrencyInfoEntity cryptoInfo) {
        cryptoInfo.setCreatedAt(new SimpleDateFormat(appProperties.dateFormat())
                .format(new Date()));
        return cryptoInfo;
    }

    public void saveCryptocurrencyInfo() {
        try {
            List<CryptocurrencyInfoEntity> cryptocurrencyInfo = getCryptocurrencyInfo();
            cryptoInfoRepo.saveAll(cryptocurrencyInfo);
        } catch (IOException e) {
            logger.warn(e.getMessage());
        }
    }

    public String getMinCryptoPrice(String currencyName) throws NonexistentCurrencyName {
        if (isExistentCurrencyName(currencyName)) {
            CryptocurrencyInfoEntity currencyInfo = cryptoInfoRepo
                    .findTopByCurrencyNameOrderByPriceAsc(currencyName);
            return currencyInfo.getPrice();
        } else {
            throw new NonexistentCurrencyName(currencyName);
        }
    }

    public String getMaxCryptoPrice(String currencyName) throws NonexistentCurrencyName {
        if (isExistentCurrencyName(currencyName)) {
            CryptocurrencyInfoEntity currencyInfo = cryptoInfoRepo
                    .findTopByCurrencyNameOrderByPriceDesc(currencyName);
            return currencyInfo.getPrice();
        } else {
            throw new NonexistentCurrencyName(currencyName);
        }
    }

    private boolean isExistentCurrencyName(String currencyName) {
        for (String s : appProperties.currencyPairList()) {
            if (s.split("/")[0].equals(currencyName)) {
                return true;
            }
        }
        return false;
    }

    public List<CryptocurrencyInfoEntity> getSelectedPageFromDB(
            String currencyName, int pageNumber, int pageSize
    ) throws NonexistentCurrencyName {

        if (isExistentCurrencyName(currencyName)) {
            Pageable pageable = PageRequest.of(pageNumber, pageSize);
            return cryptoInfoRepo.findAllByCurrencyNameOrderByPriceAsc(currencyName, pageable);
        } else {
            throw new NonexistentCurrencyName(currencyName);
        }
    }
}
