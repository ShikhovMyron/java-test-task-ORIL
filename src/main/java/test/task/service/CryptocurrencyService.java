package test.task.service;

import org.json.JSONObject;
import org.springframework.stereotype.Service;
import test.task.config.AppConfiguration;
import test.task.model.CryptocurrencyInfoModel;
import test.task.repository.CryptocurrencyInfoRepository;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static test.task.utils.JsonInfoModel.getCryptoInfoFromJsonCEX;
import static test.task.utils.JsonInfoModel.getJsonFromUrl;

@Service
public class CryptocurrencyService {

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
            e.printStackTrace();
        }
    }
}
