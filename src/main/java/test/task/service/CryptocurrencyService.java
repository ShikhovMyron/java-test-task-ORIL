package test.task.service;

import org.springframework.stereotype.Service;
import test.task.repository.CryptocurrencyInfoRepository;

@Service
public class CryptocurrencyService {

    private final CryptocurrencyInfoRepository cryptoInfoRepo;

    public CryptocurrencyService(CryptocurrencyInfoRepository cryptoInfoRepo) {
        this.cryptoInfoRepo = cryptoInfoRepo;
    }


}
