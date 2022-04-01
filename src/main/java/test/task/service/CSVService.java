package test.task.service;

import com.opencsv.CSVWriter;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import test.task.config.AppProperties;
import test.task.exeption.NonexistentCurrencyName;
import test.task.utils.CryptoDataUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;

@Service
public class CSVService {
    private static final Logger logger = Logger.getLogger(CryptoDataUtils.class);

    private final CryptocurrencyService cryptocurrencyService;
    private final AppProperties appProperties;

    public CSVService(CryptocurrencyService cryptocurrencyService, AppProperties appProperties) {
        this.cryptocurrencyService = cryptocurrencyService;
        this.appProperties = appProperties;
    }

    public void saveInfoCSV() throws IOException {
        File file = new File(appProperties.getOutputFileName() + ".csv");
        file.createNewFile();
        file = file.getAbsoluteFile();
        try (FileWriter outputFile = new FileWriter(file);
             CSVWriter writer = new CSVWriter(outputFile)) {
            String[] header = {"Cryptocurrency Name", "Min Price", "Max Price"};
            writer.writeNext(header);

            for (String baseCurrency : appProperties.getCurrencyPairs().keySet()) {
                try {
                    BigDecimal minCryptoPrice = cryptocurrencyService.getMinCryptoPrice(baseCurrency);
                    BigDecimal maxCryptoPrice = cryptocurrencyService.getMaxCryptoPrice(baseCurrency);
                    writer.writeNext(new String[]{
                            baseCurrency,
                            minCryptoPrice.toString(),
                            maxCryptoPrice.toString()
                    });
                } catch (NonexistentCurrencyName e) {
                    logger.error(e.getMessage());
                }
            }
        }

    }
}
