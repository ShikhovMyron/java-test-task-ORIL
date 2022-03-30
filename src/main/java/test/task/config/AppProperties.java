package test.task.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@ConfigurationProperties(prefix = "app.config")
public record AppProperties(
        Map<String, String> currencyPairs,
        String delimiter
) {
}
