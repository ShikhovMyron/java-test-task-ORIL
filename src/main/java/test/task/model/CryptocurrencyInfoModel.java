package test.task.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Document
public class CryptocurrencyInfoModel {
    @Id
    private String id;
    private String currency_name;
    private String price;
    private String createdAt;

    public void setCurrency_name(String currency_name) {
        this.currency_name = currency_name;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public String getCurrency_name() {
        return currency_name;
    }

    public String getPrice() {
        return price;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public CryptocurrencyInfoModel(String currency_name, String price, String createdAt) {
        this.currency_name = currency_name;
        this.price = price;
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return String.format(
                "CryptocurrencyInfo[id='%s', currency_name='%s', price='%s', createdAt='%s']"
                , id, currency_name, price, createdAt
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CryptocurrencyInfoModel that = (CryptocurrencyInfoModel) o;
        return Objects.equals(id, that.id) && Objects.equals(currency_name, that.currency_name) && Objects.equals(price, that.price) && Objects.equals(createdAt, that.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, currency_name, price, createdAt);
    }
}
