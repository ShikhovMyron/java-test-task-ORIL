package test.task.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@ConfigurationProperties(prefix = "app.config")
@Data
public class AppProperties {
    private Map<String, String> currencyPairs;
    private String outputFileName;
}
