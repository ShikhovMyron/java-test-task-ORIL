package test.task.service;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import test.task.config.AppConfiguration;
import test.task.exeption.NonexistentCurrencyName;
import test.task.model.CryptocurrencyInfoModel;
import test.task.repository.CryptocurrencyInfoRepository;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static test.task.utils.JsonInfoModel.getCryptoInfoFromJsonCEX;
import static test.task.utils.JsonInfoModel.getJsonFromUrl;

//TODO сделать логгер в файл
@Service
public class CryptocurrencyService {
    static final Logger logger = Logger.getLogger(CryptocurrencyService.class);

    private final AppConfiguration appConfiguration;
    private final CryptocurrencyInfoRepository cryptoInfoRepo;

    public CryptocurrencyService(
            AppConfiguration appConfiguration, CryptocurrencyInfoRepository cryptoInfoRepo
    ) {
        this.appConfiguration = appConfiguration;
        this.cryptoInfoRepo = cryptoInfoRepo;
    }


    public List<CryptocurrencyInfoModel> getCryptocurrencyInfo() throws IOException {
        List<CryptocurrencyInfoModel> cryptoInfoList = new ArrayList<>();

        for (String currencyPair : appConfiguration.currencyPairList()) {
            String url = String.format("https://cex.io/api/last_price/%s", currencyPair);
            JSONObject json = getJsonFromUrl(url);
            cryptoInfoList.add(setCurrentDate(getCryptoInfoFromJsonCEX(json)));
        }

        return cryptoInfoList;
    }

    private CryptocurrencyInfoModel setCurrentDate(CryptocurrencyInfoModel cryptoInfo) {
        cryptoInfo.setCreatedAt(new SimpleDateFormat(appConfiguration.dateFormat())
                .format(new Date()));
        return cryptoInfo;
    }

    public void saveCryptocurrencyInfo() {
        try {
            List<CryptocurrencyInfoModel> cryptocurrencyInfo = getCryptocurrencyInfo();
            cryptoInfoRepo.saveAll(cryptocurrencyInfo);
        } catch (IOException e) {
            logger.warn(e.getMessage());
        }
    }

    public String getMinCryptoPrice(String currencyName) throws NonexistentCurrencyName {
        if (isExistentCurrencyName(currencyName)) {
            CryptocurrencyInfoModel currencyInfo = cryptoInfoRepo
                    .findTopByCurrencyNameOrderByPriceAsc(currencyName);
            return currencyInfo.getPrice() + "";
        } else {
            throw new NonexistentCurrencyName(currencyName);
        }
    }

    public String getMaxCryptoPrice(String currencyName) throws NonexistentCurrencyName {
        if (isExistentCurrencyName(currencyName)) {
            CryptocurrencyInfoModel currencyInfo = cryptoInfoRepo
                    .findTopByCurrencyNameOrderByPriceDesc(currencyName);
            return currencyInfo.getPrice() + "";
        } else {
            throw new NonexistentCurrencyName(currencyName);
        }
    }

    private boolean isExistentCurrencyName(String currencyName) {
        for (String s : appConfiguration.currencyPairList()) {
            if (s.split("/")[0].equals(currencyName)) {
                return true;
            }
        }
        return false;
    }
}
