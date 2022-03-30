package test.task.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import test.task.entity.CryptoDataPrice;

import java.util.List;

@Repository
public interface CryptocurrencyInfoRepository extends MongoRepository<CryptoDataPrice, String> {

    CryptoDataPrice findTopByBaseCurrencyOrderByPriceAsc(String baseCurrencyName);

    CryptoDataPrice findTopByBaseCurrencyOrderByPriceDesc(String baseCurrencyName);

    List<CryptoDataPrice> findAllByBaseCurrencyOrderByPriceAsc(String baseCurrencyName, Pageable pageable);
}
