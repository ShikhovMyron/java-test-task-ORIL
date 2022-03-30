package test.task.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import test.task.model.CryptocurrencyInfoModel;

@Repository
public interface CryptocurrencyInfoRepository extends MongoRepository<CryptocurrencyInfoModel, String> {

    //Find MIN price
    CryptocurrencyInfoModel findTopByCurrencyNameOrderByPriceAsc(String currencyName);

    //Find MAX price
    CryptocurrencyInfoModel findTopByCurrencyNameOrderByPriceDesc(String currencyName);
}
