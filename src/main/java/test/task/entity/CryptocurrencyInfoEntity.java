package test.task.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Getter
@EqualsAndHashCode
@ToString
public class CryptocurrencyInfoEntity {

    private final String price;
    private final String currencyName;
    private final String valueName;
    @Id
    private String id;
    private String createdAt;

    public CryptocurrencyInfoEntity(String price, String currencyName, String valueName) {
        this.price = price;
        this.currencyName = currencyName;
        this.valueName = valueName;
//        this.createdAt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
