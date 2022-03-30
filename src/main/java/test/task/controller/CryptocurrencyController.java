package test.task.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import test.task.service.CryptocurrencyService;

@RestController
@RequestMapping("/cryptocurrencies")
public class CryptocurrencyController {

    private final CryptocurrencyService cryptocurrencyService;

    public CryptocurrencyController(CryptocurrencyService cryptocurrencyService) {
        this.cryptocurrencyService = cryptocurrencyService;
    }

    @GetMapping("/minprice")
    public ResponseEntity<String> getMinPrice(
            @RequestParam(value = "name", defaultValue = "") String currencyName
    ) {
        try {
            return ResponseEntity.ok(cryptocurrencyService.getMinCryptoPrice(currencyName));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/maxprice")
    public ResponseEntity<String> getMaxPrice(
            @RequestParam(value = "name", defaultValue = "") String currencyName
    ) {
        try {
            return ResponseEntity.ok(cryptocurrencyService.getMaxCryptoPrice(currencyName));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
