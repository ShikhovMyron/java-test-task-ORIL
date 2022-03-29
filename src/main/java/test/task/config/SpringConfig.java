package test.task.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import test.task.service.CryptocurrencyService;

@Configuration
@EnableScheduling
public class SpringConfig {

    private final CryptocurrencyService cryptocurrencyService;

    public SpringConfig(CryptocurrencyService cryptocurrencyService) {
        this.cryptocurrencyService = cryptocurrencyService;
    }

    @Scheduled(fixedDelay = 30000)
    public void fixedDelayTask() {
        cryptocurrencyService.saveCryptocurrencyInfo();
    }
}
