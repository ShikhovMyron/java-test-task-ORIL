package test.task.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@ConfigurationProperties(prefix = "app.config")
public class AppProperties {
    private final Map<String, String> currencyPairs;

    public AppProperties(Map<String, String> currencyPairs) {
        this.currencyPairs = currencyPairs;
    }
    public Map<String, String> getCurrencyPairs() {
        return currencyPairs;
    }
}
