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

    private final String price;
    private final String currency_name;
    private final String value_name;
    @Id
    private String id;
    private String createdAt;

    public CryptocurrencyInfoModel(String price, String currency_name, String value_name) {
        this.price = price;
        this.currency_name = currency_name;
        this.value_name = value_name;
//        this.createdAt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
