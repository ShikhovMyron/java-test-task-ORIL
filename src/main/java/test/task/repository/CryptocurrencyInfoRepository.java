package test.task.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import test.task.entity.CryptocurrencyInfoEntity;

import java.util.List;

@Repository
public interface CryptocurrencyInfoRepository extends MongoRepository<CryptocurrencyInfoEntity, String> {

    //Find MIN price
    CryptocurrencyInfoEntity findTopByCurrencyNameOrderByPriceAsc(String currencyName);

    //Find MAX price
    CryptocurrencyInfoEntity findTopByCurrencyNameOrderByPriceDesc(String currencyName);

    List<CryptocurrencyInfoEntity> findAllByCurrencyNameOrderByPriceAsc(String currencyName, Pageable pageable);
}
