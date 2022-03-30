package test.task.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Getter
@EqualsAndHashCode
@ToString
public class CryptocurrencyInfoModel {

    @Id
    private String id;
    private final String price;
    private final String currencyName;
    private final String valueName;
    private String createdAt;

    public CryptocurrencyInfoModel(String price, String currencyName, String valueName) {
        this.price = price;
        this.currencyName = currencyName;
        this.valueName = valueName;
//        this.createdAt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
