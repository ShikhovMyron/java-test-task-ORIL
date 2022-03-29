package test.task.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "app.configuration")
public record AppConfiguration(List<String> currencyPairList, String dateFormat) {
}
