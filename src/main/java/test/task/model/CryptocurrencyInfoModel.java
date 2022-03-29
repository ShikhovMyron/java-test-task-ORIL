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
    private final String createdAt;
    @Id
    private String id;

    public CryptocurrencyInfoModel(String price, String currency_name, String value_name) {
        this.price = price;
        this.currency_name = currency_name;
        this.value_name = value_name;
        this.createdAt = "";
    }
}
