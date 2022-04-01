package test.task.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

@Document
@Getter
@EqualsAndHashCode
@ToString
public class CryptoDataPrice {

    @Id
    private String id;
    private final BigDecimal price;
    private final String baseCurrency;
    private final String targetCurrency;
    private String createdAt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

    public CryptoDataPrice(BigDecimal price, String baseCurrency, String targetCurrency) {
        this.price = price;
        this.baseCurrency = baseCurrency;
        this.targetCurrency = targetCurrency;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

}
